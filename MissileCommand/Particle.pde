public static final PVector GRAVITY = new PVector(0, 0.03f);

public class Particle {

    public static final float DAMPING = 0.995f;
  
    private int radius;
    private float mass;
    private PVector position, velocity;
    private color col = color(128);
    
    public Particle(int radius, int xPos, int yPos, float xVel, float yVel, float mass) {
        position = new PVector(xPos, yPos);
        velocity = new PVector (xVel, yVel);
        this.radius = radius;
        this.mass = mass;
    }
    
    public void display() {
        int size = radius * 2;
        ellipseMode(CENTER);
        fill(col);
        ellipse(position.x, position.y, size, size);
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
    
    public void checkCollision(Particle collider) {
        float collideDistance = radius + collider.radius;
        PVector distanceBetween = PVector.sub(position, collider.position);
        
        if (distanceBetween.mag() < collideDistance) {
            collider.col = color(255); 
        }
    }
    
    public PVector getPosition() {
        return position;
    }
    
    public void setColor(float col) {
        this.col = color(col);
    }
  
}