package World;

import java.awt.Graphics2D;
import java.awt.Color;

public abstract class Object {
	
	Controller control;
	public int x,y;
	public Color colour;
	public boolean dead;
	
	public Object() {
	}
	
	public Object (Controller control, int x, int y, Color colour) {
		this.control = control;
		this.x = x;
		this.y = y;
		this.colour = colour;
	}
	
	public abstract void update();
	public abstract void draw(Graphics2D g);

}
