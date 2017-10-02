package game.states.impl;

import game.DrawEngine;
import game.GameConfig;
import game.GameController;
import game.states.GameContext;
import game.states.GameInput;
import game.states.GameState;
import processing.core.PConstants;

public class GameOverState extends GameState {

	public GameOverState(GameContext context, DrawEngine engine) {
		super(context, engine);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void display() {
		displayGame();
		
		int textX = GameConfig.SCREEN_X/2;
		int textY = GameConfig.SCREEN_Y/3;
		drawEngine.drawText(32, "Game Over", textX, textY, 0);
		drawEngine.drawText(32, "Final score: " + context.info.score, textX, textY + 50, 0);
		drawEngine.drawText(16, "Press Enter to play again.", textX, textY + 100, 0);

	}

	@Override
	public GameState update() {
		runningStep();
		return this;
	}

	@Override
	public GameState handleInput(GameInput input) {
		if (input.keyPressed == PConstants.RETURN || input.keyPressed == PConstants.ENTER) {
			GameContext newGame = new GameContext();
			return new StartState(newGame, drawEngine);
		}
		else return this;
	}

	@Override
	public void updateScore(int score) {
		/* Don't update score on game over state */		
	}

}
