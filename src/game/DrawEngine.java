package game;
import java.util.ArrayList;

import objects.buildings.Cannon;
import objects.buildings.City;
import objects.particles.Explosion;
import objects.particles.Meteor;
import objects.particles.Missile;
import physics.forces.impl.Explosive;
import processing.core.PApplet;
import processing.core.PFont;

public class DrawEngine {
    
	private PApplet parent;
	
    public DrawEngine(PApplet parent) {
    	this.parent = parent;
    }
    
    
    public void displayGame(ArrayList<? extends IDrawable>... drawObjects) {
        parent.background(255);
        drawGround();
        
        for (ArrayList<? extends IDrawable> drawList : drawObjects) {
        	for (IDrawable drawable : drawList) {
        		drawable.display(parent);
        	}
        }
    }
    
	public void displayStartMenu() {
		parent.background(255);
		drawText(64, "Start game", GameEngine.SCREEN_X/2, GameEngine.SCREEN_Y/2, 0);
	}


	public void displayPauseMenu() {
		parent.fill(200, 100);
		parent.rect(0, 0, 8000, 6000);
		drawText(32, "Paused", GameEngine.SCREEN_X/2, GameEngine.SCREEN_Y/2, 0);
	}


	public void displayGameOver() {
		// TODO Auto-generated method stub
		
	}
	
	private void drawText(int textSize, String text, int posX, int posY, int col) {
		PFont font = parent.createFont("Arial", textSize, true);
		
		parent.textFont(font, textSize);
		parent.fill(col);
		parent.text(text, posX, posY);
		parent.textAlign(parent.CENTER, parent.CENTER);
		
	}
    
    private  void drawGround() {
        parent.fill(128);
        parent.beginShape();
        parent.vertex(0, GameEngine.GROUND_HEIGHT);
        parent.vertex(GameEngine.SCREEN_X, GameEngine.GROUND_HEIGHT);
        parent.vertex(GameEngine.SCREEN_X, GameEngine.SCREEN_Y);
        parent.vertex(0, GameEngine.SCREEN_Y);
        parent.endShape(parent.CLOSE);
    }



    
}