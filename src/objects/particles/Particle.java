package objects.particles;

import processing.core.PVector;
import game.IDrawable;
import physics.Collision;
import processing.core.PApplet;

public abstract class Particle implements IDrawable {

	public static final PVector GRAVITY = new PVector(0, 0.1f);
    public static final float DAMPING = 0.995f;
  
    public boolean destroyed;
    
    public float radius;
    public float mass;
    public PVector position;
	public PVector velocity;
	
	public PVector forceAccumulator;
	
    public int col = 128;
    
    public Particle(float xPos, float yPos, float xVel, float yVel, float radius, float mass) {
        this.position = new PVector(xPos, yPos);
        this.velocity = new PVector (xVel, yVel);
        this.radius = radius;
        this.mass = mass;
        this.destroyed = false;
        
        forceAccumulator = new PVector(0, 0);
    }
    
    public abstract Explosion destroy();
    
    public void integrate() {
        position.add(velocity);
        
        PVector acceleration = forceAccumulator.copy();
        acceleration.mult(getInvMass());
        
        velocity.add(acceleration);

        //reset accumulator
        forceAccumulator.set(0, 0);
    }
    
    public Collision checkCollision(Particle collider) {
        float collideDistance = radius + collider.radius;
        PVector distanceBetween = PVector.sub(position, collider.position);
        
        float distance = PVector.dist(position, collider.position);
        
        if (distance < collideDistance && collider.getClass() != this.getClass()) {
            return new Collision(this, collider);
        }
        
        return null;
    }

	public void addForce(PVector force) {
		forceAccumulator.add(force);	
	}
	
	public float getInvMass() {
		return (1f/mass);
	}

  
}