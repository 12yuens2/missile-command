package physics;
import java.util.ArrayList;

import objects.particles.Meteor;
import objects.particles.Missile;
import physics.forces.impl.Explosive;
import physics.forces.ForceGenerator;
import physics.forces.ForceRegistry;

public class PhysEngine {

    public ArrayList<Collision> collisions;
    
    public ForceRegistry forceRegistry;


    public PhysEngine() {
    	forceRegistry = new ForceRegistry();
        collisions = new ArrayList<Collision>();
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
        		Collision collision = me.checkCollision(new Missile(e.position, e.radius));
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
}