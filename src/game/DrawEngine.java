package game;
import java.util.ArrayList;

import objects.Cannon;
import objects.City;
import objects.particles.Meteor;
import objects.particles.Missile;
import physics.Explosion;
import processing.core.PApplet;

public class DrawEngine {
    
	private PApplet parent;
	
    public DrawEngine(PApplet parent) {
    	this.parent = parent;
    }
    
    
    public void display(ArrayList<Meteor> meteors, ArrayList<Missile> missiles, ArrayList<Explosion> explosions, ArrayList<City> cities, ArrayList<Cannon> cannons) {
        parent.background(255);
        drawGround();
        
        for (Meteor m : meteors) m.display(parent);
        for (Missile m : missiles) m.display(parent);
        for (Explosion e : explosions) e.display(parent);
        
        for (City c : cities) c.display(parent);
        for (Cannon c : cannons) c.display(parent);
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