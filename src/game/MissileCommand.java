package game;

import objects.Crosshair;

import processing.core.PApplet;

public class MissileCommand extends PApplet {
	
	public GameController gameController;
	public DrawEngine drawEngine;
	
	
	public void settings() {
		size(GameConfig.SCREEN_X, GameConfig.SCREEN_Y);
	}
	
	public void setup() {
	    drawEngine = new DrawEngine(this);
	    gameController = new GameController(this, drawEngine);
	    
//		frameRate(30);
	}

	
	public void draw() {
		line(0, 300, 600, 300);
		gameController.step();
		Crosshair crosshair = new Crosshair();
		crosshair.display(drawEngine);
	}


	public void mousePressed() {
		gameController.handleInput(mouseX, mouseY, mouseButton, 0);
	}
	
	public void keyPressed() {
		gameController.handleInput(mouseX, mouseY, 0, keyCode);
	}
	
	public static void main(String[] args) {
		PApplet.main("game.MissileCommand");
	}
	
}
