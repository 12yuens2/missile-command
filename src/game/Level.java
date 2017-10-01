package game;

public class Level {

	public static final int STARTING_METEORS = 10;
	
	public boolean finished; 
	
	public int levelNumber;
	public int numMeteors;
	public int numToSpawn;
	
	
	public Level() {
		this.levelNumber = 1;
		this.numMeteors = STARTING_METEORS;
		this.numToSpawn = numMeteors;
		
		this.finished = false;
	}
	
	
	public Level(int levelNumber) {
		this.levelNumber = levelNumber;
	}
	
	
	public void next() {
		levelNumber++;
		numMeteors += 5;
		numToSpawn = numMeteors;
		finished = false;
	}


	public void spawnMeteor() {
		numToSpawn--;
		if (numToSpawn <= 0) finished = true;
	}
	
}
