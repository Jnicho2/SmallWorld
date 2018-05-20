package section2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Creature extends Object{

	public int energy;
	public boolean onPlant;
	public int viewDist;
	public Object targetFood;

	public Creature() {	
	}

	public Creature (Controller control, Color colour, int x, int y) {
		this.control = control;
		this.colour = colour;
		this.energy = 100;
		this.dead = false;
		this.viewDist = 250;
		this.x = x;
		this.y = y;
	}

	public void update() {
		//First checks if on plant. If yes and it is hungry then it will eat. When full it no longer registers the plant.
		onPlant = checkPlant();
		if (onPlant) {
			if (energy < 100) eat();
			else {
				onPlant = !onPlant;
				targetFood = null;
			}
		}
		//If not on plant it will choose left, right, up, down or stay
		if (!onPlant) {
			//If it has higher than 50 energy it is deemed not hungry so will wander/stay randomly
			if (energy >50) randomStep();		
			else {
				//First off all it looks for a plant unless it is already heading for one
				if (targetFood == null) targetFood = findPlant();
				//If after looking for food it can't find any it takes a random step (this will happen if there is no food in sight)
				if (targetFood == null) randomStep();
				else {
					if (targetFood.x != this.x) {
						if (targetFood.x < this.x) {
							this.x -= 50;
							this.energy -= 5;
						}
						else if (targetFood.x > this.x) {
							this.x += 50;
							this.energy -= 5;
						}
					} else {
						if (targetFood.y < this.y) {
							this.y -= 50;
							this.energy -= 5;						
						}
						else if (targetFood.y > this.y) {
							this.y += 50;
							this.energy -= 5;
						}
					}
				}
			}
		}
		//These next to lines will "wrap" the creature around the world
		x = (x+500)%500;
		y = (y+500)%500;
		if (energy <=0) dead = true;

	}

	//I moved this to its only method to help organise the class
	public void randomStep () {
		String[] dir =  {"left", "right", "up", "down", "stay"};
		String choice = dir[new Random().nextInt(dir.length)];
		switch (choice) {
		case "left":
			this.x = x - 50;
			this.energy -= 5;
			break;
		case "right":
			this.x = x + 50;
			this.energy -= 5;
			break;
		case "up":
			this.y = y - 50;
			this.energy -= 5;
			break;
		case "down":
			this.y = y + 50;
			this.energy -= 5;
			break;
		case "stay":
			this.energy -= 1;
			break;				
		}			
	}

	public Object findPlant() {

		//Set it so that it doesn't have a closest plant
		//closestDist is set to max so that any plant found will be closer;
		Object closestPlant = null;
		double closestDist = Double.MAX_VALUE;
		double distToObj;
		for (Object object: control.objects) {
			double rand = Math.random();
			//Finds the distance using Pythagoras’ theorem
			distToObj = Math.sqrt( ((this.x-object.x)*(this.x-object.x)) + ((this.y-object.y)*(this.y-object.y)));

			//If the distance is greater than half the view dist there is a 2 in 10 chance that the distance is set to double the view dist
			//this means that the creature will not register the plant when it checks
			//If it is closer there is a 1 in 10 chance
			if (distToObj > viewDist/2) {
				if (rand >= 0.8) distToObj = viewDist*2;				
			} else {
				if (rand >= 0.9) distToObj = viewDist*2;
			}

			if (object.getClass() == Plant.class
					&& object.colour == this.colour
					&& distToObj <= viewDist
					&& distToObj < closestDist) {
				closestPlant = object;
				closestDist = distToObj;
			}
		}
		if (closestPlant == null) return null;
		else return closestPlant;
	}

	public boolean checkPlant() {
		for (Object object: control.objects) {
			if (object.getClass() == Plant.class
					&& object.colour == this.colour
					&& control.overlap(this, object)) {
				return true;
			}
		}
		return false;		
	}

	public void eat() {
		energy += 25;
		if (energy > 100) energy = 100;
	}

	public void draw(Graphics2D g) {
		g.setColor(colour);
		g.fillRect(x-25, y-25, 50, 50);	
		g.setColor(Color.yellow);
		g.drawString(Integer.toString(energy), x, y);
	}
}