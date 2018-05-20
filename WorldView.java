package World;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class WorldView extends JComponent {
	
	Controller control;
	
	public WorldView (Controller control) {
		this.control = control;
	}
	
	//This draws the darker square which is the world
	//then it loops through the objects and draws them on.
	public void paintComponent (Graphics g0) {
		Graphics2D g = (Graphics2D) g0;
		g.fillRect(0, 0, 500, 500);
		synchronized (control) {
			for (Object object: control.getObjects()) {
				object.draw(g);
			}
		}
	}
}
