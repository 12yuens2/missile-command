import java.util.Iterator;

public static final int SCREEN_X = 800;
public static final int SCREEN_Y = 600;
public static final int GROUND_HEIGHT = 550;

public static final int NUM_PARTICLES = 30;
public static final int NUM_CANNONS = 4;
public static final int NUM_CITIES = 5;

public Cannon cannon;
public float xStart, yStart, xEnd, yEnd;
public int tick, currCannon;

public PVector forceApplied;

public GameScene gameScene;
public ArrayList<Particle> particles;
public ArrayList<Collision> collisions;

/* For clean up thread */
public ArrayList<Particle> particlesToRemove;


void setup() {
    size(800, 600);
    gameScene = new GameScene();
    
    particles = new ArrayList<Particle>();
    collisions = new ArrayList<Collision>();
    particlesToRemove = new ArrayList<Particle>();
    
    spawnBuildings();

}

private void spawnBuildings() {
    for (int i = 0; i < NUM_CANNONS; i++) {
        gameScene.cannons.add(new Cannon((int)random(100, SCREEN_X-100), 550));
    }
    
    for (int i = 0; i < NUM_CITIES; i++) {
        gameScene.cities.add(new City((int)random(30, SCREEN_X-30), 550));   
    }
}

void draw() {
    background(255);
    drawGround();
    
    gameScene.display();
    
    if ((int)random(0, 20) == 5) {
        Particle meteor = new Meteor((int)random(0, SCREEN_X), -100, random(-5f, 5f), 0f, random(0.1f, 0.5f));
        
        particles.add(meteor);
        gameScene.meteors.add((Meteor) meteor);
    }


    for (Collision c : collisions) {
        c.resolveCollision();
    }
    collisions.clear();

    for (Particle p : particles) {
        p.integrate(null);

        for (Particle otherP : particles) {
            if (p.getClass().equals(Missile.class)) {
                Collision collision = p.checkCollision(otherP);
                if (collision != null) {
                    collisions.add(collision);
                    ((Missile)p).exploded = true;   
                }
            }
        }

        p.display();

        if (p.position.y > GROUND_HEIGHT) {
            gameScene.explosions.add(new Explosion(p.position.x, p.position.y, p.radius));
            particlesToRemove.add(p);
        }
        
        if (p.getClass().equals(Missile.class) && ((Missile) p).lifespan <= 0) particlesToRemove.add(p);
    }
    
    thread("cleanUp");

}


void mousePressed() {
    xStart = mouseX;
    yStart = mouseY;

    Cannon cannon = getClosestCannon((int)xStart, (int)yStart);
    //particles.add(cannon.shoot(new PVector(xStart, yStart)));
    
    particles.add(new Missile((int)cannon.position.x, (int)cannon.position.y, (int)xStart, (int)yStart));
}

void drawGround() {
    fill(128);
    beginShape();
    vertex(0, GROUND_HEIGHT);
    vertex(SCREEN_X, GROUND_HEIGHT);
    vertex(SCREEN_X, SCREEN_Y);
    vertex(0, SCREEN_Y);
    endShape(CLOSE);
}

void cleanUp() {
    for (Particle p : particlesToRemove) {
        particles.remove(p);
        
        if (p.getClass().equals(Missile.class)) gameScene.missiles.remove(p);
        else if (p.getClass().equals(Meteor.class)) gameScene.meteors.remove(p);
    }
    
    particlesToRemove.clear();
    
    for (Iterator<Explosion> it = gameScene.explosions.iterator(); it.hasNext();) {
        Explosion e = it.next();
        if (e.lifespan <= 0) it.remove();
    }
}

Cannon getClosestCannon(int posX, int posY) {
    Cannon closestCannon = null;
    float closestDistance = Integer.MAX_VALUE;

    for (Cannon cannon : gameScene.cannons) {
        float distance = sqrt(sq(cannon.position.x - posX) + sq(cannon.position.y - posY));
        if (closestCannon == null || distance < closestDistance) {
            closestCannon = cannon;
            closestDistance = distance;
        }
    }

    return closestCannon;
}