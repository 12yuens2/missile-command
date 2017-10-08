package game.states;

import java.util.Random;

import game.GameConfig;

/**
 * Class to abstract over all game information that is displayed to the player.
 * @author sy35
 *
 */
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
		Random random = new Random();
		missilesLeft += GameConfig.NUM_STARTING_MISSILES + levelNumber + random.nextInt(levelNumber);
		if (levelNumber % 4 == 0) blackholesLeft++;
		if (levelNumber % 6 == 0) forcefieldsLeft++;		
	}
	
}
