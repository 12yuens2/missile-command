public class Missile extends Particle {

    private int lifespan = 120;
    
    public Missile(int xPos, int yPos, float xVel, float yVel) {
       super(xPos, yPos, xVel, yVel);
       this.radius = 10;
       this.col = color(255, 0, 0);
    }
    
    
    public void display() {
        super.display();
        
        lifespan--;
        if (lifespan % 10 == 0 && lifespan > 0) radius++;
    }
    
    
}