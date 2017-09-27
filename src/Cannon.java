import processing.core.PApplet;
import processing.core.PVector;

public class Cannon {
	
    public PVector position;
    
    public Cannon(PApplet parent, int posX, int posY) {
        position = new PVector(posX, posY);
    }
    
    public void display(PApplet parent) {
        parent.rectMode(parent.CENTER);
        parent.fill(0);
        parent.rect(position.x, position.y, 10, 10);
    }
    
    public Particle shoot(PVector direction) {
        PVector shotForce = new PVector(direction.x - position.x, direction.y - position.y);
        
        float force = shotForce.mag();
        shotForce = shotForce.normalize().mult(force/10f);
        
        Particle bullet = new CannonBall((int)position.x, (int)position.y, 0f, 0f);

        bullet.integrate(shotForce);
        
        return bullet;
    }
}