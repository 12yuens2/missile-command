package game.states.impl;

import game.DrawEngine;
import game.GameConfig;
import game.GameEngine;
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
		parent.background(100);
		drawEngine.displayGame(context.meteors, context.missiles, context.bhms, context.blackholes, context.explosions, 
				context.cities, context.cannons);
		
		drawEngine.drawText(64, "Game Over", GameConfig.SCREEN_X/2, GameConfig.SCREEN_Y/2, 0);

	}

	@Override
	public GameState update() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public GameState handleInput(GameInput input) {
		if (input.keyPressed == PConstants.RETURN || input.keyPressed == PConstants.ENTER) {
			GameContext newGame = GameEngine.createNewContext();
			return new StartState(newGame, drawEngine);
		}
		else return this;
	}

}
