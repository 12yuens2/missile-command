package game;
import java.util.ArrayList;

import game.states.GameContext;
import game.states.GameInfo;
import objects.buildings.Cannon;
import objects.buildings.City;
import objects.particles.BlackHole;
import objects.particles.Explosion;
import objects.particles.Meteor;
import objects.particles.Missile;
import physics.forces.impl.Explosive;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

public class DrawEngine {
    
	public PApplet parent;
	
    public DrawEngine(PApplet parent) {
    	this.parent = parent;
    }
    
    
    public void displayGame(GameContext context) {
        parent.background(255);
        drawGround();
        
        displayDrawables(context.meteors, 
        		context.missiles, 
        		context.explosions,
        		context.bombers,
        		context.bhms, 
        		context.blackholes, 
        		context.forcefields, 
        		context.cities, 
        		context.cannons);
        

    }
    
    private void displayDrawables(ArrayList<? extends IDrawable>... drawables) {
        for (ArrayList<? extends IDrawable> drawList : drawables) {
        	for (IDrawable drawable : drawList) {
        		drawable.display(parent);
        	}
        }
    }
    
	public void displayStartMenu() {
		parent.background(255);
		drawText(64, "Start game", GameConfig.SCREEN_X/2, GameConfig.SCREEN_Y/2, 0);		
		drawText(32, "Press Enter to start.", GameConfig.SCREEN_X/2, GameConfig.SCREEN_Y/2+75, 0);
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


	public void displayInfo(GameInfo info) {
		drawText(16, "Score: " + info.score, 100, 50, 0);
		drawText(16, "Missiles: " + info.missilesLeft, 100, 75, 0);
		drawText(16, "Blackholes: " + info.blackholesLeft, 600, 50, 0);
		drawText(16, "Forcefields: " + info.forcefieldsLeft, 600, 75, 0);
		
	}


	public void drawShopScreen() {
		int textX = GameConfig.SCREEN_X/2;
		int textY = GameConfig.SCREEN_Y/4;
		
		parent.background(255);		
		drawText(16, "Welcome to the shop, press F to leave.", textX, textY, 0);

		int shopX = 150;
		int shopY = GameConfig.SCREEN_Y/2 - 75;
		
		/* Black hole */
		BlackHole blackhole = new BlackHole(new PVector(shopX, shopY));
		blackhole.display(parent);
		drawText(12, "[1] Buy a blackhole for " + GameConfig.BLACK_HOLE_COST, shopX + 115, shopY, 0);
		
		/* Force field */
		parent.ellipseMode(parent.CENTER);
		parent.fill(0, 0, 100, 100);
		parent.ellipse(shopX + 350, shopY, 50, 50);
		drawText(12, "[2] Buy a forcefield for " + GameConfig.FORCEFIELD_COST, shopX + 465, shopY, 0);
		
		/* Missile */
		Missile missile = new Missile(parent, shopX, shopY + 100, 0, 0);
		missile.display(parent);
		drawText(12, "[3] Buy a missile for " + GameConfig.MISSILE_COST, shopX + 90, shopY + 100, 0);
		
		
		/* City */
		City city = new City(shopX + 350, shopY + 100);
		city.display(parent);
		drawText(12, "[4] Rebuild a city for " + GameConfig.CITY_COST, shopX + 450, shopY + 100, 0);
		
		
		/* End game */
		drawText(16, "To beat the game, you must have all cities alive and pay " + GameConfig.END_GAME_COST + " score", 
				textX, textY+300, 0);
		drawText(16, "Press Enter to pay", textX, textY+325, 0);
		
	}


	public void displayWin(int score) {

		parent.background(255);
		int textX = GameConfig.SCREEN_X/2;
		int textY = GameConfig.SCREEN_Y/3;
		
		drawText(64, "Win!", textX, textY, 0);
		drawText(32, "Score: " + score, textX, textY + 75, 0);
		drawText(32, "Enter to restart", textX, textY + 150, 0);
		
	}



    
}