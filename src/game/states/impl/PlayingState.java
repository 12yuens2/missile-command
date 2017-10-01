package game.states.impl;

import java.util.Iterator;
import java.util.function.Function;

import game.DrawEngine;
import game.GameConfig;
import game.IDrawable;
import game.Level;
import game.states.GameContext;
import game.states.GameInput;
import game.states.GameState;
import objects.buildings.Cannon;
import objects.buildings.City;
import objects.particles.BlackHole;
import objects.particles.BlackHoleMissile;
import objects.particles.Explosion;
import objects.particles.Meteor;
import objects.particles.Missile;
import objects.particles.Particle;
import physics.PhysicsStep;

public class PlayingState extends GameState {
	
	
	public PlayingState(GameContext context, DrawEngine drawEngine) {
		super(context, drawEngine);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void display() {
		drawEngine.displayGame(context.meteors, context.missiles, context.bhms, context.blackholes, context.explosions, 
				context.cities, context.cannons);
		
	}

	@Override
	public GameState update() {

		if (context.level.finished && context.meteorCount <= 0) {
	    	return new EndOfWaveState(context, drawEngine);
	    } 
		
		if (!context.level.finished) {
		    spawnMeteors(context.level);
	    }
		
    	PhysicsStep meteorStep = Meteor.getStep(context.meteors, context.blackholes, context.physEngine.forceRegistry);
    	PhysicsStep missileStep = Missile.getStep(context.missiles, context.meteors, context.explosions);
    	PhysicsStep explosionStep = Explosion.getStep(context.explosions, context.meteors, context.cities, context.physEngine.forceRegistry);
    	PhysicsStep blackholeMissileStep = BlackHoleMissile.getStep(context.bhms, context.blackholes);

        context.physEngine.step(meteorStep, missileStep, explosionStep, blackholeMissileStep);
	    
	    destroyObjects();
	    
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
		    if (input.mouseButton == parent.LEFT) {
		    	context.missiles.add(new Missile(parent, cannon.position.x, cannon.position.y, xStart, yStart));
		    } 
		    else if (input.mouseButton == parent.RIGHT) {
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
    
    private void destroyObjects() {
	    destroy(m -> (m.position.y > GameConfig.GROUND_HEIGHT), context.meteors.iterator(), true);
	    destroy(m -> m.destroyed == true, context.missiles.iterator(), true);
	    destroy(e -> e.lifespan <= 0, context.explosions.iterator(), true);

	    destroy(m -> (m.position.x + m.radius < 0 || m.position.x - m.radius > GameConfig.SCREEN_X || m.position.y + m.radius < 0), context.meteors.iterator(), false);
	    
	    for (BlackHole bh : context.blackholes)	destroy(m -> m.checkCollision(bh) != null, context.meteors.iterator(), false);
	    
	    remove(bhm -> bhm.destroyed == true, context.bhms.iterator());
	    remove(bh -> bh.lifespan <= 0, context.blackholes.iterator());
    }

    private <T extends Particle> void destroy(Function<T, Boolean> filter, Iterator<T> it, boolean explode) {
    	while (it.hasNext()) {
    		T object = it.next();
    	    if(filter.apply(object)) {
    	    	it.remove();
    	    	Explosion ex = object.destroy();
    	    	if (ex != null && explode) context.explosions.add(ex);
    	    	if (object.getClass().equals(Meteor.class)) {
    	    		context.meteorCount--;
    	    		if (explode) context.score += GameConfig.METEOR_EXPLODE_SCORE;
    	    		else context.score += GameConfig.METEOR_REMOVED_SCORE;
    	    	}
    	    }
    	}
    }
    
    private <T extends IDrawable> void remove(Function<T, Boolean> filter, Iterator<T> it) {
    	while (it.hasNext()) {
    		T object = it.next();
    		if (filter.apply(object)) {
    			it.remove();
    		}
    	}
    }
    
    private GameState checkGameOver() {
    	for (City c : context.cities) {
    		if (c.destroyed) context.cityCount--;
    	}
		if (context.cityCount <= 0) {
			return new GameOverState(context, drawEngine);
		}
		else {
			context.cityCount = context.cities.size();
			return this;
		}
    }

}
