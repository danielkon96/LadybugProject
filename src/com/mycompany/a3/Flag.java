package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.Fixed;

public class Flag extends Fixed implements ISelectable {
	private int sequenceNumber;
	private boolean isSelected;
	
	public Flag(int sequenceNumber) {
		super(175, ColorUtil.BLUE);
		this.sequenceNumber = sequenceNumber;
	}
	
	public Flag(int size, double locationX, double locationY, int sequenceNumber) {
		super(size, locationX, locationY, ColorUtil.BLUE);
		this.sequenceNumber = sequenceNumber;
		this.isSelected = false;
	}
	
	public Flag(int size, double locationX, double locationY, int color, int sequenceNumber) {
		super(size, locationX, locationY, color);
		this.sequenceNumber = sequenceNumber;
		this.isSelected = false;
	}
	
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	@Override
	public void setColor(int color) { return; } //A flag cannot change colors.
	
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
		int base_height = (this.getSize()/2);
		
		//See triangle diagram in Chapter 14, Slide #7 for visualization
		int topX = cmpPosRelPrnt.getX() + (int)this.getLocationX();
		int topY = cmpPosRelPrnt.getY() + (int)this.getLocationY() + base_height;
		int bottomLeftX = cmpPosRelPrnt.getX() + (int)this.getLocationX() - base_height;
		int bottomLeftY = cmpPosRelPrnt.getY() + (int)this.getLocationY() - base_height;
		int bottomRightX = cmpPosRelPrnt.getX() + (int)this.getLocationX() + base_height;
		int bottomRightY = cmpPosRelPrnt.getY() + (int)this.getLocationY() - base_height;
		
		int[] xPoints = new int[3];
		int[] yPoints = new int[3];
		
		xPoints[0] = topX;
		xPoints[1] = bottomLeftX;
		xPoints[2] = bottomRightX;
		yPoints[0] = topY;
		yPoints[1] = bottomLeftY;
		yPoints[2] = bottomRightY;
		
		g.setColor(this.getColor());
		if (isSelected) {
			g.drawPolygon(xPoints, yPoints, 3);
		} else {
			g.fillPolygon(xPoints, yPoints, 3);
		}
		
		//Steps to get string width and height for centering string.
		Font thisFont = g.getFont();
		String flagNumber = ""+this.getSequenceNumber();
		int strW = thisFont.stringWidth(flagNumber);
		int strH = thisFont.getHeight();
		
		//The location is at the center of the object, we then adjust to draw at the upper left corner of the object.
		int xLoc = cmpPosRelPrnt.getX() + (int)this.getLocationX() - (this.getSize()/2); //Center.x - width/2 relative to mapView.
		int yLoc = cmpPosRelPrnt.getY() + (int)this.getLocationY() - (this.getSize()/2); //Center.y - width/2 relative to mapView.
		
		g.setColor(ColorUtil.WHITE);
		g.drawString(flagNumber, xLoc + (this.getSize()/2 - strW/2), yLoc + (this.getSize()/2 - strH/2) );
	}

	public void handleCollision(GameObject otherObj) {
		GameWorld gwRef = this.getGWRef();
		
		//If we have already handled collision, do nothing.
		//Else, handle collision and add to the collidingVector of thisObject and the otherObject.
		if (this.getCollidingVector().contains(otherObj)) {
			return;
		} else {
			if (otherObj instanceof Ladybug) {
				gwRef.collidedWithFlag(this.getSequenceNumber());
			} else {
				//No collision with a ladybug, don't need to do anything.
			}
			this.addColliding(otherObj);
			otherObj.addColliding(this);
		}
	}

	@Override
	public String toString() {
		return "Flag: loc=" + this.getLocationX() + "," +this.getLocationY() +
				" color=[" + ColorUtil.red(this.getColor()) + "," + ColorUtil.green(this.getColor()) + "," + ColorUtil.blue(this.getColor()) +
				"] size=" + this.getSize() + " seqNum=" + this.getSequenceNumber();
	}
}
