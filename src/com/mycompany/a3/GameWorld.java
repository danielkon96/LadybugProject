package com.mycompany.a3;

import java.util.Observable;
import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Dialog;

public class GameWorld extends Observable {
	private double MAP_SIZE_X;
	private double MAP_SIZE_Y;
	private GameObjectCollection gameObjCollection;
	private int playerLives = 10;
	private int clock = 0;
	private boolean sound = true;
	
	//Non-copyright sounds found at: www.findsounds.com
	private Sound spiderSound = null;
	private Sound foodSound = null;
	private Sound flagSound = null;
	private BGSound bgSound = null;
	
	public void init(double map_x, double map_y) {
		this.MAP_SIZE_X = map_x;
		this.MAP_SIZE_Y = map_y;
		gameObjCollection = new GameObjectCollection();
		
		Flag one = new Flag(1);
		one.setOntoMap(MAP_SIZE_X, MAP_SIZE_Y);
		one.initGameWorldRef(this);
		Flag two = new Flag(2);
		two.setOntoMap(MAP_SIZE_X, MAP_SIZE_Y);
		two.initGameWorldRef(this);
		Flag three = new Flag(3);
		three.setOntoMap(MAP_SIZE_X, MAP_SIZE_Y);
		three.initGameWorldRef(this);
		Flag four = new Flag(4);
		four.setOntoMap(MAP_SIZE_X, MAP_SIZE_Y);
		four.initGameWorldRef(this);
		
		FoodStation food1 = new FoodStation();
		food1.setOntoMap(MAP_SIZE_X, MAP_SIZE_Y);
		food1.initGameWorldRef(this);
		FoodStation food2 = new FoodStation();
		food2.setOntoMap(MAP_SIZE_X, MAP_SIZE_Y);
		food2.initGameWorldRef(this);
		
		Spider spider1 = new Spider(45, 50);
		spider1.setOntoMap(MAP_SIZE_X, MAP_SIZE_Y);
		spider1.initGameWorldRef(this);
		Spider spider2 = new Spider(255, 70);
		spider2.setOntoMap(MAP_SIZE_X, MAP_SIZE_Y);
		spider2.initGameWorldRef(this);
		
		Ladybug player = Ladybug.getLadybug();
		player.setOntoMap(MAP_SIZE_X, MAP_SIZE_Y);
		player.initGameWorldRef(this);
		
		gameObjCollection.add(one);
		gameObjCollection.add(two);
		gameObjCollection.add(three);
		gameObjCollection.add(four);
		
		gameObjCollection.add(food1);
		gameObjCollection.add(food2);
		
		gameObjCollection.add(spider1);
		gameObjCollection.add(spider2);
		
		gameObjCollection.add(player);
		
		if (spiderSound == null) { spiderSound = new Sound("spiderCollision.wav"); }
		if (foodSound == null) { foodSound = new Sound("foodCollision.wav"); }
		if (flagSound == null) { flagSound = new Sound("flagCollision.wav"); }
		if (bgSound == null) { 
			bgSound = new BGSound("backgroundSound.mp3"); 
		}
		if (sound == true) {
			bgSound.play();
		}
		
		//Update observers MapView and ScoreView.
		super.setChanged();
		super.notifyObservers();
	}
	
	public void accelerate() {
		IIterator gameElements = gameObjCollection.getIterator();
		while (gameElements.hasNext()) {
			Object nextElement = gameElements.getNext();
			
			if (nextElement instanceof Ladybug) {
				Ladybug player = (Ladybug)nextElement;
				
				//maximumSpeedAllowed = (Max Speed) * (Percentage of players health Level.)
				int maximumSpeedAllowed = (int)(player.getMaximumSpeed() * (player.getHealthLevel() * 0.1) );
				int foodLevel = player.getFoodLevel();
				int newSpeed = player.getSpeed() + 10;
			
				//If no problems, accelerate the Ladybug.
				if (foodLevel == 0) {
					Dialog.show("Yikes!", "YOU ARE OUT OF FOOD! RESETTING WORLD.", "Ok", null);
					player.setSpeed(0);
					resetWorld();

				//If the newSpeed is above maximum speed, set the speed to max and return.
				} else if (newSpeed > maximumSpeedAllowed) {
					player.setSpeed(maximumSpeedAllowed);
				
				//If the player has no food left, they cannot move!
				}else {
					player.setSpeed(newSpeed);
				}
				
				//Update observers MapView and ScoreView.
				super.setChanged();
				super.notifyObservers();
				
				return;
			}
		}
	}
	
	public void brake() {
		IIterator gameElements = gameObjCollection.getIterator();
		while (gameElements.hasNext()) {
			Object nextElement = gameElements.getNext();
			
			if (nextElement instanceof Ladybug) {
				Ladybug player = (Ladybug)nextElement;
					
				int newSpeed = player.getSpeed() - 10;
				
				if (newSpeed < 0) {
					player.setSpeed(0);
				} else {
					player.setSpeed(newSpeed);
				}

				//Update observers MapView and ScoreView.
				super.setChanged();
				super.notifyObservers();
				
				return;
			}
		}
	}
	
	public void goLeft() {
		IIterator gameElements = gameObjCollection.getIterator();
		while (gameElements.hasNext()) {
			Object nextElement = gameElements.getNext();
			
			if (nextElement instanceof ISteerable) {
				((ISteerable)nextElement).steerLeft();
				
				//Update observers MapView and ScoreView.
				super.setChanged();
				super.notifyObservers();
				
				return;
			}
		}
	}
	
	public void goRight() {
		IIterator gameElements = gameObjCollection.getIterator();
		while (gameElements.hasNext()) {
			Object nextElement = gameElements.getNext();
			
			if (nextElement instanceof ISteerable) {
				((ISteerable)nextElement).steerRight();
				
				//Update observers MapView and ScoreView.
				super.setChanged();
				super.notifyObservers();
				
				return;
			}
		}
	}
	
	public void collidedWithFlag(int sequenceNumber) {
		IIterator gameElements = gameObjCollection.getIterator();
		while (gameElements.hasNext()) {
			Object nextElement = gameElements.getNext();
			
			if (nextElement instanceof Ladybug) {
				Ladybug player = (Ladybug)nextElement;
				
				if (sequenceNumber == (player.getLastFlagReached()+1) ) {
					if (sound == true)
						flagSound.play();
					if (sequenceNumber == 4) {
						//Update observers MapView and ScoreView.
						super.setChanged();
						super.notifyObservers();
						
						Dialog.show("Game Complete", "GAME WON! CONGRATULATIONS!", "Ok", null);
						exit();
					}
					player.setLastFlagReached(sequenceNumber);
				}
				
				//Update observers MapView and ScoreView.
				super.setChanged();
				super.notifyObservers();
				
				return;
			}
		}
	}
	
	public void collidedWithFoodStation(FoodStation foodStation, Ladybug player) {
		//If the foodStation we hit is not empty, do everything.
		if (foodStation.getCapacity() != 0) {
			if (sound == true) {
				foodSound.play();
			}
			
			player.setFoodLevel(player.getFoodLevel() + foodStation.getCapacity());
			foodStation.setCapacity(0); //Set the foodStation quantity to 0.
			foodStation.setColor(ColorUtil.GREEN); //Set the foodStation color to lime green.
		
			//Add a new FoodStation to gameObjectCollection.
			FoodStation newFoodStation = new FoodStation();
			newFoodStation.setOntoMap(MAP_SIZE_X, MAP_SIZE_Y);
			newFoodStation.initGameWorldRef(this);
			gameObjCollection.add(newFoodStation);
		}
		
		//Update observers MapView and ScoreView.
		super.setChanged();
		super.notifyObservers();
	}
	
	public void collidedWithSpider() {
		IIterator gameElements = gameObjCollection.getIterator();
		while (gameElements.hasNext()) {
			Object nextElement = gameElements.getNext();
			
			if (nextElement instanceof Ladybug) {
				Ladybug player = ((Ladybug)nextElement);
				
				player.setHealthLevel(player.getHealthLevel() - 1);
				player.setColor(ColorUtil.rgb(ColorUtil.red(player.getColor()) - 25, 0, 0));
				
				if (sound == true)
					spiderSound.play();
				
				if (player.getHealthLevel() == 0) {
					Dialog.show("Yikes!", "YOU DIED! RESETTING WORLD.", "Ok", null);
					resetWorld();
					return;
				}
				
				//Update observers MapView and ScoreView.
				super.setChanged();
				super.notifyObservers();
				
				return;
			}
		}
	}
	
	public void clockTick(int elapsedTime) {
		IIterator gameElements = gameObjCollection.getIterator();
		while (gameElements.hasNext()) {
			Object nextElement = gameElements.getNext();
			
			if (nextElement instanceof Moveable) {
				((Moveable)nextElement).move(elapsedTime);
			}
			
			if (nextElement instanceof Spider) {
				//Get the current heading and add or subtract 5-10 degrees.
				int newHeading = ((Spider)nextElement).getHeading() + new Random().nextInt(20) - 10;
				int validHeading = ((Spider)nextElement).limitAngleTo360Deg(newHeading);
				
				((Spider)nextElement).setHeading(validHeading);
			}
			
			if (nextElement instanceof Ladybug) {
				int currFoodLevel = ((Ladybug)nextElement).getFoodLevel();
				int foodConsumption = ((Ladybug)nextElement).getFoodConsumptionRate();
				
				if ( (currFoodLevel - foodConsumption) <= 0) {
					((Ladybug)nextElement).setFoodLevel(0);
					((Ladybug)nextElement).setSpeed(0);
					
					//Update observers MapView and ScoreView.
					super.setChanged();
					super.notifyObservers();
					
					Dialog.show("Yikes!", "YOU ARE OUT OF FOOD! RESETTING WORLD.", "Ok", null);
					resetWorld();
				} else {
					((Ladybug)nextElement).setFoodLevel(currFoodLevel - foodConsumption);
				}
			}
		}
		
		//CHECK FOR COLLISIONS.
		gameElements = gameObjCollection.getIterator();
		while (gameElements.hasNext()) {
			GameObject curObj = (GameObject)gameElements.getNext();
			
			IIterator gameElements2 = gameObjCollection.getIterator();
			while (gameElements2.hasNext()) {
				GameObject otherObj = (GameObject)gameElements2.getNext();
				
				if (curObj != otherObj) { //Make sure that they aren't the same object.
					if (curObj.collidesWith(otherObj)) { //See if we are colliding with another obj.
						curObj.handleCollision(otherObj); //Handle the collision.
					}
				}
			}
		}
		
		clock += elapsedTime;
		
		//Update observers MapView and ScoreView.
		super.setChanged();
		super.notifyObservers();
	}
	
	public void outputMap() {
		IIterator gameElements = gameObjCollection.getIterator();
		while (gameElements.hasNext()) {
			System.out.println(gameElements.getNext().toString() + "\n");
		}
	}
	
	public void exit() {
		System.exit(0);
	}
	
	public void resetWorld() {
		playerLives--;
		if (playerLives == 0) {
			Dialog.show("Game Over", "YOU ARE OUT OF LIVES. GAME OVER! Thank you for playing.", "Ok", null);
			System.exit(0);
		} else {
			
			//Destroy the ladybug.
			IIterator gameElements = gameObjCollection.getIterator();
			while (gameElements.hasNext()) {
				Object nextElement = gameElements.getNext();
				
				if(nextElement instanceof Ladybug) {
					((Ladybug)nextElement).destroyLadybug();
				}
			}
			
			//Reset the gameObjCollection.
			gameObjCollection = new GameObjectCollection();
			System.gc(); //Clean the objects that were in the previous ArrayList by invoking garbage collection.
			clock = 0;
			
			//Update observers MapView and ScoreView.
			super.setChanged();
			super.notifyObservers();
			init(MAP_SIZE_X, MAP_SIZE_Y);
		}
	}
	
	public void soundSwitch() {
		//If sound ON, turn OFF.
		//If sound OFF, turn ON.
		if (sound == true) {
			sound = false; 
			bgSound.pause();
		} else {
			sound = true;
			bgSound.play();
		}
		
		//Update observers MapView and ScoreView.
		super.setChanged();
		super.notifyObservers();
	}
	
	public int getPlayerLives() {
		return playerLives;
	}
	
	public int getClock() {
		return clock;
	}
	
	public int getLastFlagReached() {
		IIterator myIterator = gameObjCollection.getIterator();
		while (myIterator.hasNext()) {
			Object nextElement = myIterator.getNext();
			if (nextElement instanceof Ladybug) {
				return ((Ladybug)nextElement).getLastFlagReached();
			}
		}
		return 1;
	}
	
	public boolean getSound() {
		return sound;
	}
	
	public BGSound getBGSound() {
		return bgSound;
	}
	
	public void setBGSound(BGSound bgSound) {
		this.bgSound = bgSound;
	}
	
	public int getFoodLevel() {
		IIterator myIterator = gameObjCollection.getIterator();
		while (myIterator.hasNext()) {
			Object nextElement = myIterator.getNext();
			if (nextElement instanceof Ladybug) {
				return ((Ladybug)nextElement).getFoodLevel();
			}
		}
		return 0;
	}
	
	public int getHealthLevel() {
		IIterator myIterator = gameObjCollection.getIterator();
		while (myIterator.hasNext()) {
			Object nextElement = myIterator.getNext();
			if (nextElement instanceof Ladybug) {
				return ((Ladybug)nextElement).getHealthLevel();
			}
		}
		return 0;
	}
	
	public GameObjectCollection getGameObjects() {
		return gameObjCollection;
	}
}
