package game.states.impl;

import java.awt.event.KeyEvent;

import game.DrawEngine;
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
		drawEngine.displayWin(context.info.score);
	}

	@Override
	public GameState update() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public GameState handleInput(GameInput input) {
		if (input.keyPressed == PConstants.RETURN || input.keyPressed == PConstants.ENTER) {
			return newGame();
		}
		return this;
	}

	@Override
	public void updateScore(int score) {
		// TODO Auto-generated method stub

	}

}
