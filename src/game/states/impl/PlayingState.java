package game.states.impl;

import game.DrawEngine;
import game.GameConfig;
import game.Level;
import game.states.GameContext;
import game.states.GameInput;
import game.states.GameState;
import objects.buildings.Cannon;
import objects.particles.BlackHoleMissile;
import objects.particles.Bomber;
import objects.particles.ForceField;
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
			int bonusCityScore = context.info.citiesLeft * GameConfig.CITY_SURVIVE_SCORE;
			int bonusMissileScore = context.info.missilesLeft * GameConfig.MISSILE_LEFT_SCORE;

	    	return new EndOfWaveState(context, drawEngine, bonusCityScore + bonusMissileScore);
	    } 
		
		if (!context.level.finished) {
		    spawnMeteors(context.level);
		    spawnBomber(context.level);
	    }
		
    	runningStep();
	    
	    GameState s = checkGameOver();
	    
	    return s;
	}

	@Override
	public GameState handleInput(GameInput input) {
	    float mouseX = input.mouseX;
	    float mouseY = input.mouseY;
	
	    Cannon cannon = context.getClosestCannon((int)mouseX, (int)mouseY);
	    
	    if (mouseY < GameConfig.GROUND_HEIGHT) {
		    if (input.mouseButton == PConstants.LEFT && context.info.missilesLeft > 0) {
		    	context.info.missilesLeft--;
		    	context.missiles.add(new Missile(parent, cannon.position.x, cannon.position.y, mouseX, mouseY));
		    } 
		    else if (input.mouseButton == PConstants.RIGHT && context.info.blackholesLeft > 0) {
		    	context.info.blackholesLeft--;
		    	context.bhms.add(new BlackHoleMissile(parent, cannon.position.x, cannon.position.y, mouseX, mouseY));
		    }
		    else if (input.mouseButton == PConstants.CENTER) {
		    	context.info.forcefieldsLeft--;
		    	context.forcefields.add(new ForceField(mouseX, mouseY));
		    }
	    }
	    
	    return this;
	}
	
	
	
    private void spawnMeteors(Level level) {
    	if ((int)parent.random(0, 10) == 1) {
    	    Meteor meteor = new Meteor(parent.random(100, GameConfig.SCREEN_X - 100), 0, parent.random(-2f, 2f), 0f, parent.random(0.1f, 0.5f));
    	    level.spawnMeteor();
    	    context.meteors.add(meteor);
    	    context.physicsEngine.registerNewParticle(meteor);
    	}
    }
    
	private void spawnBomber(Level level) {
    	if ((int)parent.random(0, 10) == 1 && level.numBombers > 0) {
    		Bomber bomber = new Bomber(0, parent.random(50, 200), 1f, parent.random(-0.2f, 0.2f), 0f, 0);
    		level.spawnBomber();
    	    context.bombers.add(bomber);
    	    /* For simplicity, bombers are not affected by any forces, so do not add to the force registry. */
    	}
	}

	@Override
	public void updateScore(int score) {
		this.context.info.score += score;		
	}

}
