package game;
import java.util.Iterator;
import java.util.function.Function;

import objects.buildings.Cannon;
import objects.buildings.City;
import objects.particles.BlackHole;
import objects.particles.BlackHoleMissile;
import objects.particles.Explosion;
import objects.particles.Meteor;
import objects.particles.Missile;
import objects.particles.Particle;
import physics.PhysEngine;
import physics.PhysicsStep;
import physics.forces.impl.Gravity;
import processing.core.PApplet;

import java.util.ArrayList;

public class GameEngine{

	public static final int SCREEN_X = 800;
	public static final int SCREEN_Y = 600;
	public static final int GROUND_HEIGHT = 550;
	
	public static final int NUM_PARTICLES = 30;
	public static final int NUM_CANNONS = 4;
	public static final int NUM_CITIES = 5;
	
	public static final int METEOR_EXPLODE_SCORE = 11;
	public static final int METEOR_REMOVED_SCORE = 23;
	
	private PApplet parent;
	
	public Level level;
	
    public PhysEngine physicsEngine;
    public DrawEngine drawEngine;
    
    public ArrayList<Meteor> meteors;
    public ArrayList<Missile> missiles;
    public ArrayList<Explosion> explosions;
    
	public ArrayList<BlackHoleMissile> bhms;
	public ArrayList<BlackHole> blackholes;
	
    public ArrayList<City> cities;
    public ArrayList<Cannon> cannons;

    public int score;
    public boolean gameOver;
    public GameState state;

    
    public GameEngine(PApplet parent) {
    	this.parent = parent;
    	this.level = new Level();
    	this.gameOver = false;
    	this.state = GameState.PLAYING;
    	
    	physicsEngine = new PhysEngine();
	    drawEngine = new DrawEngine(parent);

	    initLists();
	    initGameObjects(parent);
    }
    
    private void resetGame() {
    	level = new Level();
    	gameOver = false;
    	
    	initLists();
    	initGameObjects(parent);
    }
    
    private void initLists() {
    	meteors = new ArrayList<Meteor>();
    	missiles = new ArrayList<Missile>();
    	explosions = new ArrayList<Explosion>();
    
    	bhms = new ArrayList<BlackHoleMissile>();
    	blackholes = new ArrayList<BlackHole>();
    	
    	cities = new ArrayList<City>();
	    cannons = new ArrayList<Cannon>();
    }
    
    private void initGameObjects(PApplet parent) {
	    for (int i = 0; i < NUM_CANNONS; i++) {
	        cannons.add(new Cannon(parent, (int)parent.random(100, SCREEN_X-100), GROUND_HEIGHT));
	    }
	    
	    for (int i = 0; i < NUM_CITIES; i++) {
	        cities.add(new City(50 + ((SCREEN_X/NUM_CITIES) * i), GROUND_HEIGHT));   
	    }	
    }
    
    
    

    public void step() {
    	switch(state) {
    	
    	case START_MENU:
    		drawEngine.displayStartMenu();
    		break;
    		
    	case PLAYING:
    		playStep();
    		drawEngine.displayGame(meteors, missiles, bhms, blackholes, explosions, cities, cannons);
    		break;
    		
    	case PAUSED:
    		drawEngine.displayGame(meteors, missiles, bhms, blackholes, explosions, cities, cannons);
    		drawEngine.displayPauseMenu();
    		break;
    		
    	case GAMEOVER:
    		drawEngine.displayGameOver();
    		break;
    	
    	}

    }
    
    private void playStep() {
		if (level.state == Level.State.FINISHED && level.meteorCount <= 0) {
	    	level.next();
	    } else if (level.state == Level.State.RUNNING) {
		    spawnMeteors(level);
	    }

    	PhysicsStep meteorStep = physicsEngine.meteorStep(meteors, blackholes);
    	PhysicsStep missileStep = physicsEngine.missileStep(missiles, meteors, explosions);
    	PhysicsStep explosionStep = physicsEngine.explosionStep(explosions, meteors, cities);
    	PhysicsStep blackholeMissileStep = physicsEngine.blackholeMissileStep(bhms, blackholes);

        physicsEngine.step(meteorStep, missileStep, explosionStep, blackholeMissileStep);
	    
	    destroyObjects();
    }

    private void spawnMeteors(Level level) {
    	if ((int)parent.random(0, 10) == 1 && level.numMeteors > 0) {
    	    Meteor meteor = new Meteor(level, (int)parent.random(0, SCREEN_X), 0, parent.random(-2f, 2f), 0f, parent.random(0.1f, 0.5f));
    	    level.spawnMeteor();
    	    meteors.add(meteor);
    	    physicsEngine.registerNewParticle(meteor);
    	}
    }
    
    private void destroyObjects() {
	    destroy(m -> (m.position.y > GROUND_HEIGHT), meteors.iterator(), true);
	    destroy(m -> m.destroyed == true, missiles.iterator(), true);
	    destroy(e -> e.lifespan <= 0, explosions.iterator(), true);

	    destroy(m -> (m.position.x + m.radius < 0 || m.position.x - m.radius > SCREEN_X || m.position.y + m.radius < 0), meteors.iterator(), false);
	    
	    for (BlackHole bh : blackholes)	destroy(m -> m.checkCollision(bh) != null, meteors.iterator(), false);
	    
	    remove(bhm -> bhm.destroyed == true, bhms.iterator());
	    remove(bh -> bh.lifespan <= 0, blackholes.iterator());
	    
    }

    private <T extends Particle> void destroy(Function<T, Boolean> filter, Iterator<T> it, boolean explode) {
    	while (it.hasNext()) {
    		T object = it.next();
    	    if(filter.apply(object)) {
    	    	it.remove();
    	    	Explosion ex = object.destroy();
    	    	if (ex != null && explode) explosions.add(ex);
    	    	if (object.getClass().equals(Meteor.class)) {
    	    		if (explode) score += METEOR_EXPLODE_SCORE;
    	    		else score += METEOR_REMOVED_SCORE;
    	    	}
    	    }
    	}
    }
    
    private <T extends IDrawable> void remove(Function<T, Boolean> filter, Iterator<T> it) {
    	while (it.hasNext()) {
    		T object = it.next();
    		if (filter.apply(object)) {
    			it.remove();
    		}
    	}
    }
    
    

	public Cannon getClosestCannon(int posX, int posY) {
    	Cannon closestCannon = null;
    	float closestDistance = Integer.MAX_VALUE;
    
    	for (Cannon cannon : cannons) {
    	    float distance = PApplet.sqrt(PApplet.sq(cannon.position.x - posX) + PApplet.sq(cannon.position.y - posY));
    	    if (closestCannon == null || distance < closestDistance) {
	    		closestCannon = cannon;
	    		closestDistance = distance;
    	    }
    	}
    
    	return closestCannon;
    }
}