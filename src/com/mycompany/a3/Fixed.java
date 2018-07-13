package com.mycompany.a3;

public abstract class Fixed extends GameObject {
	
	/**
	 * This constructor will assign the location of the GameObject randomly.
	 * See @GameObject constructor for more details...
	 * 
	 * @param size
	 * @param color
	 */
	public Fixed(int size, int color) {
		super(size, color);
	}
	
	/**
	 * See @GameObject constructor for more details...
	 * 
	 * @param size
	 * @param locationX
	 * @param locationY
	 * @param color
	 */
	public Fixed(int size, double locationX, double locationY, int color) {
		super(size, locationX, locationY, color);
	}

	@Override
	public void setLocationX(double x) {
		super.setLocationX(x); 
	}
	
	@Override
	public void setLocationY(double y) {
		super.setLocationY(y); 
	}
}
