public class Missile extends Particle {

    private int lifespan = 120;
    private PVector destinationPos;
    
    public Missile(int xPos, int yPos, int destX, int destY) {
       super(xPos, yPos, 0f, 0f);
       this.velocity = new PVector((destX - xPos)/30f, (destY - yPos)/30f);
       
       this.radius = 30;
       this.col = color(255, 0, 0);
       
       this.destinationPos = new PVector(destX, destY);
    }
    
    
    public void display() {
        
        if (PVector.sub(destinationPos, position).mag() < 1f) {
            super.display();  
        } else {
            rectMode(CENTER);
            fill(col);
            rect(position.x, position.y, 20, 50);
            
            System.out.println("bx: " + position.x + " , y: " + position.y);
            position.add(velocity);
            System.out.println("x: " + position.x + " , y: " + position.y);
            //System.out.println("velx: " + velocity.x + " , vely: " + velocity.y);
        }
    }
    
    
}