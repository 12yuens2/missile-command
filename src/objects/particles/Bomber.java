package objects.particles;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

import game.Level;
import game.states.GameContext;
import game.states.GameState;
import game.states.impl.PlayingState;
import physics.PhysicsEngine;
import physics.PhysicsStep;
import physics.forces.ForceRegistry;
import processing.core.PApplet;

public class Bomber extends Particle {

	public static final int bombSpawn = 150;
	
	public Bomber(float xPos, float yPos, float xVel, float yVel, float radius, float mass) {
		super(xPos, yPos, xVel, yVel, radius, 1);
	}

	@Override
	public void display(PApplet parent) {
		parent.ellipseMode(parent.CENTER);
        parent.fill(255, 127, 80, 200);
        parent.ellipse(position.x, position.y, 50, 20);
	}

	@Override
	public Explosion destroy() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void spawnBomb(ArrayList<Meteor> meteors, PhysicsEngine physicsEngine, Random r) {
		float xVel = r.nextFloat()-1f;
		float yVel = 3f + r.nextFloat() * 4f;
		
		Meteor meteor = new Meteor(position.x, position.y, xVel, yVel, 10, 0);
		meteors.add(meteor);
		physicsEngine.registerNewParticle(meteor);
	}

	public static PhysicsStep getStep(Class<? extends GameState> stateClass, GameContext context) {
		return new PhysicsStep(context.bombers, new Function<Bomber, Void>() {
			
			@Override
			public Void apply(Bomber b) {
				b.integrate();
				
				Random r = new Random();
				if (r.nextInt(bombSpawn) == 0 && stateClass.equals(PlayingState.class)) b.spawnBomb(context.meteors, context.physicsEngine, r);
				
				return null;
			}
			
		});
	}

}
