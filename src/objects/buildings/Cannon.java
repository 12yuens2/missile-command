package objects.buildings;
import objects.particles.Particle;
import processing.core.PApplet;
import processing.core.PVector;

public class Cannon extends Building {
    
    public Cannon(PApplet parent, int posX, int posY) {
        position = new PVector(posX, posY);
    }
    
    public void display(PApplet parent) {
        parent.rectMode(parent.CENTER);
        parent.fill(0);
        parent.rect(position.x, position.y, 10, 10);
    }
    
}