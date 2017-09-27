package objects.particles;
import physics.Explosion;
import processing.core.PApplet;
public class CannonBall extends Particle {

    public static final int CANNONBALL_RADIUS = 50;
    public static final float CANNONBALL_MASS = 3f;
    
    public CannonBall(int xPos, int yPos, float xVel, float yVel) {
        super(xPos, yPos, xVel, yVel, CANNONBALL_RADIUS, CANNONBALL_MASS);
    }

	@Override
	public Explosion destroy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void display(PApplet parent) {
		// TODO Auto-generated method stub
		
	}
    
    
}