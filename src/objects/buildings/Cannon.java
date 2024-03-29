package objects.buildings;

import game.DrawEngine;

import processing.core.PVector;

public class Cannon extends Building {
    
    public Cannon(int posX, int posY) {
        position = new PVector(posX, posY);
    }
    
    @Override
    public void display(DrawEngine drawEngine) {
    	drawEngine.drawRectangle(0, position.x, position.y, 10, 10);
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
    
}