public class CannonBall extends Particle {

    public static final int CANNONBALL_RADIUS = 50;
    public static final float CANNONBALL_MASS = 0.1f;
    
    public CannonBall(int xPos, int yPos, float xVel, float yVel) {
        super(xPos, yPos, xVel, yVel);
        this.radius = CANNONBALL_RADIUS;
        this.mass = CANNONBALL_MASS;
    }
    
    
}