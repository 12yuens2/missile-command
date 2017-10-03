package game.states;

import java.util.ArrayList;

import game.DrawEngine;
import game.GameConfig;
import game.GameController;
import game.Level;
import objects.buildings.Cannon;
import objects.buildings.City;
import objects.particles.BlackHole;
import objects.particles.BlackHoleMissile;
import objects.particles.Bomber;
import objects.particles.Explosion;
import objects.particles.ForceField;
import objects.particles.Meteor;
import objects.particles.Missile;
import physics.PhysicsEngine;
import processing.core.PApplet;

public class GameContext {

	public PhysicsEngine physicsEngine;
	
	public ArrayList<Meteor> meteors;
    public ArrayList<Missile> missiles;
    public ArrayList<Explosion> explosions;
    public ArrayList<Bomber> bombers;
    
	public ArrayList<BlackHoleMissile> bhms;
	public ArrayList<BlackHole> blackholes;
	public ArrayList<ForceField> forcefields;
	
    public ArrayList<City> cities;
    public ArrayList<Cannon> cannons;

    public Level level;
    public GameInfo info;

    public int cityCount;
    public int meteorCount;

    
    public GameContext() {
    	initFields();
    	initGameObjects(cities, cannons);
    	
    	this.cityCount = cities.size();
    	this.meteorCount = level.meteorSpawnCount;
    	
    	this.info = new GameInfo(0, GameConfig.NUM_STARTING_MISSILES, 0, 0, cityCount);    	
    }
    
    private void initFields() {
    	this.meteors = new ArrayList<Meteor>();
    	this.missiles = new ArrayList<Missile>();
    	this.explosions = new ArrayList<Explosion>();
    	this.bombers = new ArrayList<Bomber>();
    	this.bhms = new ArrayList<BlackHoleMissile>();
    	this.blackholes = new ArrayList<BlackHole>();
    	this.forcefields = new ArrayList<ForceField>();
    	this.cities = new ArrayList<City>();
    	this.cannons = new ArrayList<Cannon>();
    	
    	this.physicsEngine = new PhysicsEngine();
    	this.level = new Level();
    }
    
    
    private void initGameObjects(ArrayList<City> cities, ArrayList<Cannon> cannons) {
	    for (int i = 0; i < GameConfig.NUM_CITIES; i++) {
	        cities.add(new City(50 + ((GameConfig.SCREEN_X/GameConfig.NUM_CITIES) * i), GameConfig.GROUND_HEIGHT));   
	    }	
	    
	    cannons.add(new Cannon(GameConfig.SCREEN_X/2, GameConfig.GROUND_HEIGHT));
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
