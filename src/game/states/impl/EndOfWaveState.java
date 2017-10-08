package game.states.impl;

import java.awt.event.KeyEvent;

import game.DrawEngine;
import game.GameConfig;
import game.states.GameContext;
import game.states.GameInput;
import game.states.GameState;

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
		drawEngine.drawText(16, "Press F to go to shop", textX, textY+100, 0);
		drawEngine.drawText(16, "Press Enter to start next wave.", textX, textY+125, 0);
	}

	@Override
	public GameState update() {
    	runningStep();
	    GameState s = checkGameOver();
    	
		return s;
	}

	@Override
	public GameState handleInput(GameInput input) {
		switch(input.keyPressed) {
			case PConstants.ENTER:
			case PConstants.RETURN:
				return nextLevel();
				
			case KeyEvent.VK_F:
				return new ShopState(context, drawEngine, this);
				
			default:
				return this;
		}
	}
	
	private PlayingState nextLevel() {
    	context.level.next();
    	context.info.resetWaveStart(context.level.levelNumber);
		context.meteorCount = context.level.meteorSpawnCount;
		return new PlayingState(context, drawEngine);
	}

	@Override
	public void updateScore(int score) {
		context.info.missilesLeft = 0;
		context.info.score += score;
	}

}
