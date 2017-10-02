package physics.forces;

import java.util.ArrayList;
import java.util.Iterator;

import objects.particles.Particle;
import physics.forces.impl.Attractive;


public class ForceRegistry {

	public ArrayList<ForceRegistration> registrations;
	
	public ForceRegistry() {
		registrations = new ArrayList<ForceRegistration>();
	}
	
	public void register(Particle particle, ForceGenerator forceGenerator) {
		registrations.add(new ForceRegistration(particle, forceGenerator));		
	}
	
	public void updateForces() {
		for (Iterator<ForceRegistration> it = registrations.iterator(); it.hasNext();) {
			ForceRegistration fr = it.next();
			Particle p = fr.particle;
			
			if (p.destroyed || fr.forceGenerator.lifespan <= 0) {
				it.remove();
			}
			else fr.forceGenerator.updateForce(p);
		}	
	}
	
	public void clear() {
		registrations.clear();
	}
	
}
