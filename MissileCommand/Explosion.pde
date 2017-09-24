public static final int EXPLOSION_LIFESPAN = 15;

public class Explosion {
    
    private float radius;
    private int lifespan = EXPLOSION_LIFESPAN;
    private PVector position;
    
    public Explosion(float posX, float posY, float startRadius) {
        this.position = new PVector(posX, posY);
        this.radius = startRadius;
    }
    
    public void display() {
        if (lifespan > 0) {
            ellipseMode(CENTER);
            fill(255, 127, 80);
            ellipse(position.x, position.y, radius, radius);
            
            radius += 2.5f;
            lifespan--;   
        }
        
    }
}