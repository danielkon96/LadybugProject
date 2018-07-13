package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Spider extends Moveable {
	private static int minSize = 50;
	private static int maxSize = 200; //Add minSize to find realMax
	
	public Spider() {
		super(new Random().nextInt(maxSize) + minSize, ColorUtil.BLACK, 0, 50);
	}
	
	public Spider(int heading, int speed) {
		super(new Random().nextInt(maxSize) + minSize, ColorUtil.BLACK, 0, 50, heading, speed);
	}
	
	public Spider(int size, int color, int heading, int speed) {
		super(size, color, heading, speed);
	}
	
	public Spider(int size, double locationX, double locationY, int color, int heading, int speed) {
		super(size, locationX, locationY, color, heading, speed);
	}
	
	@Override
	public void setSpeed(int speed) { return; } //Assumption: A spider cannot change speed.
	
	@Override
	public void setColor(int color) { return; } //A spider cannot change colors.
	
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
		g.drawPolygon(xPoints, yPoints, 3);
		
		/*
		 * Another way to draw an open triangle. 
		 * 
		 * g.drawLine(topX, topY, bottomLeftX, bottomLeftY);
		 * g.drawLine(bottomLeftX, bottomLeftY, bottomRightX, bottomRightY);
		 * g.drawLine(bottomRightX, bottomRightY, topX, topY);
		 */
	}

	public void handleCollision(GameObject otherObj) {
		GameWorld gwRef = this.getGWRef();
		
		//If we have already handled collision, do nothing.
		//Else, handle collision and add to the collidingVector of thisObject and the otherObject.
		if (this.getCollidingVector().contains(otherObj)) {
			return;
		} else {
			if (otherObj instanceof Ladybug) {
				gwRef.collidedWithSpider();
			} else {
				//No collision with a ladybug, don't need to do anything.
			}
			this.addColliding(otherObj);
			otherObj.addColliding(this);
		}
	}

	@Override
	public String toString() {
		return "Spider: loc=" + Math.round(this.getLocationX()*10.0)/10.0 + "," + Math.round(this.getLocationY()*10.0)/10.0 +
				" color=[" + ColorUtil.red(this.getColor()) + "," + ColorUtil.green(this.getColor()) + "," + ColorUtil.blue(this.getColor()) +
				"] heading=" + this.getHeading() + " speed=" + this.getSpeed() + " size=" + this.getSize();
	}
}
