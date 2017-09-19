public static final int SCREEN_X = 800;
public static final int SCREEN_Y = 600;

public static final int NUM_PARTICLES = 30;
public static final int NUM_CANNONS = 4;

public Cannon cannon;
public float xStart, yStart, xEnd, yEnd;
public int tick, currCannon;

public PVector forceApplied;

public ArrayList<Particle> particles = new ArrayList<Particle>();

void setup() {
    size(800, 600);
    cannon = new Cannon(400, 300);
}

void draw() {
    background(255);
    cannon.display();
    
    ArrayList<Particle> particlesToRemove = new ArrayList<Particle>();
    
    for (Particle p : particles) {
        p.integrate(null);
        p.display();
        
        if (p.position.x > width || p.position.x < 0 || p.position.y > height || p.position.y < 0) {
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
    
    particles.add(cannon.shoot(new PVector(xStart, yStart)));
}