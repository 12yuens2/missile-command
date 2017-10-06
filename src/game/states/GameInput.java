package game.states;

/**
 * Class to abstract over all player input.
 * @author sy35
 *
 */
public class GameInput {

	public float mouseX, mouseY;
	public int mouseButton, keyPressed;
	
	public GameInput(float mouseX, float mouseY, int mouseButton, int keyPressed) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.mouseButton = mouseButton;
		this.keyPressed = keyPressed;
	}

}
