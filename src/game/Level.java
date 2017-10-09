package game;

import java.util.Random;

/**
 * Represents every wave of the game, keeps track of the number of meteors to spawn and adjusts the difficulty every level.
 * @author sy35
 *
 */
public class Level {

	public static final int STARTING_METEORS = 10;
	public static final int METEOR_INCREASE_PER_LEVEL = 3;
	public static final float MASS_INCREASE_PER_LEVEL = 0.1f;
	
	/* Number of levels before each base mass increase of meteors */
	public static final int LEVEL_INCREASE_MASS = 7;
	
	/* Number of levels before adding bomber */
	public static final int LEVEL_INCREASE_BOMBER = 6;
	
	/* Number of levels before adding even more meteors per level */
	public static final int LEVEL_INCREASE_METEOR = 5;
	
	public boolean finished; 
	
	public int levelNumber;
	public int meteorSpawnCount;
	public int numMeteors;
	
	public int numBombers;
	public int bomberSpawnCount;

	public float meteorMassBase;
	
	public Level() {
		this.levelNumber = 1;
		this.meteorMassBase = 0.2f;
		
		this.meteorSpawnCount = STARTING_METEORS;
		this.numMeteors = meteorSpawnCount;
		
		this.bomberSpawnCount = 0;
		this.numBombers = bomberSpawnCount;
		
		this.finished = false;
	}
	
	
	public Level(int levelNumber) {
		this.levelNumber = levelNumber;
	}
	
	
	public void next() {
		Random random = new Random();
		levelNumber++;

		if (levelNumber % LEVEL_INCREASE_MASS == 0) meteorMassBase += MASS_INCREASE_PER_LEVEL;
		if (levelNumber > LEVEL_INCREASE_METEOR) meteorSpawnCount += random.nextInt(levelNumber);
		meteorSpawnCount += random.nextInt(METEOR_INCREASE_PER_LEVEL);
		numMeteors = meteorSpawnCount;
		
		if (levelNumber % LEVEL_INCREASE_BOMBER == 0) bomberSpawnCount += 1;
		numBombers = bomberSpawnCount;
		
		finished = false;
	}


	public void spawnMeteor() {
		numMeteors--;
		if (numMeteors <= 0) finished = true;
	}
	
	public void spawnBomber() {
		if (numBombers > 0) numBombers--;
	}
	
}
