package objects.buildings;
import processing.core.PApplet;
import processing.core.PVector;

public class City extends Building{
 
    public float radius = 7.5f;
    
    public City(int posX, int posY) {
        position = new PVector(posX, posY);
        destroyed = false;
    }
   
    public void display(PApplet parent) {
        if (!destroyed) {
            parent.rectMode(parent.CENTER);
            parent.fill(parent.color(0, 255, 0));
            parent.rect(position.x, position.y, 20, 10);
            parent.ellipse(position.x, position.y, radius*2, radius*2);
        } 
    }

	@Override
	public void destroy() {
		destroyed = true;
	}
	
	public void rebuild() {
		destroyed = false;
	}
    
}