package game.states.impl;

import java.awt.event.KeyEvent;

import game.DrawEngine;
import game.GameConfig;
import game.states.GameContext;
import game.states.GameInfo;
import game.states.GameInput;
import game.states.GameState;
import objects.buildings.City;
import processing.core.PConstants;

public class ShopState extends GameState {

	private EndOfWaveState prevState;
	
	public ShopState(GameContext context, DrawEngine drawEngine, EndOfWaveState prevState) {
		super(context, drawEngine);
		this.prevState = prevState;
	}

	@Override
	public void display() {
		drawEngine.drawShopScreen();
		drawEngine.displayInfo(context.info);
	}

	@Override
	public GameState update() {
		return this;
	}

	@Override
	public GameState handleInput(GameInput input) {
		GameInfo info = context.info;
		switch(input.keyPressed) {
			case KeyEvent.VK_F:
				return prevState;
				
			case PConstants.ENTER:
			case PConstants.RETURN:
				if (info.score >= GameConfig.END_GAME_COST && info.citiesLeft == GameConfig.NUM_CITIES) {
					info.score -= GameConfig.END_GAME_COST;
					return new GameWinState(context, drawEngine);
				}
				break;
				
				
			case KeyEvent.VK_1:
				if (info.score >= GameConfig.BLACK_HOLE_COST) {
					info.score -= GameConfig.BLACK_HOLE_COST;
					info.blackholesLeft += 1;
				}
				break;
				
			case KeyEvent.VK_2:
				if (info.score >= GameConfig.FORCEFIELD_COST) {
					info.score -= GameConfig.FORCEFIELD_COST;
					info.forcefieldsLeft += 1;
				}
				break;
				
			case KeyEvent.VK_3:
				if (info.score >= GameConfig.MISSILE_COST) {
					info.score -= GameConfig.MISSILE_COST;
					info.missilesLeft += 1;
				}
				break;
				
			case KeyEvent.VK_4:
				if (info.score >= GameConfig.CITY_COST && info.citiesLeft < GameConfig.NUM_CITIES) {
					info.score -= GameConfig.CITY_COST;
					rebuildCity();
				}
				break;
		}
		return this;
	}

	private void rebuildCity() {
		for (City c : context.cities) {
			if (c.destroyed) {
				c.rebuild();
				context.info.citiesLeft += 1;
				return;
			}
		}
	}

	@Override
	public void updateScore(int score) {
		// TODO Auto-generated method stub

	}

}
