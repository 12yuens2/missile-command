import processing.core.PApplet;
import processing.core.PVector;

public class Missile extends Particle {

    private int lifespan = 60;
    public PVector destinationPos;
    public boolean exploded = false;
    
    public Missile(PApplet parent, int xPos, int yPos, int destX, int destY) {
       super(xPos, yPos, 0f, 0f);
       this.velocity = new PVector((destX - xPos)/10f, (destY - yPos)/10f);
       
       this.mass = 2;
       this.radius = 30;
       this.col = parent.color(255, 0, 0);
       
       this.destinationPos = new PVector(destX, destY);
    }
    
    @Override
    public void integrate(PVector force) {
        //do nothing
    }
    
    public void display(PApplet parent) {
        
        if (PVector.sub(destinationPos, position).mag() < 1f || exploded) {
            if (lifespan > 0) {
                super.display(parent);
                lifespan--;
            }
        } else {
            parent.ellipseMode(parent.CENTER);
            parent.fill(col);
            parent.ellipse(position.x, position.y, 10, 10);
            
            //System.out.println("bx: " + position.x + " , y: " + position.y);
            position.add(velocity);
            //System.out.println("x: " + position.x + " , y: " + position.y);
            //System.out.println("velx: " + velocity.x + " , vely: " + velocity.y);

        }
    }

	@Override
	public Explosion destroy() {
		return new Explosion(position.x, position.y, radius);
	}
    
    
}