package game.states.impl;

import game.DrawEngine;
import game.GameConfig;
import game.Level;
import game.states.GameContext;
import game.states.GameInput;
import game.states.GameState;
import objects.buildings.Cannon;
import objects.particles.BlackHoleMissile;
import objects.particles.Meteor;
import objects.particles.Missile;
import processing.core.PConstants;

public class PlayingState extends GameState {
	
	
	public PlayingState(GameContext context, DrawEngine drawEngine) {
		super(context, drawEngine);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void display() {
		displayGame();
	}

	@Override
	public GameState update() {

		if (context.level.finished && context.meteorCount <= 0) {
			updateScore(context.info.citiesLeft * GameConfig.CITY_SURVIVE_SCORE);
			updateScore(context.info.missilesLeft * GameConfig.MISSILE_LEFT_SCORE);
	    	return new EndOfWaveState(context, drawEngine);
	    } 
		
		if (!context.level.finished) {
		    spawnMeteors(context.level);
	    }
		
    	runningStep();
	    
	    GameState s = checkGameOver();
	    
	    return s;
	}

	@Override
	public GameState handleInput(GameInput input) {
	    float xStart = input.mouseX;
	    float yStart = input.mouseY;
	
	    Cannon cannon = context.getClosestCannon((int)xStart, (int)yStart);
	    //particles.add(cannon.shoot(new PVector(xStart, yStart)))
	    
	    if (yStart < GameConfig.GROUND_HEIGHT) {
		    if (input.mouseButton == PConstants.LEFT && context.info.missilesLeft > 0) {
		    	context.info.missilesLeft--;
		    	context.missiles.add(new Missile(parent, cannon.position.x, cannon.position.y, xStart, yStart));
		    } 
		    else if (input.mouseButton == PConstants.RIGHT && context.info.blackholesLeft > 0) {
		    	context.info.blackholesLeft--;
		    	context.bhms.add(new BlackHoleMissile(parent, cannon.position.x, cannon.position.y, xStart, yStart));
		    }
	    }
	    
	    return this;
	}
	
	
    private void spawnMeteors(Level level) {
    	if ((int)parent.random(0, 10) == 1 && level.numMeteors > 0) {
    	    Meteor meteor = new Meteor((int)parent.random(0, GameConfig.SCREEN_X), 0, parent.random(-2f, 2f), 0f, parent.random(0.1f, 0.5f));
    	    level.spawnMeteor();
    	    context.meteors.add(meteor);
    	    context.physEngine.registerNewParticle(meteor);
    	}
    }

	@Override
	public void updateScore(int score) {
		this.context.info.score += score;		
	}

}
