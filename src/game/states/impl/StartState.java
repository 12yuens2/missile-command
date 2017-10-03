package game.states.impl;

import game.DrawEngine;
import game.GameConfig;
import game.GameController;
import game.states.GameContext;
import game.states.GameInput;
import game.states.GameState;
import processing.core.PConstants;

public class StartState extends GameState{

	public StartState(GameContext context, DrawEngine drawEngine) {
		super(context, drawEngine);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void display() {
		drawEngine.displayStartMenu();
	}

	@Override
	public GameState update() {
		return this;
	}

	@Override
	public GameState handleInput(GameInput input) {
		if (input.keyPressed == PConstants.ENTER || input.keyPressed == PConstants.RETURN) return new PlayingState(context, drawEngine);
		else return this;
	}

	@Override
	public void updateScore(int score) {
		/* Do not update score on start state */		
	}

}
