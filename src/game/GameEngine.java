package game;

import game.states.GameContext;
import game.states.GameInput;
import game.states.GameState;
import game.states.impl.StartState;
import objects.buildings.Cannon;
import objects.buildings.City;
import objects.particles.BlackHole;
import objects.particles.BlackHoleMissile;
import objects.particles.Explosion;
import objects.particles.Meteor;
import objects.particles.Missile;
import physics.PhysEngine;
import processing.core.PApplet;

import java.util.ArrayList;

public class GameEngine {
	
	public PApplet parent;
	
    public DrawEngine drawEngine;
    
    public GameContext context;
    public GameState state;

    
    public GameEngine(PApplet parent, DrawEngine drawEngine) {
    	this.parent = parent;
    	this.context = createNewContext();
    	this.state = new StartState(context, drawEngine);

	    this.drawEngine = drawEngine;
    }

    public static GameContext createNewContext() {
    	ArrayList<Meteor> meteors = new ArrayList<Meteor>();
    	ArrayList<Missile> missiles = new ArrayList<Missile>();
    	ArrayList<Explosion> explosions = new ArrayList<Explosion>();
    
    	ArrayList<BlackHoleMissile> bhms = new ArrayList<BlackHoleMissile>();
    	ArrayList<BlackHole> blackholes = new ArrayList<BlackHole>();
    	
    	ArrayList<City> cities = new ArrayList<City>();
    	ArrayList<Cannon> cannons = new ArrayList<Cannon>();
    	
    	initObjects(cities, cannons);
    	
    	PhysEngine physicsEngine = new PhysEngine();
    	Level level = new Level();
    	
    	return new GameContext(meteors, missiles, explosions, bhms, blackholes, 
    							cities, cannons,
    							physicsEngine, level);
    }
    
    private static void initObjects(ArrayList<City> cities, ArrayList<Cannon> cannons) {
	    for (int i = 0; i < GameConfig.NUM_CITIES; i++) {
	        cities.add(new City(50 + ((GameConfig.SCREEN_X/GameConfig.NUM_CITIES) * i), GameConfig.GROUND_HEIGHT));   
	    }	
	    
	    cannons.add(new Cannon(GameConfig.SCREEN_X/2, GameConfig.GROUND_HEIGHT));
    }
    

    public void step() {
    	state.display();
    	state = state.update();
    }
    
	public void handleInput(int mouseX, int mouseY, int mouseButton, int keyPressed) {
		GameInput input = new GameInput(mouseX, mouseY, mouseButton, keyPressed);
		state = state.handleInput(input);
	}
    


}