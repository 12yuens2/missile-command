package game.states;

import game.GameConfig;

public class GameInfo {

	public int score;
	public int missilesLeft;
	public int blackholesLeft;
	public int forcefieldsLeft;
	
	public int citiesLeft;
	
	public GameInfo(int score, int missilesLeft, int blackholesLeft, int forcefieldsLeft, int citiesLeft) {
		this.score = score;
		this.missilesLeft = missilesLeft;
		this.blackholesLeft = blackholesLeft;
		this.forcefieldsLeft = forcefieldsLeft;
		
		this.citiesLeft = citiesLeft;
	}

	public void resetWaveStart(int levelNumber) {
		missilesLeft = GameConfig.NUM_STARTING_MISSILES + (2 * levelNumber);
		if (levelNumber % 5 == 0) blackholesLeft++;
		if (levelNumber % 10 == 0) forcefieldsLeft++;		
	}
	
}
