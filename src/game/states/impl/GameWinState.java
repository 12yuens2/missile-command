package game.states.impl;

import java.awt.event.KeyEvent;

import game.DrawEngine;
import game.GameConfig;
import game.states.GameContext;
import game.states.GameInput;
import game.states.GameState;
import processing.core.PConstants;

public class GameWinState extends GameState {
	
	public GameWinState(GameContext context, DrawEngine drawEngine) {
		super(context, drawEngine);
	}

	@Override
	public void display() {
		parent.background(255);
		int textX = GameConfig.SCREEN_X/2;
		int textY = GameConfig.SCREEN_Y/3;
		
		drawEngine.drawText(64, "Win!", textX, textY, 0);
		drawEngine.drawText(32, "Score: " + context.info.score, textX, textY + 75, 0);
		drawEngine.drawText(32, "Enter to restart", textX, textY + 150, 0);
	}

	@Override
	public GameState update() {
		/* Always return 'this' as there is nothing to update in this state. */
		return this;
	}

	@Override
	public GameState handleInput(GameInput input) {
		if (input.keyPressed == PConstants.RETURN || input.keyPressed == PConstants.ENTER) {
			/* Create a new game */
			return newGame();
		}
		return this;
	}

	@Override
	public void updateScore(int score) {
		/* Do nothing as there is no score to update in this state */
	}

}
