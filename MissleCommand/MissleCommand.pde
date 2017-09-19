public static final int SCREEN_X = 800;
public static final int SCREEN_Y = 600;

public static final int NUM_PARTICLES = 30;
public static final int NUM_CANNONS = 4;

public Particle[][] particleCannons = new Particle[NUM_CANNONS][];
public float xStart, yStart, xEnd, yEnd;
public int tick, currCannon;

public PVector forceApplied;

void setup() {
    size(800, 600);
    tick = 0;
    currCannon = 0;
   
    createCannon();
}

void draw() {
    background(255);
    tick++;
    if (tick == 50) {
       currCannon = (currCannon >= NUM_CANNONS - 1 ? 0 : currCannon+1);
       System.out.println((currCannon >= NUM_CANNONS - 1 ? 0 : currCannon+1));
       createCannon();
       tick = 0;
    }
    
    
    for (Particle[] cannon : particleCannons) {
        if (cannon != null) { 
          for (Particle p : cannon) {
              p.integrate(null);
              p.display();
          }
        }
    }
}

private void createCannon() {
    particleCannons[currCannon] = new Particle[NUM_PARTICLES]; 
    for (int i = 0; i < NUM_PARTICLES; i++) {
        particleCannons[currCannon][i] = new Particle((int)random(350, 450), SCREEN_Y, random(-4f, 4f), random(-40f, -30f), random(0.05f, 0.15f)); 
    }
}

void mousePressed() {
    xStart = mouseX;
    yStart = mouseY;
}

void mouseReleased() {
    xEnd = mouseX;
    yEnd = mouseY;
    forceApplied = new PVector((xEnd - xStart)/1000f, (yEnd - yStart)/1000f);
}