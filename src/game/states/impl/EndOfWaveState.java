package game.states.impl;

import game.DrawEngine;
import game.GameConfig;
import game.GameEngine;
import game.states.GameContext;
import game.states.GameInput;
import game.states.GameState;
import objects.particles.BlackHoleMissile;
import objects.particles.Explosion;
import objects.particles.Meteor;
import objects.particles.Missile;
import physics.PhysicsStep;
import processing.core.PConstants;

public class EndOfWaveState extends GameState{

	public EndOfWaveState(GameContext context, DrawEngine drawEngine) {
		super(context, drawEngine);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void display() {
		displayGame();
		
		int textX = GameConfig.SCREEN_X/2;
		int textY = GameConfig.SCREEN_Y/3;
		drawEngine.drawText(32, "Wave " + context.level.levelNumber + " finished.", textX, textY, 0);
		drawEngine.drawText(16, "Press Enter to start next wave.", textX, textY+50, 0);
	}

	@Override
	public GameState update() {
    	runningStep();
	    GameState s = checkGameOver();
    	
		return s;
	}

	@Override
	public GameState handleInput(GameInput input) {
		if (input.keyPressed == PConstants.ENTER || input.keyPressed == PConstants.RETURN) {
	    	context.level.next();
	    	context.info.resetWaveStart(context.level.levelNumber);
			context.meteorCount = context.level.numMeteors;
			return new PlayingState(context, drawEngine);
		}
		else return this;
	}

	@Override
	public void updateScore(int score) {
		this.context.info.score += score;
	}

}
