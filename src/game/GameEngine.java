package game;
import java.util.Iterator;
import java.util.function.Function;

import objects.buildings.Cannon;
import objects.buildings.City;
import objects.particles.BlackHoleMissile;
import objects.particles.Meteor;
import objects.particles.Missile;
import objects.particles.Particle;
import physics.BlackHole;
import physics.Explosion;
import physics.PhysEngine;
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
	
	private PApplet parent;
	
	public Level level;
	
    public PhysEngine physicsEngine;
    public DrawEngine drawEngine;
    
    public ArrayList<Meteor> meteors;
    public ArrayList<Missile> missiles;
    public ArrayList<Explosion> explosions;
    
	public ArrayList<BlackHoleMissile> blackMissiles;
	public ArrayList<BlackHole> blackholes;
	
    public ArrayList<City> cities;
    public ArrayList<Cannon> cannons;


    
    public GameEngine(PApplet parent) {
    	this.parent = parent;
    	
    	this.level = new Level();
    	
    	physicsEngine = new PhysEngine();
	    drawEngine = new DrawEngine(parent);

	    initLists();
	    initGameObjects(parent);
    }
    
    private void initLists() {
    	meteors = new ArrayList<Meteor>();
    	missiles = new ArrayList<Missile>();
    	explosions = new ArrayList<Explosion>();
    
    	blackMissiles = new ArrayList<BlackHoleMissile>();
    	blackholes = new ArrayList<BlackHole>();
    	
    	cities = new ArrayList<City>();
	    cannons = new ArrayList<Cannon>();
    }
    
    private void initGameObjects(PApplet parent) {
	    for (int i = 0; i < NUM_CANNONS; i++) {
	        cannons.add(new Cannon(parent, (int)parent.random(100, SCREEN_X-100), 550));
	    }
	    
	    for (int i = 0; i < NUM_CITIES; i++) {
	        cities.add(new City((int)parent.random(30, SCREEN_X-30), 550));   
	    }	
    }
    
    
    

    public void step() {

	    if (level.state == Level.State.FINISHED) {
	    	level.state = Level.State.WAITING;
	    	new Thread(() -> {
	    		try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		level.next();
	    	}).start();
	    } else if (level.state == Level.State.RUNNING) {
		    spawnMeteors(level);
	    }

    	
    	
    	
    	
        drawEngine.display(meteors, missiles, blackMissiles, blackholes, explosions, cities, cannons);

        physicsEngine.step(meteors, missiles, blackMissiles, blackholes, explosions);
	    
	    destroyObjects();
	    

    }

    private void spawnMeteors(Level level) {
    	if ((int)parent.random(0, 2) == 1 && level.numMeteors > 0) {
    	    Meteor meteor = new Meteor((int)parent.random(0, SCREEN_X), 0, parent.random(-2f, 2f), 0f, parent.random(0.1f, 0.5f));
    	    level.spawnMeteor();
    	    meteors.add(meteor);
    	    physicsEngine.registerNewParticle(meteor);
    	}
    }
    
    private void destroyObjects() {
	    destroy(m -> (m.position.y > GROUND_HEIGHT), meteors.iterator());
	    destroy(m -> m.destroyed == true, missiles.iterator());
	    destroy(e -> e.lifespan <= 0, explosions.iterator());
	    
	    remove(bhm -> bhm.destroyed == true, blackMissiles.iterator());
	    remove(bh -> bh.lifespan <= 0, blackholes.iterator());
	    remove(m -> (m.position.x + m.radius < 0 || m.position.x - m.radius > SCREEN_X), meteors.iterator());
	    
	    for (BlackHole bh : blackholes) {
	    	remove(m -> m.checkCollision(bh) != null, meteors.iterator());
	    }

    }

    private <T extends Particle> void destroy(Function<T, Boolean> filter, Iterator<T> it) {
    	while (it.hasNext()) {
    		T object = it.next();
    	    if(filter.apply(object)) {
    	    	it.remove();
    	    	Explosion ex = object.destroy();
    	    	if (ex != null)	explosions.add(ex);
    	    }
    	}
    }
    
    private <T extends IDrawable> void remove(Function<T, Boolean> filter, Iterator<T> it) {
    	while (it.hasNext()) {
    		T object = it.next();
    		if (filter.apply(object)) it.remove();
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