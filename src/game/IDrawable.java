package game;

import processing.core.PApplet;

/**
 * This interface is implemented by all game objects so the DrawEngine can call each object's own display function to display them.
 * @author sy35
 *
 */
public interface IDrawable {

	public void display(PApplet parent);
}
