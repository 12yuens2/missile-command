package game;
import objects.Crosshair;
import objects.buildings.Cannon;
import objects.particles.BlackHole;
import objects.particles.BlackHoleMissile;
import objects.particles.Missile;
import physics.forces.impl.Explosive;
import processing.core.PApplet;
import processing.core.PFont;

public class MissileCommand extends PApplet {
	
	public GameController gameController;
	public DrawEngine drawEngine;
	
	
	public void settings() {
		size(800, 600);
	}
	
	public void setup() {
	    drawEngine = new DrawEngine(this);
	    gameController = new GameController(this, drawEngine);
//		frameRate(30);
	}

	
	public void draw() {
		gameController.step();
		Crosshair crosshair = new Crosshair();
		crosshair.display(this);
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
