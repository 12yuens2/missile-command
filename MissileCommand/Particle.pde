public static final PVector GRAVITY = new PVector(0, 0.03f);

public class Particle {

    public static final float DAMPING = 0.995f;
  
    private float mass;
    private PVector position, velocity;
    
    public Particle(int xPos, int yPos, float xVel, float yVel, float mass) {
        position = new PVector(xPos, yPos);
        velocity = new PVector (xVel, yVel);
        this.mass = mass;
    }
    
    public void display() {
        rectMode(CENTER);
        fill(128);
        rect(position.x, position.y, 10, 10);
    }
    
    public void integrate(PVector force) {
    
        position.add(velocity);
        
        accelerate(GRAVITY, force);
        
        velocity.mult(DAMPING);
    }
    
    private void accelerate(PVector... forces) {
        float inverseMass = 1f/mass;
        PVector totalAcceleration = new PVector(0f, 0f);
        for (PVector force : forces) {
          if (force != null) {
            PVector acceleration = force.copy();
            
            acceleration = acceleration.mult(inverseMass);
            totalAcceleration.add(acceleration);
          }
        }
        
        velocity.add(totalAcceleration);
    }
    
    public PVector getPosition() {
        return position;
    }
  
  
}