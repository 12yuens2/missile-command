package objects;

import game.IDrawable;
import processing.core.PApplet;

public class Crosshair implements IDrawable {

	@Override
	public void display(PApplet parent) {
		parent.cursor(parent.CROSS);
		
	}

}
