package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class FoodStation extends Fixed implements ISelectable {
	private int capacity;
	private boolean isSelected;
	private static int minSize = 50;
	private static int maxSize = 200; //Add minSize to find realMax
	
	public FoodStation() {
		super(new Random().nextInt(maxSize) + minSize, ColorUtil.rgb(0, 150, 0)); //Size will be between minSize - maxSize+minSize
		this.capacity = this.getSize(); //The capacity is to be proportional to the size.
		this.isSelected = false;
	}
	
	public FoodStation(int size, double locationX, double locationY, int color) {
		super(size, locationX, locationY, color);
		this.capacity = size; //The capacity is to be proportional to the size.
		this.isSelected = false;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public void setSelected(boolean yesNo) {
		isSelected = yesNo;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public boolean contains(Point ptrPosRelPrnt, Point cmpPosRelPrnt) {
		int px = ptrPosRelPrnt.getX();
		int py = ptrPosRelPrnt.getY();
		int xLoc = cmpPosRelPrnt.getX();
		int yLoc = cmpPosRelPrnt.getY();
		
		if ( (px >= xLoc-this.getSize()/2) && (px <= xLoc+this.getSize()/2)
		  && (py >= yLoc-this.getSize()/2) && (py <= yLoc+this.getSize()/2)) {
			return true;
		} else {
			return false;
		}
	}

	public void draw(Graphics g, Point cmpPosRelPrnt) {
		//The location is at the center of the object, we then adjust to draw at the upper left corner of the object.
		int xLoc = cmpPosRelPrnt.getX() + (int)this.getLocationX() - (this.getSize()/2); //Center.x - width/2 relative to mapView.
		int yLoc = cmpPosRelPrnt.getY() + (int)this.getLocationY() - (this.getSize()/2); //Center.y - width/2 relative to mapView.
		
		//Steps to get string width and height for centering string.
		Font thisFont = g.getFont();
		String foodCapacity = ""+this.getCapacity();
		int strW = thisFont.stringWidth(foodCapacity);
		int strH = thisFont.getHeight();
		
		g.setColor(this.getColor());
		if (isSelected) {
			g.drawRect(xLoc, yLoc, this.getSize(), this.getSize());
		} else {
			g.fillRect(xLoc, yLoc, this.getSize(), this.getSize());
		}
		
		//Add a black border and black text showing the capacity of the foodStation.
		g.setColor(ColorUtil.BLACK);
		g.drawString(foodCapacity, xLoc + (this.getSize()/2 - strW/2), yLoc + (this.getSize()/2 - strH/2) );
	}

	public void handleCollision(GameObject otherObj) {
		GameWorld gwRef = this.getGWRef();
		
		//If we have already handled collision, do nothing.
		//Else, handle collision and add to the collidingVector of thisObject and the otherObject.
		if (this.getCollidingVector().contains(otherObj)) {
			return;
		} else {
			if (otherObj instanceof Ladybug) {
				gwRef.collidedWithFoodStation(this, (Ladybug)otherObj);
			} else {
				//No collision with a ladybug, don't need to do anything.
			}
			this.addColliding(otherObj);
			otherObj.addColliding(this);
		}
	}

	@Override
	public String toString() {
		return "FoodStation: loc=" + this.getLocationX() + "," +this.getLocationY() +
				" color=[" + ColorUtil.red(this.getColor()) + "," + ColorUtil.green(this.getColor()) + "," + ColorUtil.blue(this.getColor()) +
				"] size=" + this.getSize();
	}
}
