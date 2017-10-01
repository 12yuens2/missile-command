package objects.particles;

import java.util.ArrayList;
import java.util.function.Function;

import physics.PhysicsStep;
import processing.core.PApplet;

public class BlackHoleMissile extends Missile{

	public BlackHoleMissile(PApplet parent, float xPos, float yPos, float destX, float destY) {
		super(parent, xPos, yPos, destX, destY);
	}

	
	public static PhysicsStep getStep(ArrayList<BlackHoleMissile> bhms, ArrayList<BlackHole> blackholes) {
		return new PhysicsStep(bhms, new Function<BlackHoleMissile, Void>() {
			
			@Override
			public Void apply(BlackHoleMissile bhm) {
	        	if (bhm.destroyed) {
	        		blackholes.add(new BlackHole(bhm.position));
	        	}
	        	else {
	        		bhm.integrate();
	        	}
	        	
	        	return null;
			}
		});
	}
	
}
