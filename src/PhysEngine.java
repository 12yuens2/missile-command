import java.util.ArrayList;

public class PhysEngine {
 

    public ArrayList<Collision> collisions;

    public PhysEngine() {
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