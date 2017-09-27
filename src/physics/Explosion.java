package physics;
import objects.particles.Particle;
import physics.forces.impl.Explosive;
import processing.core.PApplet;
import processing.core.PVector;

public class Explosion extends Particle{
    
	public static final int EXPLOSION_LIFESPAN = 15;
	

    public int lifespan = EXPLOSION_LIFESPAN;
	
    public float radius;
    
    public Explosion(float posX, float posY, float startRadius) {
        super(posX, posY, 0, 0);
        this.radius = startRadius;
    }
    
    public Explosion(PVector position, float radius) {
		super(position.x, position.y, 0, 0);
		this.radius = radius;
	}
    
    public Explosive getForce() {
    	return new Explosive(position, radius);
    }

	public void display(PApplet parent) {
        if (lifespan >= 0) {
            parent.ellipseMode(parent.CENTER);
            parent.fill(255, 127, 80);
            parent.ellipse(position.x, position.y, radius, radius);
            
            radius += 2.5f;
            lifespan--;   
        }   
    }

	@Override
	public Explosion destroy() {
		/* Do not create new explosion when an Explosion is destroyed */
		return null;
	}
}