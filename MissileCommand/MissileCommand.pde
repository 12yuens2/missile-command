public static final int SCREEN_X = 800;
public static final int SCREEN_Y = 600;

public static final int NUM_PARTICLES = 30;
public static final int NUM_CANNONS = 4;

public Cannon cannon;
public float xStart, yStart, xEnd, yEnd;
public int tick, currCannon;

public PVector forceApplied;

public ArrayList<Particle> particles = new ArrayList<Particle>();
public ArrayList<Cannon> cannons = new ArrayList<Cannon>();
public ArrayList<Collision> collisions = new ArrayList<Collision>();
public Missile m;

void setup() {
    size(800, 600);
    for (int i = 0; i < NUM_CANNONS; i++) {
        cannons.add(new Cannon((int)random(100, SCREEN_X-100), 550));   
    }
}

void draw() {
    background(255);
    for (Cannon c : cannons) {
        c.display();
    }
    
    if ((int)random(0, 20) == 5) {
        Particle missile = new Meteor((int)random(0, SCREEN_X), -100, random(-5f, 5f), 0f, random(0.1f, 0.5f));
        particles.add(missile);
    }
    
    ArrayList<Particle> particlesToRemove = new ArrayList<Particle>();
    
    for (Collision c : collisions) {
        c.resolveCollision();
    }
    collisions.clear();
    
    for (Particle p : particles) {
        p.integrate(null);
        
        for (Particle otherP : particles) {
            if (p.getClass().equals(CannonBall.class)) {
                Collision collision = p.checkCollision(otherP);
                if (collision != null) collisions.add(collision);
            }
        }
        
        p.display();
        
        if (p.position.y > height) {
            particlesToRemove.add(p);   
        }
    }
    
    for (Particle p : particlesToRemove) {
        particles.remove(p);   
    }
    
    if (m != null) m.display();
}


void mousePressed() {
    xStart = mouseX;
    yStart = mouseY;
    
    Cannon cannon = getClosestCannon((int)xStart, (int)yStart);
    particles.add(cannon.shoot(new PVector(xStart, yStart)));
    
    m = new Missile((int)xStart, (int)yStart, 0f, 0f);
}

Cannon getClosestCannon(int posX, int posY) {
    Cannon closestCannon = null;
    float closestDistance = Integer.MAX_VALUE;
    
    for (Cannon cannon : cannons) {
        float distance = sqrt(sq(cannon.position.x - posX) + sq(cannon.position.y - posY));
        if (closestCannon == null || distance < closestDistance) {
            closestCannon = cannon;
            closestDistance = distance;
        }
    }
    
    return closestCannon;
}