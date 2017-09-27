package physics;
import java.util.ArrayList;

import objects.particles.Meteor;
import objects.particles.Missile;
import objects.particles.Particle;
import physics.forces.impl.Drag;
import physics.forces.impl.Explosive;
import physics.forces.impl.Gravity;
import physics.forces.ForceGenerator;
import physics.forces.ForceRegistry;

public class PhysEngine {

	public static final float dragk1 = 0.1f;
	public static final float dragk2 = 0.1f;
	
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

    public void step(ArrayList<Meteor> meteors, ArrayList<Missile> missiles, ArrayList<Explosion> explosions) {
    	forceRegistry.updateForces();
        resolveCollisions();
        
        for (Meteor m : meteors) m.integrate();
        
        for (Missile m : missiles) {
            m.integrate();
            
            for (Meteor me : meteors) {
                Collision collision = m.checkCollision(me);
                if (collision != null) {
                	Explosion ex = m.destroy();
    	    		forceRegistry.register(me, ex.getForce());
                    explosions.add(ex);
                }
            }
        }
        
        for (Explosion e : explosions) {
        	for (Meteor me : meteors) {
        		Collision collision = me.checkCollision(e);
        		if (collision != null) {
        			forceRegistry.register(me, e.getForce());
        		}
        	}
        	
        }
    }
    
    
    public void resolveCollisions() {
//        for (Collision c : collisions) c.resolveCollision();
//        collisions.clear();
    }

	public void registerNewParticle(Particle p) {
		forceRegistry.register(p, gravity);
//		forceRegistry.register(p, drag);
		
	}
}