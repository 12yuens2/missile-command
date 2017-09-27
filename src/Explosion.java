import processing.core.PApplet;
import processing.core.PVector;

public class Explosion {
    
	public static final int EXPLOSION_LIFESPAN = 15;
	
    private float radius;
    private int lifespan = EXPLOSION_LIFESPAN;
    private PVector position;
    
    public Explosion(float posX, float posY, float startRadius) {
        this.position = new PVector(posX, posY);
        this.radius = startRadius;
    }
    
    public void display(PApplet parent) {
        if (lifespan > 0) {
            parent.ellipseMode(parent.CENTER);
            parent.fill(255, 127, 80);
            parent.ellipse(position.x, position.y, radius, radius);
            
            radius += 2.5f;
            lifespan--;   
        }   
    }
}