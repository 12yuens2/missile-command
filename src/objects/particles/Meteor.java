package objects.particles;
import game.GameEngine;
import physics.Explosion;
import processing.core.PApplet;

public class Meteor extends Particle {
    
    public static final int METEOR_RADIUS = 10;
    
    public Meteor(int xPos, int yPos, float xVel, float yVel, float mass) {
        super(xPos, yPos, xVel, yVel, METEOR_RADIUS, mass);
    }
	
	@Override
    public void display(PApplet parent) {
        float size = radius * 2;
        
        parent.ellipseMode(parent.CENTER);
        parent.fill(col);
        parent.ellipse(position.x, position.y, size, size);
    }

	@Override
	public Explosion destroy() {
		position.y = GameEngine.GROUND_HEIGHT;
		return new Explosion(position, radius);
	}
	
    
}