package objects.particles;

import java.util.function.Function;

import game.DrawEngine;
import game.GameConfig;
import game.states.GameContext;
import physics.Collision;
import physics.PhysicsStep;

public class Meteor extends Particle {
    
    public static final int METEOR_RADIUS_BASE = 20;
    
    public Meteor(float xPos, float yPos, float xVel, float yVel, float mass) {
        super(xPos, yPos, xVel, yVel, METEOR_RADIUS_BASE * mass, mass);
    }
    
    public Meteor(float xPos, float yPos, float xVel, float yVel, float radius, float mass) {
    	super(xPos, yPos, xVel, yVel, radius, mass);
    }
    
    public Meteor(float xPos, float yPos, float xVel, float yVel, float radius, float mass, int col) {
    	super(xPos, yPos, xVel, yVel, radius, mass);
    	this.col = col;
    }
	
	@Override
    public void display(DrawEngine drawEngine) {
        float size = radius * 2;
        drawEngine.drawEllipse(col, position.x, position.y, size, size);
    }

	@Override
	public Explosion destroy() {
		destroyed = true;
		position.y = GameConfig.GROUND_HEIGHT;
		return new Explosion(position, radius, false);
	}
	
	
	public static PhysicsStep getStep(GameContext context) {
		return new PhysicsStep(context.meteors, new Function<Meteor, Void>() {
			
			@Override
			public Void apply(Meteor me) {
				me.integrate();
				
				for (Meteor m : context.meteors) {
					Collision c = m.checkCollision(me);
					if (c != null) {
						context.physicsEngine.collisions.add(c);
					}
				}
				
	        	for (BlackHole bh : context.blackholes) {
	        		context.physicsEngine.forceRegistry.register(me, bh.attractionForce);
	        	}
	        	
	        	for (ForceField ff : context.forcefields) {
	        		context.physicsEngine.forceRegistry.register(me, ff.repulsiveForce);
	        	}
				return null;
			}
    	});
	}
	
    
}