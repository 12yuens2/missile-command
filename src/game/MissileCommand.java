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
	
	public GameEngine gameEngine;
	public DrawEngine drawEngine;
	PFont f;
	
	
	public void settings() {
		size(800, 600);
	}
	
	public void setup() {
	    drawEngine = new DrawEngine(this);
	    gameEngine = new GameEngine(this, drawEngine);
	    f = createFont("Arial", 16, true);
//		frameRate(30);
	}

	
	public void draw() {
		gameEngine.step();
		Crosshair crosshair = new Crosshair();
		crosshair.display(this);
		
		textFont(f, 16);
		fill(0);
		text("Score: " + gameEngine.context.score, 100, 100);
	}


	public void mousePressed() {
		gameEngine.handleInput(mouseX, mouseY, mouseButton, keyCode);
	}
	
	public void keyPressed() {
		gameEngine.handleInput(mouseX, mouseY, mouseButton, keyCode);
	}
	
	
	
	public static void main(String[] args) {
		PApplet.main("game.MissileCommand");
	}
	
}
