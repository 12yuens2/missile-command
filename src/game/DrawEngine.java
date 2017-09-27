package game;
import java.util.ArrayList;

import objects.buildings.Cannon;
import objects.buildings.City;
import objects.particles.Meteor;
import objects.particles.Missile;
import physics.Explosion;
import physics.forces.impl.Explosive;
import processing.core.PApplet;

public class DrawEngine {
    
	private PApplet parent;
	
    public DrawEngine(PApplet parent) {
    	this.parent = parent;
    }
    
    
    public void display(ArrayList<? extends IDrawable>... drawObjects) {
        parent.background(255);
        drawGround();
        
        for (ArrayList<? extends IDrawable> drawList : drawObjects) {
        	for (IDrawable drawable : drawList) {
        		drawable.display(parent);
        	}
        }
        
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