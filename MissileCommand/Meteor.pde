public class Meteor extends Particle {
    
    public static final int METEOR_RADIUS = 10;
    
    public Meteor(int xPos, int yPos, float xVel, float yVel, float mass) {
        super(xPos, yPos, xVel, yVel);
        this.radius = METEOR_RADIUS;
        this.mass = mass;
    }
    
}