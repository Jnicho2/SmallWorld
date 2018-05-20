package section2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Controller {
	
	ArrayList<Object> objects;
	ArrayList<Object> pending;
	ArrayList<Object> alive;
	static Controller control;
	static boolean running = true;
	static JFrame frame;
	
	public Controller() {
		//These arrays will hold all the objects. They are split into three lists
		//Pending is the objects that are being added to the world
		//Alive contains objects that will be alive at the end of each loop
		//Objects is all of the objects
		objects = new ArrayList<Object>();
		pending = new ArrayList<Object>();
		alive = new ArrayList<Object>();

	}
	
	public static void main(String[] args) throws Exception {
		control = new Controller();
		WorldView view = new WorldView(control);
		
		//Sets up the JFrame		
		frame = new JFrame("Section 2");	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(BorderLayout.CENTER, view);
		frame.setSize(550, 550);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		//This is where creatures and plants are added
		control.pending.add(new Plant(control, Color.blue,225,25));
		control.pending.add(new Plant(control, Color.blue,75,275));
		control.pending.add(new Plant(control, Color.blue,425,275));
		control.pending.add(new Plant(control, Color.red, 225, 225));
		control.pending.add(new Creature(control, Color.blue,425,25));
		control.pending.add(new Creature(control, Color.blue,375,325));
		control.pending.add(new Creature(control, Color.blue,25,275));
		control.pending.add(new Creature(control, Color.red, 175, 325));

		//This is the main loop for the simulation
		while (running) {
			control.update();
			view.repaint();
			Thread.sleep(1000);
		}		
	}
	
	public void update() {
		
		//Looping through the objects, if the object is a creature it checks collisions
		//if the object is still alive at the end of its movement it is added to the alive array.
		for (Object object: objects) {
			int xTemp = object.x;
			int yTemp = object.y;
			object.update();
			if (object instanceof Creature) checkCollision((Creature)object, xTemp, yTemp);
			if (!object.dead) alive.add(object);
		}
		
		//This is used to remove dead objects
		synchronized (this) {
			objects.clear();
			objects.addAll(pending);
			objects.addAll(alive);
		}
		pending.clear();
		alive.clear();
	}
	
	//This method is used in WorldView to check the objects
	public Iterable<Object> getObjects() {
		return objects;
	}

	public void checkCollision(Object object, int prevX, int prevY) {
		if (!object.dead) {
			for (Object otherObj : objects) {
				if (object != otherObj) {	
					//If a creature touches another creature it moves back to where it started
					if (overlap((Object) object, (Object) otherObj)
							&& otherObj.getClass() == Creature.class) {
						object.x = prevX;
						object.y = prevY;
					}
				}
			}
		}		
	}
	
	//Checks if objects are touching by comparing the distance between their centres.
	public boolean overlap(Object ob1, Object ob2) {
		return Math.sqrt( ((ob1.x-ob2.x)*(ob1.x-ob2.x)) + ((ob1.y-ob2.y)*(ob1.y-ob2.y))) <= 45;
	}
}
