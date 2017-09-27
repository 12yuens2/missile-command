package physics;
import java.util.ArrayList;

import objects.particles.Meteor;
import objects.particles.Missile;
import physics.forces.ForceRegistry;

public class PhysEngine {

    public ArrayList<Collision> collisions;
    
    public ForceRegistry forceRegistry;

    public PhysEngine() {
    	forceRegistry = new ForceRegistry();
        collisions = new ArrayList<Collision>();        
    }

    public void step(ArrayList<Meteor> meteors, ArrayList<Missile> missiles, ArrayList<Explosion> explosions) {
        resolveCollisions();
        
        for (Meteor m : meteors) m.integrate(null);
        
        for (Missile m : missiles) {
            m.integrate(null);
            
            for (Meteor me : meteors) {
                Collision collision = m.checkCollision(me);
                if (collision != null) {
                    collisions.add(collision);
                    m.exploded = true;
                }
            }
        }
    }
    
    
    public void resolveCollisions() {
        for (Collision c : collisions) c.resolveCollision();
        collisions.clear();
    }
}