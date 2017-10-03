package objects.particles;

import java.util.ArrayList;
import java.util.function.Function;

import game.GameConfig;
import game.states.GameContext;
import physics.PhysicsStep;
import physics.forces.ForceRegistry;
import processing.core.PApplet;

public class Meteor extends Particle {
    
    public static final int METEOR_RADIUS = 10;
    
    public Meteor(float xPos, float yPos, float xVel, float yVel, float mass) {
        super(xPos, yPos, xVel, yVel, METEOR_RADIUS, mass);
    }
    
    public Meteor(float xPos, float yPos, float xVel, float yVel, float mass, int col) {
    	super(xPos, yPos, xVel, yVel, METEOR_RADIUS, mass);
    	this.col = col;
    }
	
	@Override
    public void display(PApplet parent) {
        float size = radius * 2;
        
        parent.ellipseMode(parent.CENTER);
        parent.fill(col);
        parent.ellipse(position.x, position.y, size, size);
    }

	@Override
	public Explosion destroy() {
		destroyed = true;
		position.y = GameConfig.GROUND_HEIGHT;
		return new Explosion(position, radius);
	}
	
	
	public static PhysicsStep getStep(GameContext context) {
		return new PhysicsStep(context.meteors, new Function<Meteor, Void>() {
			
			@Override
			public Void apply(Meteor me) {
				me.integrate();
				
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