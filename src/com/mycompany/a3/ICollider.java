package com.mycompany.a3;

public interface ICollider {

	public boolean collidesWith(GameObject otherObj);
	
	public void handleCollision(GameObject otherObj);
}
