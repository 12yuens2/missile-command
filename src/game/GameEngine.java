package game;
import java.util.Iterator;
import java.util.function.Function;

import objects.buildings.Cannon;
import objects.buildings.City;
import objects.particles.Meteor;
import objects.particles.Missile;
import objects.particles.Particle;
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
	
    public PhysEngine physicsEngine;
    public DrawEngine drawEngine;
    
    public final Gravity gravity = new Gravity();
    
    public ArrayList<Meteor> meteors;
    public ArrayList<Missile> missiles;
    public ArrayList<Explosion> explosions;


    public ArrayList<City> cities;
    public ArrayList<Cannon> cannons;

    
    public GameEngine(PApplet parent) {
    	this.parent = parent;
    	
    	physicsEngine = new PhysEngine();
	    drawEngine = new DrawEngine(parent);

	    initLists();
	    initGameObjects(parent);
    }
    
    private void initLists() {
    	meteors = new ArrayList<Meteor>();
    	missiles = new ArrayList<Missile>();
    	explosions = new ArrayList<Explosion>();
    
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
	    spawnMeteors();
	    
	    destroy(m -> m.position.y > GROUND_HEIGHT, meteors.iterator());
	    destroy(m -> m.exploded == true, missiles.iterator());
	    destroy(e -> e.lifespan < 0, explosions.iterator());
	    
        physicsEngine.step(meteors, missiles, explosions);
        drawEngine.display(meteors, missiles, explosions, cities, cannons);
    }

    private void spawnMeteors() {
    	if ((int)parent.random(0, 2) == 1) {
    	    Meteor meteor = new Meteor((int)parent.random(0, SCREEN_X), -100, parent.random(-5f, 5f), 0f, parent.random(0.1f, 0.5f));
    	    meteors.add(meteor);
    	    physicsEngine.forceRegistry.register(meteor, gravity);
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