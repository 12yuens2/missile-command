package game.states;

public abstract class GameState {

	public GameContext context;
	
	public GameState(GameContext context) {
		this.context = context;
	}
	
	public abstract void display();
	
	public abstract GameState update();
	
	public abstract GameState handleInput(GameInput input);
}
