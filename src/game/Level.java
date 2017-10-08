package game;

public class Level {

	public static final int STARTING_METEORS = 10;
	public static final int METEOR_INCREASE_PER_LEVEL = 2;
	
	public static final int LEVEL_INCREASE_MASS = 7;
	public static final int LEVEL_INCREASE_BOMBER = 6;
	
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
		levelNumber++;

		if (levelNumber % LEVEL_INCREASE_MASS == 0) meteorMassBase += 0.5f;
		meteorSpawnCount += METEOR_INCREASE_PER_LEVEL;
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
