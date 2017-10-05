package game;

public class GameConfig {
	public static final int SCREEN_X = 800;
	public static final int SCREEN_Y = 600;
	public static final int GROUND_HEIGHT = 550;
	
	public static final int NUM_PARTICLES = 30;
	public static final int NUM_CANNONS = 4;
	public static final int NUM_CITIES = 5;
	public static final int NUM_STARTING_MISSILES = 10;
	
	public static final int METEOR_EXPLODE_SCORE = 9;
	public static final int METEOR_REMOVED_SCORE = 15;
	public static final int CITY_SURVIVE_SCORE = 125;
	public static final int MISSILE_LEFT_SCORE = 25;

	public static final int END_GAME_COST = 25000;
	
	public static final int BLACK_HOLE_COST = 1000;
	public static final int FORCEFIELD_COST = 1000;
	public static final int MISSILE_COST = MISSILE_LEFT_SCORE * 2;
	public static final int CITY_COST = 1000;
}
