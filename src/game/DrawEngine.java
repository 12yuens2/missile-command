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
    
	public PApplet parent;
	
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
		drawText(64, "Start game", GameConfig.SCREEN_X/2, GameConfig.SCREEN_Y/2, 0);
	}


	public void displayPauseMenu() {
		parent.fill(200, 100);
		parent.rect(0, 0, 8000, 6000);
		drawText(32, "Paused", GameConfig.SCREEN_X/2, GameConfig.SCREEN_Y/2, 0);
	}


	public void displayGameOver() {
		parent.background(0);
		drawText(64, "Game Over", GameConfig.SCREEN_X/2, GameConfig.SCREEN_Y/2, 255);
		
	}
	
	public void drawText(int textSize, String text, int posX, int posY, int col) {
		PFont font = parent.createFont("Arial", textSize, true);
		
		parent.textFont(font, textSize);
		parent.fill(col);
		parent.text(text, posX, posY);
		parent.textAlign(parent.CENTER, parent.CENTER);
		
	}
    
    private  void drawGround() {
        parent.fill(128);
        parent.beginShape();
        parent.vertex(0, GameConfig.GROUND_HEIGHT);
        parent.vertex(GameConfig.SCREEN_X, GameConfig.GROUND_HEIGHT);
        parent.vertex(GameConfig.SCREEN_X, GameConfig.SCREEN_Y);
        parent.vertex(0, GameConfig.SCREEN_Y);
        parent.endShape(parent.CLOSE);
    }



    
}