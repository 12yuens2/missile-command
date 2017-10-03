package objects.particles;

import java.util.function.Function;

import game.states.GameContext;
import objects.buildings.City;
import physics.Collision;
import physics.PhysicsStep;
import physics.forces.impl.Explosive;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Explosion extends Particle{
    
	public static final int EXPLOSION_LIFESPAN = 20;

    public int lifespan = EXPLOSION_LIFESPAN;
    public boolean friendly;
    
    public Explosion(float posX, float posY, float startRadius, int lifespan, boolean friendly) {
        super(posX, posY, 0, 0, startRadius, 0);
        this.lifespan = lifespan;
        this.friendly = friendly;
    }
    
    public Explosion(PVector position, float radius, boolean friendly) {
		this(position.x, position.y, radius, EXPLOSION_LIFESPAN, friendly);
	}
    
    public Explosion(PVector position, float radius, int lifespan, boolean friendly) {
    	this(position.x, position.y, radius, lifespan, friendly);
    }
    
    public Explosive getForce() {
    	return new Explosive(position, radius);
    }

	public void display(PApplet parent) {
        if (lifespan >= 0) {
            radius += 1;
            lifespan--;
            
            float size = radius * 2;
            parent.ellipseMode(PConstants.CENTER);
            parent.fill(255, 127, 80, 200);
            parent.ellipse(position.x, position.y, size, size);
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
}