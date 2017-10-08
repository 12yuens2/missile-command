package game.states.impl;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

import game.DrawEngine;
import game.GameConfig;
import game.states.GameContext;
import game.states.GameInfo;
import game.states.GameInput;
import game.states.GameState;
import objects.buildings.City;
import objects.particles.BlackHole;
import objects.particles.Missile;
import processing.core.PConstants;
import processing.core.PVector;

public class ShopState extends GameState {

	private EndOfWaveState prevState;
	
	public ShopState(GameContext context, DrawEngine drawEngine, EndOfWaveState prevState) {
		super(context, drawEngine);
		this.prevState = prevState;
	}

	@Override
	public void display() {
		drawEngine.displayGame(context);
		drawEngine.displayInfo(context.info);
		drawShopScreen();
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
				
			case KeyEvent.VK_0:
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
				
			case KeyEvent.VK_5:
				if (info.score >= GameConfig.FRIENDLY_EXPLOSIONS_COST && !context.friendlyExplosions) {
					info.score -= GameConfig.FRIENDLY_EXPLOSIONS_COST;
					context.friendlyExplosions = true;
				}
		}
		return this;
	}

	private void rebuildCity() {
		ArrayList<City> destroyedCities = (ArrayList<City>) context.cities.stream()
																		.filter(c -> c.destroyed)
																		.collect(Collectors.toList());
		
		Random r = new Random();
		City city = destroyedCities.get(r.nextInt(destroyedCities.size()));
		
		city.rebuild();
		context.info.citiesLeft++;
	}
	
	private void drawShopScreen() {
		int textX = GameConfig.SCREEN_X/2;
		int textY = GameConfig.SCREEN_Y/4;
		
		drawEngine.drawText(16, "Welcome to the shop, press F to leave.", textX, textY, 0);

		int shopX = 150;
		int shopY = GameConfig.SCREEN_Y/2 - 75;
		
		/* Black hole */
		BlackHole blackhole = new BlackHole(new PVector(shopX, shopY));
		blackhole.display(drawEngine);
		drawEngine.drawText(12, "[1] Buy a blackhole for " + GameConfig.BLACK_HOLE_COST, shopX + 115, shopY, 0);
		
		/* Force field */
		parent.ellipseMode(PConstants.CENTER);
		parent.fill(0, 0, 100, 100);
		parent.ellipse(shopX + 350, shopY, 50, 50);
		drawEngine.drawText(12, "[2] Buy a forcefield for " + GameConfig.FORCEFIELD_COST, shopX + 465, shopY, 0);
		
		/* Missile */
		Missile missile = new Missile(shopX, shopY + 100, 0, 0, parent.color(255, 0, 0));
		missile.display(drawEngine);
		drawEngine.drawText(12, "[3] Buy a missile for " + GameConfig.MISSILE_COST, shopX + 90, shopY + 100, 0);
		
		
		/* City */
		City city = new City(shopX + 350, shopY + 100);
		city.display(drawEngine);
		drawEngine.drawText(12, "[4] Rebuild a city for " + GameConfig.CITY_COST, shopX + 450, shopY + 100, 0);
		
		
		/* Player explosions */
		if (!context.friendlyExplosions) {
			drawEngine.drawText(12, "[5] Missile explosions don't destroy your own cities.", shopX+70, shopY + 165, parent.color(20, 20, 250));
		} else {
			drawEngine.drawText(12, "[5] Upgrade purchased!", shopX + 90, shopY + 165, parent.color(20, 20, 250));
		}
		
		/* End game */
		drawEngine.drawText(16, "To beat the game, you must have all cities alive and pay " + GameConfig.END_GAME_COST + " score", 
				textX, textY+300, 0);
		drawEngine.drawText(16, "Press [0] to pay", textX, textY+325, 0);
		
	}

	@Override
	public void updateScore(int score) {
		// TODO Auto-generated method stub

	}

}
