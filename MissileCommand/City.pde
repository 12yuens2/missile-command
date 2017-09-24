public class City {
    
 
    PVector position;
    boolean destroyed;
    
    public City(int posX, int posY) {
        position = new PVector(posX, posY);
        destroyed = false;
    }
    
    
    
    public void destroy() {
        destroyed = true;   
    }
    
    public void respawn() {
        destroyed = false;   
    }
    
    
    
    
    public void display() {
    
        if (!destroyed) {
            rectMode(CENTER);
            fill(color(0, 255, 0));
            rect(position.x, position.y, 20, 10);
        }
        
    }
    
}