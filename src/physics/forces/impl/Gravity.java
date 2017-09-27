package physics.forces.impl;

import objects.particles.Particle;
import physics.forces.ForceGenerator;
import processing.core.PVector;

public class Gravity extends ForceGenerator{

	private static final PVector gravity = new PVector(0f, 0.1f);
	
	@Override
	public void updateForce(Particle particle) {
		PVector resultingForce = gravity.copy().mult(particle.mass);
		particle.addForce(resultingForce);		
	}

}
