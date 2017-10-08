package game;

import game.states.GameContext;
import game.states.GameInput;
import game.states.GameState;
import game.states.impl.StartState;
import processing.core.PApplet;

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
    

    /**
     * Each step of the game.
     * Called at every frame.
     */
    public void step() {
    	state.display();
    	state = state.update();
    }
    
	public void handleInput(int mouseX, int mouseY, int mouseButton, int keyPressed) {
		GameInput input = new GameInput(mouseX, mouseY, mouseButton, keyPressed);
		state = state.handleInput(input);
	}
    


}