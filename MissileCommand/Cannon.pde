public class Cannon {

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
        shotForce = shotForce.normalize().mult(force/50f);
        
        Particle bullet = new CannonBall((int)position.x, (int)position.y, 0f, 0f);
        bullet.setColor(0);
        bullet.integrate(shotForce);
        
        return bullet;
    }
}