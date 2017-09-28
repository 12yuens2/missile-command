package physics.forces.impl;

import objects.particles.Particle;
import physics.forces.ForceGenerator;

public class Attraction extends ForceGenerator {

	public Particle attractor;
	
	public Attraction(Particle attractor) {
		this.attractor = attractor;
	}
	
	@Override
	public void updateForce(Particle atractee) {
		// TODO Auto-generated method stub
		
	}

}
