package objects.particles;
import game.GameEngine;
import physics.Explosion;
import physics.forces.impl.Explosive;
import processing.core.PVector;

public class Meteor extends Particle {
    
    public static final int METEOR_RADIUS = 10;
    
    public Meteor(int xPos, int yPos, float xVel, float yVel, float mass) {
        super(xPos, yPos, xVel, yVel);
        this.radius = METEOR_RADIUS;
        this.mass = mass;
    }

	public Meteor(PVector position, float radius) {
		super(0, 0, 0, 0);
		this.position = position;
		this.radius = radius;
		this.mass = 1;
	}

	@Override
	public Explosion destroy() {
		position.y = GameEngine.GROUND_HEIGHT;
		return new Explosion(position, radius);
	}
	
    
}