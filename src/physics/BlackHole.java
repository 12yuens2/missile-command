package physics;

import objects.particles.Particle;
import processing.core.PApplet;
import processing.core.PVector;

public class BlackHole extends Particle {

	public static final int BLACKHOLE_MASS = 10000;
	public static final int BLACKHOLE_RADIUS = 30;
	
	public int lifespan;
	
	public BlackHole(PVector position) {
		super(position.x, position.y, 0, 0, BLACKHOLE_RADIUS, BLACKHOLE_MASS);
		this.lifespan = 1000;
	}

	@Override
	public void display(PApplet parent) {
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
