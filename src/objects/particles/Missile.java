package objects.particles;

import physics.Explosion;
import processing.core.PApplet;
import processing.core.PVector;

public class Missile extends Particle {

//    private int lifespan = 60;
    public PVector destinationPos;
    public boolean exploded = false;
    
    public Missile(PApplet parent, int xPos, int yPos, int destX, int destY) {
       super(xPos, yPos, 0f, 0f);
       this.velocity = new PVector((destX - xPos)/10f, (destY - yPos)/10f);
       
       this.mass = 2;
       this.radius = 10;
       this.col = parent.color(255, 0, 0);
       
       this.destinationPos = new PVector(destX, destY);
    }
    
    public Missile(PVector position, float radius) {
    	super(0, 0, 0, 0);
		this.position = position;
		this.radius = radius;
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
            parent.ellipse(position.x, position.y, radius*2, radius*2);
        }
    }

	@Override
	public Explosion destroy() {
		exploded = true;
		return new Explosion(position, radius);
	}
    
    
}