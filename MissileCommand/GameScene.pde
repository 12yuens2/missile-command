public class GameScene {
    
 
    /* Particles */
    public ArrayList<Meteor> meteors;
    public ArrayList<Missile> missiles;
    public ArrayList<Explosion> explosions;
    
    
    /* Ground objects*/
    public ArrayList<City> cities;
    public ArrayList<Cannon> cannons;
    
    
    public GameScene() {
        this.meteors = new ArrayList<Meteor>();
        this.missiles = new ArrayList<Missile>();
        this.explosions = new ArrayList<Explosion>();
        
        this.cities = new ArrayList<City>();
        this.cannons = new ArrayList<Cannon>();
    }
    
    
    public void display() {        
        for (Meteor m : meteors) m.display();
        for (Missile m : missiles) m.display();
        for (Explosion e : explosions) e.display();
        
        for (City c : cities) c.display();
        for (Cannon c : cannons) c.display();
    }
    
}