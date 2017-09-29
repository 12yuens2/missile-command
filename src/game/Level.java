package game;

public class Level {

	public static final int STARTING_METEORS = 10;
	
	public enum State {RUNNING, FINISHED, WAITING};
	
	public State state; 
	
	public int levelNumber;
	public int numMeteors;
	public int meteorCount;
	
	
	public Level() {
		this.levelNumber = 1;
		this.numMeteors = STARTING_METEORS;
		this.meteorCount = numMeteors;
		
		this.state = State.RUNNING;
	}
	
	
	public Level(int levelNumber) {
		this.levelNumber = levelNumber;
	}
	
	
	public void next() {
		levelNumber++;
		numMeteors += 5;
		meteorCount = numMeteors;
		state = State.RUNNING;
	}


	public void spawnMeteor() {
		meteorCount--;
		if (meteorCount <= 0) state = State.FINISHED;
	}
	
}
