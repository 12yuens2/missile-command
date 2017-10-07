package physics;
import java.util.ArrayList;
import java.util.function.Function;

import objects.buildings.City;
import objects.particles.BlackHole;
import objects.particles.BlackHoleMissile;
import objects.particles.Explosion;
import objects.particles.Meteor;
import objects.particles.Missile;
import objects.particles.Particle;
import physics.forces.impl.Drag;
import physics.forces.impl.Gravity;
import processing.core.PVector;
import physics.forces.ForceRegistry;

public class PhysicsEngine {

	public static final float dragk1 = 0.0003f;
	public static final float dragk2 = 0.0001f;
	
    public ArrayList<Collision> collisions;
    
    public ForceRegistry forceRegistry;
    public Gravity gravity;
    public Drag drag;
    

    public PhysicsEngine() {
    	collisions = new ArrayList<Collision>();
    	
    	forceRegistry = new ForceRegistry();
    	gravity = new Gravity();
    	drag = new Drag(dragk1, dragk2);

    }

    public void step(PhysicsStep... steps) {
    	forceRegistry.updateForces();
        resolveCollisions();
        
        for (PhysicsStep step : steps) step.apply();
    }
    
    
    public void resolveCollisions() {
        for (Collision c : collisions) c.resolveCollision();
        collisions.clear();
    }

	public void registerNewParticle(Particle p) {
		forceRegistry.register(p, gravity);
		forceRegistry.register(p, drag);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}