package World;

import java.awt.Color;
import java.awt.Graphics2D;


public class Plant extends Object {
	
	public Plant() {
	}
	
	public Plant (Controller control, Color colour, int x, int y) {
		this.control = control;
		this.colour = colour;
		this.x = x;
		this.y = y;
	}

	public void update() {
	}

	public void draw(Graphics2D g) {
		g.setColor(colour);
		g.fillOval(x-25, y-25, 50, 50);		
	}
}
