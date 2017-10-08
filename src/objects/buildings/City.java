package objects.buildings;

import game.DrawEngine;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class City extends Building{
 
    public float radius = 7.5f;
    
    public City(int posX, int posY) {
        position = new PVector(posX, posY);
        destroyed = false;
    }
   
    @Override
    public void display(DrawEngine drawEngine) {
        if (!destroyed) {
        	int col = drawEngine.parent.color(0, 255, 0);
        	drawEngine.drawRectangle(col, position.x, position.y, 20, 10);
//            parent.rectMode(PConstants.CENTER);
//            parent.fill(parent.color(0, 255, 0));
//            parent.rect(position.x, position.y, 20, 10);
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