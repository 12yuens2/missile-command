package game.states;

import java.util.Iterator;
import java.util.function.Function;

import game.DrawEngine;
import game.GameConfig;
import game.IDrawable;
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
	
	public abstract GameState update();
	
	public abstract GameState handleInput(GameInput input);
	
	public abstract void updateScore(int score);
	
	
	
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
	    
	    destroyObjects();
	}
	
	protected void destroyObjects() {
	    destroy(m -> (m.position.y > GameConfig.GROUND_HEIGHT), context.meteors.iterator(), true);
	    destroy(m -> m.destroyed == true, context.missiles.iterator(), true);
	    destroy(e -> e.lifespan <= 0, context.explosions.iterator(), true);

	    destroy(m -> (m.position.x + m.radius < 0 || m.position.x - m.radius > GameConfig.SCREEN_X || m.position.y + m.radius < 0), context.meteors.iterator(), false);
	    
	    for (BlackHole bh : context.blackholes)	destroy(m -> m.checkCollision(bh) != null, context.meteors.iterator(), false);
	    
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
