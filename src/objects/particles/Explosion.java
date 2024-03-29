package objects.particles;

import java.util.function.Function;

import game.DrawEngine;
import game.states.GameContext;
import objects.buildings.City;
import physics.Collision;
import physics.PhysicsStep;
import physics.forces.impl.Explosive;

import processing.core.PVector;

public class Explosion extends Particle {
    
	public static final int EXPLOSION_LIFESPAN = 20;

	public float initialRadius;
    public int lifespan = EXPLOSION_LIFESPAN;
    public boolean friendly;
    
    public Explosion(float posX, float posY, float startRadius, int lifespan, boolean friendly) {
        super(posX, posY, 0, 0, startRadius, 0);
        this.initialRadius = startRadius;
        this.lifespan = lifespan;
        this.friendly = friendly;
    }
    
    public Explosion(PVector position, float radius, boolean friendly) {
		this(position.x, position.y, radius, EXPLOSION_LIFESPAN, friendly);
	}
    
    public Explosion(PVector position, float radius, int lifespan, boolean friendly) {
    	this(position.x, position.y, radius, lifespan, friendly);
    }

    @Override
	public void display(DrawEngine drawEngine) {
        if (lifespan >= 0) {
            radius += initialRadius/10f;
            lifespan--;
            
            int col;
            if (friendly) col = drawEngine.parent.color(255, 30, 30, 210);
            else col = drawEngine.parent.color(255, 127, 80, 200);
            
            drawEngine.drawEllipse(col, position.x, position.y, radius * 2, radius * 2);
        }   
    }

	@Override
	public Explosion destroy() {
		/* Do not create new explosion when an Explosion is destroyed */
		return null;
	}
	
	
	
	public static PhysicsStep getStep(GameContext context) {
		return new PhysicsStep(context.explosions, new Function<Explosion, Void>() {
			
			@Override
			public Void apply(Explosion e) {
				for (Meteor me : context.meteors) {
					Collision collision = me.checkCollision(e);
					if (collision != null) context.physicsEngine.forceRegistry.register(me, e.getForce());
				}
				
				for (City city : context.cities) {
					float collideDistance = city.radius + e.radius;
					float distance = PVector.dist(city.position, e.position);
					if (distance < collideDistance && !e.friendly) city.destroy();
				}
				
				return null;
			}
		});
	}
	
    public Explosive getForce() {
    	return new Explosive(position, radius);
    }
}