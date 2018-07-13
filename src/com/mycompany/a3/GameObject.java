package com.mycompany.a3;

import java.util.Random;
import java.util.Vector;

public abstract class GameObject implements IDrawable, ICollider {
	private double MAP_SIZE_X;
	private double MAP_SIZE_Y;
	
	private int size;
	private double locationX;
	private double locationY;
	private int color;
	
	//Needed for handleCollision() for ICollider Objects.
	private GameWorld gwRef;
	private Vector<GameObject> colliding;
	
	/**
	 * This constructor will assign the location of the GameObject randomly.
	 * 
	 * @param size The size of the GameObject.
	 * @param color The color of the GameObject.
	 */
	public GameObject(int size, int color) {
		this.size = size;
		this.color = color;
		colliding = new Vector<GameObject>();
	}
	
	/**
	 * This constructor has the option to set locationX and locationY manually.
	 * 
	 * @param size The size of the GameObject.
	 * @param locationX The initial location on the x axis.
	 * @param locationY The initial location on the y axis.
	 * @param color The color of the GameObject.
	 */
	public GameObject(int size, double locationX, double locationY, int color) {
		this.size = size;
		this.locationX = locationX;
		this.locationY = locationY;
		this.color = color;
		colliding = new Vector<GameObject>();
	}
	
	public void initGameWorldRef(GameWorld gwRef) {
		this.gwRef = gwRef;
	}
	
	public void setOntoMap(double MAP_SIZE_X, double MAP_SIZE_Y) {
		this.MAP_SIZE_X = MAP_SIZE_X;
		this.MAP_SIZE_Y = MAP_SIZE_Y;
		
		Random rand = new Random();
		int centerOfObjectOffset = size/2;
		
		//Keep object in bounds of the map. To do this we need to make the bounds for the objects.
		//To make the bounds correct, we need to keep track of the center of the object and make sure that
		//the edges of the gameObject do not go out of bounds.
		this.locationX = rand.nextInt((int)MAP_SIZE_X - size) + centerOfObjectOffset; //From centerOfObject - MAP_SIZE - centerOfObject
		this.locationY = rand.nextInt((int)MAP_SIZE_Y - size) + centerOfObjectOffset;
	}
	
	public boolean collidesWith(GameObject otherObj) {
		//#1 = thisObject.
		//#2 = otherObj.
		
		//Left side of object 1 and 2.
		int L1 = (int)this.getLocationX() - this.getSize()/2;
		int L2 = (int)otherObj.getLocationX() - otherObj.getSize()/2;
		//Right side of object 1 and 2.
		int R1 = (int)this.getLocationX() + this.getSize()/2;
		int R2 = (int)otherObj.getLocationX() + otherObj.getSize()/2;
		//Top of object 1 and 2.
		int T1 = (int)this.getLocationY() - this.getSize()/2;
		int T2 = (int)otherObj.getLocationY() - otherObj.getSize()/2;
		//Bottom of object 1 and 2.
		int B1 = (int)this.getLocationY() + this.getSize()/2;
		int B2 = (int)otherObj.getLocationY() + otherObj.getSize()/2;
		
		//If no-overlap, it implies no collision.
		if ( ( R1 < L2 || L1 > R2 ) //No left or right overlap.
		  || ( B1 < T2 || T1 > B2 ) ) { //No top or bottom overlap.
			
			//Check if otherObj is inside vector. If it is, remove it since we are no longer colliding with it.
			if (colliding.contains(otherObj)) {
				colliding.remove(otherObj);
			}
			//Check if thisObject is inside the OtherObjects vector. If it is, remove it since we are no longer colliding with it.
			if (otherObj.getCollidingVector().contains(this)) {
				otherObj.getCollidingVector().remove(this);
			}
			
			return false;
		} else {
			return true;
		}
	}
	
	public double getMapSizeX() {
		return MAP_SIZE_X;
	}
	
	public double getMapSizeY() {
		return MAP_SIZE_Y;
	}
	
	public int getSize() {
		return size;
	}
	
	public double getLocationX() {
		return locationX;
	}
	
	public void setLocationX(double locationX) { 
		this.locationX = locationX;
	}

	public double getLocationY() {
		return locationY;
	}
	
	public void setLocationY(double locationY) { 
		this.locationY = locationY;
	}
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public GameWorld getGWRef() {
		return gwRef;
	}
	
	public Vector<GameObject> getCollidingVector() {
		return colliding;
	}
	
	public void addColliding(GameObject gameObj) {
		this.colliding.add(gameObj);
	}
}
