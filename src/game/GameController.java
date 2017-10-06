package game;

import game.states.GameContext;
import game.states.GameInput;
import game.states.GameState;
import game.states.impl.StartState;
import objects.buildings.Cannon;
import objects.buildings.City;
import objects.particles.BlackHole;
import objects.particles.BlackHoleMissile;
import objects.particles.Explosion;
import objects.particles.Meteor;
import objects.particles.Missile;
import physics.PhysicsEngine;
import processing.core.PApplet;

import java.util.ArrayList;

/**
 * Keep's track of the current State of the game and interacts with the main MissileCommand class used by Processing.
 * @author sy35
 *
 */
public class GameController {
	
	public PApplet parent;
	
    public DrawEngine drawEngine;
    
    public GameContext context;
    public GameState state;

    
    public GameController(PApplet parent, DrawEngine drawEngine) {
    	this.parent = parent;
    	this.context = new GameContext();
    	this.state = new StartState(context, drawEngine);

	    this.drawEngine = drawEngine;
    }
    

    public void step() {
    	state.display();
    	state = state.update();
    }
    
	public void handleInput(int mouseX, int mouseY, int mouseButton, int keyPressed) {
		GameInput input = new GameInput(mouseX, mouseY, mouseButton, keyPressed);
		state = state.handleInput(input);
	}
    


}