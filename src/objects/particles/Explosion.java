package objects.particles;
import physics.forces.impl.Explosive;
import processing.core.PApplet;
import processing.core.PVector;

public class Explosion extends Particle{
    
	public static final int EXPLOSION_LIFESPAN = 20;
	

    public int lifespan = EXPLOSION_LIFESPAN;
    
    public Explosion(float posX, float posY, float startRadius, int lifespan) {
        super(posX, posY, 0, 0, startRadius, 0);
        this.lifespan = lifespan;
    }
    
    public Explosion(PVector position, float radius) {
		this(position.x, position.y, radius, EXPLOSION_LIFESPAN);
	}
    
    public Explosion(PVector position, float radius, int lifespan) {
    	this(position.x, position.y, radius, lifespan);
    }
    
    public Explosive getForce() {
    	return new Explosive(position, radius);
    }

	public void display(PApplet parent) {
        if (lifespan >= 0) {
            radius += 1;
            lifespan--;
            
            float size = radius * 2;
            parent.ellipseMode(parent.CENTER);
            parent.fill(255, 127, 80, 200);
            parent.ellipse(position.x, position.y, size, size);
        }   
    }

	@Override
	public Explosion destroy() {
		/* Do not create new explosion when an Explosion is destroyed */
		return null;
	}
}