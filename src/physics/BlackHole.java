package physics;

import objects.particles.Particle;
import physics.forces.impl.Attraction;
import processing.core.PApplet;
import processing.core.PVector;

public class BlackHole extends Particle {

	public static final int BLACKHOLE_MASS = 100;
	public static final int BLACKHOLE_RADIUS = 50;
	public static final int BLACKHOLE_LIFESPAN = 100;
	
	public int lifespan;
	public Attraction attractionForce;
	
	public BlackHole(PVector position) {
		super(position.x, position.y, 0, 0, BLACKHOLE_RADIUS, BLACKHOLE_MASS);
		this.lifespan = BLACKHOLE_LIFESPAN;
		
		attractionForce = new Attraction(this);
	}

	@Override
	public void display(PApplet parent) {
		attractionForce.lifespan--;
		lifespan--;
		parent.ellipseMode(parent.CENTER);
		parent.fill(0);
		parent.ellipse(position.x, position.y, radius, radius);
	}

	@Override
	public Explosion destroy() {
		// TODO Auto-generated method stub
		return null;
	}

}
