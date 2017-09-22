public static final int SCREEN_X = 800;
public static final int SCREEN_Y = 600;
public static final int GROUND_HEIGHT = 550;

public static final int NUM_PARTICLES = 30;
public static final int NUM_CANNONS = 4;

public Cannon cannon;
public float xStart, yStart, xEnd, yEnd;
public int tick, currCannon;

public PVector forceApplied;

public ArrayList<Particle> particles = new ArrayList<Particle>();
public ArrayList<Cannon> cannons = new ArrayList<Cannon>();
public ArrayList<Collision> collisions = new ArrayList<Collision>();
public ArrayList<Explosion> explosions = new ArrayList<Explosion>();
public Missile m;




void setup() {
    size(800, 600);
    for (int i = 0; i < NUM_CANNONS; i++) {
        cannons.add(new Cannon((int)random(100, SCREEN_X-100), 550));   
    }
}

void draw() {
    background(255);
    drawGround();
    for (Cannon c : cannons) {
        c.display();
    }
    
    if ((int)random(0, 20) == 5) {
        Particle meteor = new Meteor((int)random(0, SCREEN_X), -100, random(-5f, 5f), 0f, random(0.1f, 0.5f));
        particles.add(meteor);
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
        
        if (p.position.y > GROUND_HEIGHT) {
            explosions.add(new Explosion(p.position.x, p.position.y, p.radius));
            particlesToRemove.add(p);   
        }
    }
    
    for (Explosion explosion : explosions) {
        explosion.display();   
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
    
    m = new Missile((int)cannon.position.x, (int)cannon.position.y, (int)xStart, (int)yStart);
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