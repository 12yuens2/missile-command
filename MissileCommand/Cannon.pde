public class Cannon {
    
    public static final float forceScalar = 2f;

    private PVector position;
    
    public Cannon(int posX, int posY) {
        position = new PVector(posX, posY);
    }
    
    public void display() {
        rectMode(CENTER);
        fill(0);
        rect(position.x, position.y, 10, 10);
    }
    
    public Particle shoot(PVector direction) {
        PVector shotForce = new PVector(direction.x - position.x, direction.y - position.y);
        
        float force = shotForce.mag();
        shotForce = shotForce.normalize().mult(force/200f);
        System.out.println(force);
        
        Particle bullet = new Particle((int)position.x, (int)position.y, 0f, 0f, 0.1f);
        bullet.integrate(shotForce);
        
        return bullet;
    }
}