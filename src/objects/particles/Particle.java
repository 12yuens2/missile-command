package objects.particles;

import processing.core.PVector;
import game.IDrawable;
import physics.Collision;
import physics.Explosion;
import processing.core.PApplet;

public abstract class Particle implements IDrawable {

	public static final PVector GRAVITY = new PVector(0, 0.1f);
    public static final float DAMPING = 0.995f;
  
    public float radius;
    public float mass;
    public PVector position;
	public PVector velocity;
	
	public PVector forceAccumulator;
	
    protected int col = 128;
    
    public Particle(float xPos, float yPos, float xVel, float yVel) {
        position = new PVector(xPos, yPos);
        velocity = new PVector (xVel, yVel);
        
        forceAccumulator = new PVector(0, 0);
    }
    
    public void display(PApplet parent) {
        float size = radius * 2;
        
        parent.ellipseMode(parent.CENTER);
        parent.fill(col);
        parent.ellipse(position.x, position.y, size, size);
    }
    
    public void integrate() {
    
        position.add(velocity);
        
        PVector acceleration = forceAccumulator.copy();
        acceleration.mult(getInvMass());
        
        
        velocity.add(acceleration);
        
        //reset accumulator
        forceAccumulator.set(0, 0);
    }
    
//    private void accelerate(PVector... forces) {
//        float inverseMass = 1f/mass;
//        PVector totalAcceleration = new PVector(0f, 0f);
//        for (PVector force : forces) {
//          if (force != null) {
//            PVector acceleration = force.copy();
//            
//            acceleration = acceleration.mult(inverseMass);
//            totalAcceleration.add(acceleration);
//          }
//        }
//        
//        velocity.add(totalAcceleration);
//    }
    
    public abstract Explosion destroy();
    
    public Collision checkCollision(Particle collider) {
        float collideDistance = radius + collider.radius;
        PVector distanceBetween = PVector.sub(position, collider.position);
        
        if (distanceBetween.mag() < collideDistance && collider.getClass() != this.getClass()) {
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