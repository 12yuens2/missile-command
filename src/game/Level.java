package game;

public class Level {

	public static final int STARTING_METEORS = 10;
	
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
		
		this.bomberSpawnCount = 1;
		this.numBombers = bomberSpawnCount;
		
		this.finished = false;
	}
	
	
	public Level(int levelNumber) {
		this.levelNumber = levelNumber;
	}
	
	
	public void next() {
		levelNumber++;

		if (levelNumber % 7 == 0) meteorMassBase += 0.75f;
		meteorSpawnCount += 5;
		numMeteors = meteorSpawnCount;
		
		if (levelNumber % 5 == 0) bomberSpawnCount += 1;
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
