package objects.particles;

import physics.forces.impl.Repulsive;
import processing.core.PApplet;

public class ForceField extends Particle {

	private static final int FORCEFIELD_MASS = 100;
	private static final int FORCEFIELD_LIFESPAN = 80;
	private static final int FORCEFIELD_RADIUS = 50;
	
	
	public int lifespan;
	public Repulsive repulsiveForce;
	
	public ForceField(float xPos, float yPos) {
		super(xPos, yPos, 0, 0, FORCEFIELD_RADIUS, FORCEFIELD_MASS);
		this.lifespan = FORCEFIELD_LIFESPAN;

		repulsiveForce = new Repulsive(this);
	}

	@Override
	public void display(PApplet parent) {
		repulsiveForce.lifespan--;
		lifespan--;
		
		parent.ellipseMode(parent.CENTER);
		parent.fill(0, 0, 100, 100);
		parent.ellipse(position.x, position.y, radius, radius);
		
		radius += 15f;
	}

	@Override
	public Explosion destroy() {
		// TODO Auto-generated method stub
		return null;
	}

}
