package objects.particles;

import java.util.function.Function;

import game.states.GameContext;
import physics.Collision;
import physics.PhysicsStep;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Missile extends Particle {

	public static final int MISSILE_MASS = 2;
	public static final int MISSILE_RADIUS = 7;
	public static final float MISSILE_SPEED_MULT = 7f;
	
	private static final float DETECT_RANGE = 5f;
	
	public boolean friendlyUpgrade;
	
    public PVector destinationPos;
    
    public Missile(PApplet parent, float xPos, float yPos, float destX, float destY) {
    	super(xPos, yPos, 0, 0, MISSILE_RADIUS, MISSILE_MASS);
		this.velocity = new PVector((destX - xPos), (destY - yPos)).normalize().mult(MISSILE_SPEED_MULT);
    	
		this.col = parent.color(255, 0, 0);
		
		this.friendlyUpgrade = false;
		this.destinationPos = new PVector(destX, destY);
    }
    
    public Missile(PApplet parent, float xPos, float yPos, float destX, float destY, boolean friendly) {
    	this(parent, xPos, yPos, destX, destY);
    	this.friendlyUpgrade = friendly;
    	
    	if (friendly) this.col = parent.color(255, 30, 30);
    	else this.col = parent.color(255, 127, 80);
    }

	@Override
    public void integrate() {
		position.add(velocity);
    }
    
    public void display(PApplet parent) {
        if (PVector.sub(destinationPos, position).mag() < DETECT_RANGE || destroyed) {
        	destroyed = true;
        } else {
        	float size = radius * 2;
            parent.ellipseMode(PConstants.CENTER);
            parent.fill(col);
            parent.ellipse(position.x, position.y, size, size);
        }
    }

	@Override
	public Explosion destroy() {
		destroyed = true;
		return new Explosion(position, radius, Explosion.EXPLOSION_LIFESPAN*2, friendlyUpgrade);
	}
	
	
	
	public static PhysicsStep getStep(GameContext context) {
		return new PhysicsStep(context.missiles, new Function<Missile, Void>() {
			
			@Override
			public Void apply(Missile m) {
				m.integrate();
				for (Meteor me : context.meteors) {
					Collision collision = m.checkCollision(me);
					if (collision != null) m.destroy();
				}
				return null;
			}
		});
	}
    
    
}