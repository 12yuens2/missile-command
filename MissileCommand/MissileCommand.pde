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
        Particle missile = new Particle((int)random(0, SCREEN_X), 0, random(-5f, 5f), 0f, random(0.1f, 0.5f));
        particles.add(missile);
    }
    
    ArrayList<Particle> particlesToRemove = new ArrayList<Particle>();
    
    for (Particle p : particles) {
        p.integrate(null);
        p.display();
        
        if (p.position.y > height) {
            particlesToRemove.add(p);   
        }
    }
    
    for (Particle p : particlesToRemove) {
        particles.remove(p);   
    }
}


void mousePressed() {
    xStart = mouseX;
    yStart = mouseY;
    
    Cannon cannon = cannons.get((int)random(0, cannons.size()));
    particles.add(cannon.shoot(new PVector(xStart, yStart)));
}