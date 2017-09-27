package objects;
import processing.core.PApplet;
import processing.core.PVector;

public class City {
 
	
    public PVector position;
    public boolean destroyed;
    
    float radius = 10;
    
    public City(int posX, int posY) {
        position = new PVector(posX, posY);
        destroyed = false;
    }
   
    public void display(PApplet parent) {
        if (!destroyed) {
            parent.rectMode(parent.CENTER);
            parent.fill(parent.color(0, 255, 0));
            parent.rect(position.x, position.y, 20, 10);
        }
        
    }
    
}