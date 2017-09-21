public static final PVector GRAVITY = new PVector(0, 0.03f);

public abstract class Particle {

    public static final float DAMPING = 0.995f;
  
    protected int radius;
    protected float mass;
    private PVector position, velocity;
    private color col = color(128);
    
    public Particle(int xPos, int yPos, float xVel, float yVel) {
        position = new PVector(xPos, yPos);
        velocity = new PVector (xVel, yVel);
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
    
    public Collision checkCollision(Particle collider) {
        float collideDistance = radius + collider.radius;
        PVector distanceBetween = PVector.sub(position, collider.position);
        
        if (distanceBetween.mag() < collideDistance && collider.getClass() != this.getClass()) {
            return new Collision(this, collider);
        }
        
        return null;
    }
    
    public PVector getPosition() {
        return position;
    }
    
    public void setColor(float col) {
        this.col = color(col);
    }
  
}