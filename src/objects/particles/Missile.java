package objects.particles;

import physics.Explosion;
import processing.core.PApplet;
import processing.core.PVector;

public class Missile extends Particle {

	public static final int MISSILE_MASS = 2;
	public static final int MISSILE_RADIUS = 10;
	public static final float MISSILE_SPEED_MULT = 5f;
	
	private static final float DETECT_RANGE = 2.5f;
	
    public PVector destinationPos;
    
    public Missile(PApplet parent, float xPos, float yPos, float destX, float destY) {
    	super(xPos, yPos, 0, 0, MISSILE_RADIUS, MISSILE_MASS);
		this.velocity = new PVector((destX - xPos), (destY - yPos)).normalize().mult(MISSILE_SPEED_MULT);
    	
		this.col = parent.color(255, 0, 0);
		   
		this.destinationPos = new PVector(destX, destY);
    }

	@Override
    public void integrate() {
		position.add(velocity);
    }
    
    public void display(PApplet parent) {
        if (PVector.sub(destinationPos, position).mag() < DETECT_RANGE || destroyed) {
        	destroyed = true;
        } else {
            parent.ellipseMode(parent.CENTER);
            parent.fill(col);
            parent.ellipse(position.x, position.y, radius, radius);
        }
    }

	@Override
	public Explosion destroy() {
		destroyed = true;
		return new Explosion(position, radius, Explosion.EXPLOSION_LIFESPAN*2);
	}
    
    
}