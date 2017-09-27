package physics.forces;

import java.util.ArrayList;

import objects.particles.Particle;


public class ForceRegistry {

	public ArrayList<ForceRegistration> registrations;
	
	public ForceRegistry() {
		registrations = new ArrayList<ForceRegistration>();
	}
	
	public void register(Particle particle, ForceGenerator forceGenerator) {
		registrations.add(new ForceRegistration(particle, forceGenerator));		
	}
	
	public void updateForces() {
		for (ForceRegistration fr : registrations) {
			fr.forceGenerator.updateForce(fr.particle);
		}		
	}
	
	public void clear() {
		registrations.clear();
	}
	
}
