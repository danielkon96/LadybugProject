package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;

public class ScoreView extends Container implements Observer {
	private Label timeLabel;
	private Label timeValue;
	
	private Label livesLeft;
	private Label livesValue;
	
	private Label lastFlagLabel;
	private Label lastFlagValue;
	
	private Label foodLevel;
	private Label foodValue;
	
	private Label healthLevel;
	private Label healthValue;
	
	private Label soundLabel;
	private Label soundValue;
	
	public ScoreView() {
		this.setLayout(new FlowLayout(Component.CENTER));
		
		//Create labels.
		timeLabel = new Label("Time:");
		livesLeft = new Label("Lives Left:");
		lastFlagLabel = new Label("Last Flag Reached:");
		foodLevel = new Label("Food Level:");
		healthLevel = new Label("Health Level:");
		soundLabel = new Label("Sound:");
		
		//Create value labels.
		timeValue = new Label("-1");
		livesValue = new Label("-1");
		lastFlagValue = new Label("-1");
		foodValue = new Label("-1");
		healthValue = new Label("-1");
		soundValue = new Label("ON");
		
		//SetPadding for value labels.
		int paddingVal = 1;
		timeValue.getAllStyles().setPadding(RIGHT, paddingVal);
		livesValue.getAllStyles().setPadding(RIGHT, paddingVal);
		lastFlagValue.getAllStyles().setPadding(RIGHT, paddingVal);
		foodValue.getAllStyles().setPadding(RIGHT, paddingVal);
		healthValue.getAllStyles().setPadding(RIGHT, paddingVal);
		soundValue.getAllStyles().setPadding(RIGHT, paddingVal);
		timeValue.getAllStyles().setPadding(LEFT, paddingVal);
		livesValue.getAllStyles().setPadding(LEFT, paddingVal);
		lastFlagValue.getAllStyles().setPadding(LEFT, paddingVal);
		foodValue.getAllStyles().setPadding(LEFT, paddingVal);
		healthValue.getAllStyles().setPadding(LEFT, paddingVal);
		soundValue.getAllStyles().setPadding(LEFT, paddingVal);
		
		//Stlye labels.
		timeLabel.getAllStyles().setFgColor(ColorUtil.BLUE);
		timeValue.getAllStyles().setFgColor(ColorUtil.BLUE);
		livesLeft.getAllStyles().setFgColor(ColorUtil.BLUE);
		livesValue.getAllStyles().setFgColor(ColorUtil.BLUE);
		lastFlagLabel.getAllStyles().setFgColor(ColorUtil.BLUE);
		lastFlagValue.getAllStyles().setFgColor(ColorUtil.BLUE);
		foodLevel.getAllStyles().setFgColor(ColorUtil.BLUE);
		foodValue.getAllStyles().setFgColor(ColorUtil.BLUE);
		healthLevel.getAllStyles().setFgColor(ColorUtil.BLUE);
		healthValue.getAllStyles().setFgColor(ColorUtil.BLUE);
		soundLabel.getAllStyles().setFgColor(ColorUtil.BLUE);
		soundValue.getAllStyles().setFgColor(ColorUtil.BLUE);
		
		//Add components to the container.
		this.addComponent(timeLabel);
		this.addComponent(timeValue);
		this.addComponent(livesLeft);
		this.addComponent(livesValue);
		this.addComponent(lastFlagLabel);
		this.addComponent(lastFlagValue);
		this.addComponent(foodLevel);
		this.addComponent(foodValue);
		this.addComponent(healthLevel);
		this.addComponent(healthValue);
		this.addComponent(soundLabel);
		this.addComponent(soundValue);
	}

	public void update(Observable o, Object arg) {
		//Update all the labels and revalidate for reupdating the layout.
		GameWorld gwRef = (GameWorld)o;
		
		//If we are at one second or more show the time. Otherwise, we're still at "0" seconds.
		if ( (gwRef.getClock() / 1000) > 0) {
			timeValue.setText(Integer.toString(gwRef.getClock() / 1000));
		} else {
			timeValue.setText("0");
		}
		timeValue.getParent().revalidate();
		livesValue.setText(Integer.toString(gwRef.getPlayerLives()));
		livesValue.getParent().revalidate();
		lastFlagValue.setText(Integer.toString(gwRef.getLastFlagReached()));
		foodValue.setText(Integer.toString(gwRef.getFoodLevel()));
		foodValue.getParent().revalidate();
		healthValue.setText(Integer.toString(gwRef.getHealthLevel()));
		healthValue.getParent().revalidate();
		if (gwRef.getSound() == true) {
			soundValue.setText("ON");
		} else {
			soundValue.setText("OFF");
		}
	}
	
}
