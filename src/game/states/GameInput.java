package game.states;

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
