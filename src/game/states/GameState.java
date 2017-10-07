package game.states;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Function;

import game.DrawEngine;
import game.GameConfig;
import game.IDrawable;
import game.Level;
import game.states.impl.GameOverState;
import game.states.impl.StartState;
import objects.buildings.City;
import objects.particles.BlackHole;
import objects.particles.BlackHoleMissile;
import objects.particles.Bomber;
import objects.particles.Explosion;
import objects.particles.Meteor;
import objects.particles.Missile;
import objects.particles.Particle;
import physics.PhysicsStep;
import processing.core.PApplet;

/**
 * This class represents a state of the game (i.e, playing, game over, end of wave etc.)
 * This allows the GameController to call the same update() and handleInput() functions for every state 
 * and let each state's implementation decide what to do on those steps.
 * 
 * update() and handleInput() return the next state after the update or input. 
 * @author sy35
 *
 */
public abstract class GameState {

	public GameContext context;
	public DrawEngine drawEngine;

	protected PApplet parent;
	
	public GameState(GameContext context, DrawEngine drawEngine) {
		this.context = context;
		this.drawEngine = drawEngine;
		
		this.parent = drawEngine.parent;
	}
	
	public abstract void display();
	
	/**
	 * Updates that happen on each frame in the state.
	 * @return the next state of the game
	 */
	public abstract GameState update();
	
	/**
	 * Handle player input.
	 * @param input - Player input which contains mouse coordinates, mouse click and key pressed.
	 * @return the next state of the game.
	 */
	public abstract GameState handleInput(GameInput input);
	
	
	public abstract void updateScore(int score);
	

/*
 * Common GameState functions.
 * These are functions that multiple GameStates use.
 */
	
    protected GameState checkGameOver() {
    	for (City c : context.cities) {
    		if (c.destroyed) context.cityCount--;
    	}
    	
    	context.info.citiesLeft = context.cityCount;
    	
		if (context.cityCount <= 0) {
			return new GameOverState(context, drawEngine);
		}
		else {
			context.cityCount = context.cities.size();
			return this;
		}
    }
	
	
	/**
	 * Display most of the game's objects and score information.
	 */
	protected void displayGame() {
		drawEngine.displayGame(context);
		drawEngine.displayInfo(context.info);
	}
	
	
	/**
	 * Update step of the running game to update object positions.
	 */
	protected void runningStep() {
    	PhysicsStep meteorStep = Meteor.getStep(context);
    	PhysicsStep missileStep = Missile.getStep(context);
    	PhysicsStep explosionStep = Explosion.getStep(context);
    	PhysicsStep blackholeMissileStep = BlackHoleMissile.getStep(context);
    	PhysicsStep bomberStep = Bomber.getStep(this.getClass(), context);

        context.physicsEngine.step(meteorStep, missileStep, explosionStep, blackholeMissileStep, bomberStep);

        if (context.level.levelNumber > GameConfig.METEOR_SPLIT_STARTING_LEVEL) splitMeteors();
        
	    destroyObjects();
	}
	
	
	private void splitMeteors() {
		Random r = new Random();
		ArrayList<Meteor> newMeteors = new ArrayList<Meteor>();
		Iterator<Meteor> it = context.meteors.iterator();
		
		while (it.hasNext()) {
			Meteor m = it.next();
			
			if (m.radius >= GameConfig.METEOR_SPLIT_MIN_RADIUS && m.position.y < GameConfig.METEOR_SPLIT_MAX_HEIGHT && m.position.y > GameConfig.METEOR_SPLIT_MIN_HEIGHT) {
				if (r.nextInt(GameConfig.METEOR_SPLIT_RATE) == 0) {
					int numChildren = 2 + r.nextInt(1 + context.level.levelNumber/5);
					for (int i = 0; i < numChildren; i++) {
						Meteor child = new Meteor(m.position.x, m.position.y, parent.random(-2f, 2f), parent.random(-0.5f, -2f), m.mass/2f);
						newMeteors.add(child);
					}
					context.meteorCount--;
					it.remove();
				}
			}
		}
		
		for (Meteor child : newMeteors) {
			context.meteorCount++;
    	    context.meteors.add(child);
    	    context.physicsEngine.registerNewParticle(child);
		}
	}

	/**
	 * Clean up of each step to destroy any objects and forces that should be destroyed after they've bee updated.
	 */
	protected void destroyObjects() {
	    destroy(m -> (m.position.y > GameConfig.GROUND_HEIGHT), context.meteors.iterator(), true);
	    destroy(m -> m.destroyed == true, context.missiles.iterator(), true);
	    destroy(e -> e.lifespan <= 0, context.explosions.iterator(), true);

	    destroy(m -> (m.position.x + m.radius < 0 || m.position.x - m.radius > GameConfig.SCREEN_X || m.position.y + m.radius < 0), context.meteors.iterator(), false);
	    
	    /* Destroy meteors in black holes, but do not leave explosions */
	    for (BlackHole bh : context.blackholes)	destroy(m -> m.checkCollision(bh) != null, context.meteors.iterator(), false);
	    
	    /* Remove from the list without adding explosions */
	    remove(bhm -> bhm.destroyed == true, context.bhms.iterator());
	    remove(bh -> bh.lifespan <= 0, context.blackholes.iterator());
	    remove(ff -> ff.lifespan <= 0, context.forcefields.iterator());
    }
	
    private <T extends Particle> void destroy(Function<T, Boolean> filter, Iterator<T> it, boolean explode) {
    	while (it.hasNext()) {
    		T object = it.next();
    	    if(filter.apply(object)) {
    	    	it.remove();
    	    	Explosion ex = object.destroy();
    	    	if (ex != null && explode) context.explosions.add(ex);
    	    	
    	    	/* Update score for destroyed meteors */
    	    	if (object.getClass().equals(Meteor.class)) {
    	    		context.meteorCount--;
    	    		if (explode) updateScore(GameConfig.METEOR_EXPLODE_SCORE);
    	    		else updateScore(GameConfig.METEOR_REMOVED_SCORE);
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
    
    
    protected StartState newGame() {
		GameContext newGame = new GameContext();
		return new StartState(newGame, drawEngine);
    }
}
