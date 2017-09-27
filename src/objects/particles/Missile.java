package objects.particles;

import physics.Explosion;
import processing.core.PApplet;
import processing.core.PVector;

public class Missile extends Particle {

	public static final int MISSILE_MASS = 2;
	public static final int MISSILE_RADIUS = 40;
	public static final float MISSILE_SPEED_MULT = 20f;
	
    public PVector destinationPos;
    public boolean exploded;
    
    public Missile(PApplet parent, float xPos, float yPos, float destX, float destY) {
    	super(xPos, yPos, (destX - xPos)/MISSILE_SPEED_MULT, (destY - yPos)/MISSILE_SPEED_MULT, MISSILE_RADIUS, MISSILE_MASS);
		   
		this.col = parent.color(255, 0, 0);
		   
		this.destinationPos = new PVector(destX, destY);
		this.exploded = false;
    }

	@Override
    public void integrate() {
		position.add(velocity);
    }
    
    public void display(PApplet parent) {
        if (PVector.sub(destinationPos, position).mag() < 1f || exploded) {
        	exploded = true;
        } else {
            parent.ellipseMode(parent.CENTER);
            parent.fill(col);
            parent.ellipse(position.x, position.y, radius, radius);
        }
    }

	@Override
	public Explosion destroy() {
		exploded = true;
		return new Explosion(position, radius);
	}
    
    
}