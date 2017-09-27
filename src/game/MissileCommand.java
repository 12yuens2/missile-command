package game;
import objects.buildings.Cannon;
import objects.particles.Missile;
import processing.core.PApplet;

public class MissileCommand extends PApplet {
	
	public static void main(String[] args) {
		PApplet.main("game.MissileCommand");
	}
	


	public Cannon cannon;
	public float xStart, yStart, xEnd, yEnd;
	public int tick, currCannon;
	
	
	public GameEngine gameEngine;
	
	public void settings() {
		size(800, 600);

	}
	
	public void setup() {
	    gameEngine = new GameEngine(this);
//		frameRate(10);
	    spawnBuildings();
	}

	private void spawnBuildings() {

	}
	
	public void draw() {
		gameEngine.step();
    
    
    //    if (p.getClass().equals(Meteor.class)) {
    //        for (City city : gameScene.cities) {
    //            float destroyDistance = city.radius + p.radius;
    //            PVector distanceBetween = PVector.sub(city.position, p.position);
                
    //            if (distanceBetween.mag() < destroyDistance) city.destroyed = true;
    //        }
    //    }
        
    //    if (p.getClass().equals(Missile.class) && ((Missile) p).lifespan <= 0) particlesToRemove.add(p);
    //}

	}


	public void mousePressed() {
	    xStart = mouseX;
	    yStart = mouseY;
	
	    Cannon cannon = gameEngine.getClosestCannon((int)xStart, (int)yStart);
	    //particles.add(cannon.shoot(new PVector(xStart, yStart)));
	    
//	    Meteor meteor = new Meteor((int)random(0, 800), -100, random(-5f, 5f), 0f, random(0.1f, 0.5f));
//	    gameEngine.meteors.add(meteor);
//	    gameEngine.physicsEngine.registerNewParticle(meteor);
	    
	    gameEngine.missiles.add(new Missile(this, cannon.position.x, cannon.position.y, xStart, yStart));
	}
	
}
