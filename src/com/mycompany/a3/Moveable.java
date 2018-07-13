package com.mycompany.a3;

public abstract class Moveable extends GameObject {
	private int heading;
	private int speed;
	
	public Moveable(int size, int color, int heading, int speed) {
		super(size, color);
		this.heading = heading;
		this.speed = speed;
	}
	
	public Moveable(int size, double locationX, double locationY, int color, int heading, int speed) {
		super(size, locationX, locationY, color);
		this.heading = heading;
		this.speed = speed;
	}
	
	public void move(int elapsedTime) {
		//Get elapsedTime / 1000.0 and see how much we will actually move from our position.
		//If our time clock is ticked 1 second apart from each other, the ladybug will cover a larger distance.
		double setX = this.getLocationX() + ( (elapsedTime/1000.0) * (Math.cos(Math.toRadians(heading)) * speed) );
		double setY = this.getLocationY() + ( (elapsedTime/1000.0) * (Math.sin(Math.toRadians(heading)) * speed) );
		
		//Initialize min and max boundaries
		double minX = 0.0 + this.getSize()/2;
		double maxX = this.getMapSizeX() - this.getSize()/2;
		double minY = 0.0 + this.getSize()/2;
		double maxY = this.getMapSizeY() - this.getSize()/2;
		
		//If we hit an x-axis boundary, stop there and turn around.
		if (setX < minX) {
			setX = minX;
			heading = limitAngleTo360Deg(heading + 70);
		} else if (setX > maxX) {
			setX = maxX;
			heading = limitAngleTo360Deg(heading + 80);
		}
		
		//If we hit an y-axis boundary, stop there and turn around.
		if (setY < minY) {
			setY = minY;
			heading = limitAngleTo360Deg(heading + 70);
		} else if (setY > maxY) {
			setY = maxY;
			heading = limitAngleTo360Deg(heading + 80);
		}
		
		//If we are stuck in a corner, get out by turning 135 degrees.
		if ( (setX < minX && setY < minY) || (setX > maxX && setY > maxY) ) {
			heading = limitAngleTo360Deg(heading + 135);
		}
		
		this.setLocationX(setX);
		this.setLocationY(setY);
	}
	
	public int limitAngleTo360Deg(int angle) {
		while (angle < 0) { angle += 360; }
		while (angle > 360) { angle -= 360;}
		if (angle == 360) { angle = 0; }
		return angle;
	}

	public int getHeading() {
		return heading;
	}

	public void setHeading(int heading) {
		this.heading = heading;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
