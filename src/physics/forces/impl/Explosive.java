package physics.forces.impl;

import objects.particles.Particle;
import physics.forces.ForceGenerator;
import processing.core.PApplet;
import processing.core.PVector;

public class Explosive extends ForceGenerator{

	private static final int EXPLOSIVE_FORCE = 50;
	
	public PVector position;
	public float radius;
	
	public Explosive(PVector position, float radius) {
		this.position = position;
		this.radius = radius;
	}
	
	@Override
	public void updateForce(Particle particle) {
		
		if (PVector.dist(particle.position, position) < radius + particle.radius) {
			PVector impulseDirection = PVector.sub(position, particle.position).mult(-1);
			float impulseMag = EXPLOSIVE_FORCE * (1f/impulseDirection.mag()) * (1f/impulseDirection.mag());
	
			particle.addForce(impulseDirection.mult(impulseMag));
		}
	}


}
