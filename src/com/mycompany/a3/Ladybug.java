package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Ladybug extends Moveable implements ISteerable {
	private static Ladybug theLadybug;
	private int maximumSpeed;
	private int foodLevel;
	private int foodConsumptionRate;
	private int healthLevel;
	private int lastFlagReached;
	
	private Ladybug() {
		//super(size, locationX, locationY, color, heading, speed);
		super(100, 100.0, 100.0, ColorUtil.rgb(255, 0, 0), 45, 40);
		
		this.maximumSpeed = 100;
		this.foodLevel = 500;
		this.foodConsumptionRate = 2;
		this.healthLevel = 10;
		this.lastFlagReached = 1;
	}
	
	public static Ladybug getLadybug() {
		if (theLadybug == null) {
			theLadybug = new Ladybug();
		}
		return theLadybug;
	}
	
	public void destroyLadybug() {
		theLadybug = null;
	}
	
	public int getMaximumSpeed() {
		return maximumSpeed;
	}

	public void setMaximumSpeed(int maximumSpeed) {
		this.maximumSpeed = maximumSpeed;
	}

	public int getFoodLevel() {
		return foodLevel;
	}

	public void setFoodLevel(int foodLevel) {
		this.foodLevel = foodLevel;
	}

	public int getFoodConsumptionRate() {
		return foodConsumptionRate;
	}

	public void setFoodConsumptionRate(int foodConsumptionRate) {
		this.foodConsumptionRate = foodConsumptionRate;
	}

	public int getHealthLevel() {
		return healthLevel;
	}

	public void setHealthLevel(int healthLevel) {
		this.healthLevel = healthLevel;
	}

	public int getLastFlagReached() {
		return lastFlagReached;
	}

	public void setLastFlagReached(int lastFlagReached) {
		this.lastFlagReached = lastFlagReached;
	}

	
	public void steerLeft() {
		int currentHeading = this.getHeading();
		currentHeading = super.limitAngleTo360Deg(currentHeading - 5); //Move the angle to the left by 5 degrees.
		this.setHeading(currentHeading);
	}
	
	public void steerRight() {
		int currentHeading = this.getHeading();
		currentHeading = super.limitAngleTo360Deg(currentHeading + 5); //Move the angle to the right by 5 degrees.
		this.setHeading(currentHeading);
	}
	
	public void draw(Graphics g, Point cmpPosRelPrnt) {
		//The location is at the center of the object, we then adjust to draw at the upper left corner of the object.
		int xLoc = cmpPosRelPrnt.getX() + (int)this.getLocationX() - (this.getSize()/2); //Center.x - width/2 relative to mapView.
		int yLoc = cmpPosRelPrnt.getY() + (int)this.getLocationY() - (this.getSize()/2); //Center.y - width/2 relative to mapView.
		
		int radius = this.getSize()/2;
		
		g.setColor(this.getColor());
		g.fillArc(xLoc, yLoc, 2*radius, 2*radius, 0, 360);
	}

	public void handleCollision(GameObject otherObj) {
		GameWorld gwRef = this.getGWRef();
		
		//If we have already handled collision, do nothing.
		//Else, handle collision and add to the collidingVector of thisObject and the otherObject.
		if (this.getCollidingVector().contains(otherObj)) {
			return;
		} else {
			if (otherObj instanceof Spider) {
				gwRef.collidedWithSpider();
			} else if (otherObj instanceof FoodStation) {
				gwRef.collidedWithFoodStation((FoodStation)otherObj, this);
			} else if (otherObj instanceof Flag) {
				int flagNumber = ((Flag)otherObj).getSequenceNumber();
				gwRef.collidedWithFlag(flagNumber);
			}
			this.addColliding(otherObj);
			otherObj.addColliding(this);
		}
	}

	@Override
	public String toString() {
		return "Ladybug: loc=" + Math.round(this.getLocationX()*10.0)/10.0 + "," + Math.round(this.getLocationY()*10.0)/10.0 +
			   " color=[" + ColorUtil.red(this.getColor()) + "," + ColorUtil.green(this.getColor()) + "," + ColorUtil.blue(this.getColor()) +
			   "] heading=" + this.getHeading() + " speed=" + this.getSpeed() + " size=" + this.getSize() + " maxSpeed=" + this.getMaximumSpeed() +
			   " foodConsumptionRate=" + this.getFoodConsumptionRate();
	}
	
	
}
