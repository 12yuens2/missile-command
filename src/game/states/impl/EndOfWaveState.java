package game.states.impl;

import game.DrawEngine;
import game.GameConfig;
import game.GameEngine;
import game.states.GameContext;
import game.states.GameInput;
import game.states.GameState;
import objects.particles.BlackHoleMissile;
import objects.particles.Explosion;
import objects.particles.Meteor;
import objects.particles.Missile;
import physics.PhysicsStep;
import processing.core.PConstants;

public class EndOfWaveState extends GameState{

	public EndOfWaveState(GameContext context, DrawEngine drawEngine) {
		super(context, drawEngine);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void display() {
		drawEngine.displayGame(context.meteors, context.missiles, context.bhms, context.blackholes, context.explosions, 
				context.cities, context.cannons);
		
		drawEngine.drawText(32, "Wave " + context.level.levelNumber + " finished.", GameConfig.SCREEN_X/2, GameConfig.SCREEN_Y/2, 0);
	}

	@Override
	public GameState update() {
    	PhysicsStep meteorStep = Meteor.getStep(context.meteors, context.blackholes, context.physEngine.forceRegistry);
    	PhysicsStep missileStep = Missile.getStep(context.missiles, context.meteors, context.explosions);
    	PhysicsStep explosionStep = Explosion.getStep(context.explosions, context.meteors, context.cities, context.physEngine.forceRegistry);
    	PhysicsStep blackholeMissileStep = BlackHoleMissile.getStep(context.bhms, context.blackholes);

        context.physEngine.step(meteorStep, missileStep, explosionStep, blackholeMissileStep);
        
		return this;
	}

	@Override
	public GameState handleInput(GameInput input) {
		if (input.mouseButton == PConstants.LEFT) {
	    	context.level.next();
			context.meteorCount = context.level.numMeteors;
			return new PlayingState(context, drawEngine);
		}
		else return this;
	}

}
