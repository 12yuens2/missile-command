package physics.forces.impl;

import objects.particles.Particle;
import physics.BlackHole;
import physics.forces.ForceGenerator;
import processing.core.PVector;

public class Attraction extends ForceGenerator {

	public static final float GRAV_CONSTANT = 2f;
	
	public Particle attractor;
	
	public Attraction(Particle attractor) {
		this.attractor = attractor;
		this.lifespan = BlackHole.BLACKHOLE_LIFESPAN;
	}
	
	@Override
	public void updateForce(Particle attractee) {
		PVector force = PVector.sub(attractor.position, attractee.position);
		float distance = force.mag();
		force.normalize();
		
		float mag = (GRAV_CONSTANT * attractor.mass * attractee.mass) / (distance * distance);
		force.mult(-mag);
		
		attractee.addForce(force);		
	}

}
