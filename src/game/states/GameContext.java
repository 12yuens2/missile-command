package game.states;

import java.util.ArrayList;

import game.DrawEngine;
import game.GameConfig;
import game.GameEngine;
import game.Level;
import objects.buildings.Cannon;
import objects.buildings.City;
import objects.particles.BlackHole;
import objects.particles.BlackHoleMissile;
import objects.particles.Explosion;
import objects.particles.Meteor;
import objects.particles.Missile;
import physics.PhysEngine;
import processing.core.PApplet;

public class GameContext {

	public PhysEngine physEngine;
	
	public ArrayList<Meteor> meteors;
    public ArrayList<Missile> missiles;
    public ArrayList<Explosion> explosions;
    
	public ArrayList<BlackHoleMissile> bhms;
	public ArrayList<BlackHole> blackholes;
	
    public ArrayList<City> cities;
    public ArrayList<Cannon> cannons;

    public Level level;
    
    public int score;
    public int numMissiles;
    public int numBlackholes;
    public int cityCount;
    public int meteorCount;
    
    public GameContext(ArrayList<Meteor> meteors, 
    				ArrayList<Missile> missiles, 
    				ArrayList<Explosion> explosions,
    				ArrayList<BlackHoleMissile> bhms,
    				ArrayList<BlackHole> blackholes,
    				ArrayList<City> cities,
    				ArrayList<Cannon> cannons,
    				
    				PhysEngine physEngine,
    				Level level) {
    	
    	this.meteors = meteors;
    	this.missiles = missiles;
    	this.explosions = explosions;
    	this.bhms = bhms;
    	this.blackholes = blackholes;
    	this.cities = cities;
    	this.cannons = cannons;
    	
    	this.physEngine = physEngine;
    	
    	this.level = level;
    	
    	this.score = 0;
    	this.numMissiles = GameConfig.NUM_STARTING_MISSILES;
    	this.numBlackholes = 0;
    	this.cityCount = cities.size();
    	this.meteorCount = level.numMeteors;
    	
    }
    

	public Cannon getClosestCannon(int posX, int posY) {
    	Cannon closestCannon = null;
    	float closestDistance = Integer.MAX_VALUE;
    
    	for (Cannon cannon : cannons) {
    	    float distance = PApplet.sqrt(PApplet.sq(cannon.position.x - posX) + PApplet.sq(cannon.position.y - posY));
    	    if (closestCannon == null || distance < closestDistance) {
	    		closestCannon = cannon;
	    		closestDistance = distance;
    	    }
    	}
    
    	return closestCannon;
    }
}
