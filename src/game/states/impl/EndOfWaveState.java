package game.states.impl;

import game.DrawEngine;
import game.GameConfig;
import game.GameController;
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

	private int bonusScore;
	
	public EndOfWaveState(GameContext context, DrawEngine drawEngine, int bonusScore) {
		super(context, drawEngine);
		this.bonusScore = bonusScore;
		
		updateScore(bonusScore);
	}

	@Override
	public void display() {
		displayGame();
		
		int textX = GameConfig.SCREEN_X/2;
		int textY = GameConfig.SCREEN_Y/4;
		drawEngine.drawText(32, "Wave " + context.level.levelNumber + " finished.", textX, textY, 0);
		drawEngine.drawText(16, bonusScore + " bonus score for remaining cities and missiles.", textX, textY+50, 0);
		drawEngine.drawText(16, "Press Enter to start next wave.", textX, textY+75, 0);
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
			context.meteorCount = context.level.meteorSpawnCount;
			return new PlayingState(context, drawEngine);
		}
		else return this;
	}

	@Override
	public void updateScore(int score) {
		this.context.info.score += score;
	}

}