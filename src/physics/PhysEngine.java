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

public class PhysEngine {

	public static final float dragk1 = 0.0001f;
	public static final float dragk2 = 0.0001f;
	
    public ArrayList<Collision> collisions;
    
    public ForceRegistry forceRegistry;
    public Gravity gravity;
    public Drag drag;
    

    public PhysEngine() {
    	collisions = new ArrayList<Collision>();
    	
    	forceRegistry = new ForceRegistry();
    	gravity = new Gravity();
    	drag = new Drag(dragk1, dragk2);

    }
    
    public void stepFunctions(PhysicsStep step) {
    	step.apply();
    }

    public void step(PhysicsStep... steps) {
    	forceRegistry.updateForces();
        resolveCollisions();
        
        for (PhysicsStep step : steps) step.apply();

    }
    
    
    public void resolveCollisions() {
//        for (Collision c : collisions) c.resolveCollision();
//        collisions.clear();
    }

	public void registerNewParticle(Particle p) {
		forceRegistry.register(p, gravity);
		forceRegistry.register(p, drag);
		
	}
	
	
/*
 * All particles steps
 */
	public PhysicsStep meteorStep(ArrayList<Meteor> meteors, ArrayList<BlackHole> blackholes) {
		return new PhysicsStep(meteors, new Function<Meteor, Void>() {
			
			@Override
			public Void apply(Meteor me) {
				me.integrate();
				
	        	for (BlackHole bh : blackholes) {
	        		forceRegistry.register(me, bh.attractionForce);
	        	}
				return null;
			}
    	});
	}
	
	public PhysicsStep missileStep(ArrayList<Missile> missiles, ArrayList<Meteor> meteors, ArrayList<Explosion> explosions) {
		return new PhysicsStep(missiles, new Function<Missile, Void>() {
			
			@Override
			public Void apply(Missile m) {
				m.integrate();
				
				for (Meteor me : meteors) {
					Collision collision = m.checkCollision(me);
					if (collision != null) explosions.add(m.destroy());
				}
				return null;
			}
		});
	}
	
	public PhysicsStep explosionStep(ArrayList<Explosion> explosions, ArrayList<Meteor> meteors, ArrayList<City> cities) {
		return new PhysicsStep(explosions, new Function<Explosion, Void>() {
			
			@Override
			public Void apply(Explosion e) {
				for (Meteor me : meteors) {
					Collision collision = me.checkCollision(e);
					if (collision != null) forceRegistry.register(me, e.getForce());
				}
				
				for (City city : cities) {
					float collideDistance = city.radius + e.radius;
					float distance = PVector.dist(city.position, e.position);
					if (distance < collideDistance) city.destroy();
				}
				
				return null;
			}
		});
	}
	
	public PhysicsStep blackholeMissileStep(ArrayList<BlackHoleMissile> bhms, ArrayList<BlackHole> blackholes) {
		return new PhysicsStep(bhms, new Function<BlackHoleMissile, Void>() {
			
			@Override
			public Void apply(BlackHoleMissile bhm) {
	        	if (bhm.destroyed) {
	        		blackholes.add(new BlackHole(bhm.position));
	        	}
	        	else {
	        		bhm.integrate();
	        	}
	        	
	        	return null;
			}
		});
	}
	
	
	
	
	
	
	
}