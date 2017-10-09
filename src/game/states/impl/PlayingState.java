package game.states.impl;

import java.util.Random;

import game.DrawEngine;
import game.GameConfig;
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
	
	Random random;
	
	public PlayingState(GameContext context, DrawEngine drawEngine) {
		super(context, drawEngine);
		
		this.random = new Random();
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
		    spawnMeteor();
		    spawnBomber();
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
		    	context.missiles.add(new Missile(parent, cannon.position.x, cannon.position.y, mouseX, mouseY, context.friendlyExplosions));
		    } 
		    else if (input.mouseButton == PConstants.RIGHT && context.info.blackholesLeft > 0) {
		    	context.info.blackholesLeft--;
		    	context.bhms.add(new BlackHoleMissile(parent, cannon.position.x, cannon.position.y, mouseX, mouseY));
		    }
		    else if (input.mouseButton == PConstants.CENTER && context.info.forcefieldsLeft > 0) {
		    	context.info.forcefieldsLeft--;
		    	context.forcefields.add(new ForceField(mouseX, mouseY));
		    }
	    }
	    
	    return this;
	}
	
    private void spawnMeteor() {
    	if (random.nextInt(GameConfig.METEOR_SPAWN_RATE_INV) == 0) {
    		float xPos = parent.random(100, GameConfig.SCREEN_X - 100);
    		float yPos = 0;
    		float xVel = parent.random(-2f, 2f);
    		float yVel = parent.random(0, 3f);
    		float mass = parent.random(0.1f, 0.5f) + context.level.meteorMassBase;
    		
    	    Meteor meteor = new Meteor(xPos, yPos, xVel, yVel, mass);
    	    context.level.spawnMeteor();
    	    context.meteors.add(meteor);
    	    context.physicsEngine.registerNewParticle(meteor);
    	}
    }
	
	private void spawnBomber() {
    	if ((int)parent.random(0, 10) == 1 && context.level.numBombers > 0) {
    		Bomber bomber = new Bomber(0, parent.random(50, 200), 1f, parent.random(-0.2f, 0.2f), 0f, 0);
    		context.level.spawnBomber();
    	    context.bombers.add(bomber);
    	    /* For simplicity, bombers are not affected by any forces, so do not add to the force registry. */
    	}
	}

	@Override
	public void updateScore(int score) {
		this.context.info.score += score;		
	}

}
