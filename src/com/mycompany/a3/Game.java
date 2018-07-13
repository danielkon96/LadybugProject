package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;

public class Game extends Form implements Runnable {
	private GameWorld gw;
	private MapView mv;
	private ScoreView sv;
	private UITimer gameClock;
	private int refreshRate = 100; //Refresh rate in ms.
	private boolean isPaused = false;
	private Button acclButton, brakeButton, leftButton, rightButton, positionButton, playPauseButton;
	private AccelerateCommand acclCommand;
	private BrakeCommand brakeCommand;
	private LeftTurnCommand leftCommand;
	private RightTurnCommand rightCommand;
	private ExitCommand exitCommand;
	private SoundSwitchCommand soundCommand;
	private AboutInformationCommand aboutCommand;
	private HelpInformationCommand helpCommand;
	private PositionCommand positionCommand;
	private PlayPauseCommand playPauseCommand;
	private CheckBox soundCheck;

	public Game() {
		gw = new GameWorld();
		
		//Create and schedule game clock.
		gameClock = new UITimer(this);
		
		//Create the "Observers"
		mv = new MapView(this, gw);
		sv = new ScoreView();
		
		//Register the "Observers"
		gw.addObserver(mv);
		gw.addObserver(sv);
		
		//Create all command objects.
		acclCommand = new AccelerateCommand(gw);
		brakeCommand = new BrakeCommand(gw);
		leftCommand = new LeftTurnCommand(gw);
		rightCommand = new RightTurnCommand(gw);
		exitCommand = new ExitCommand(gw);
		soundCommand = new SoundSwitchCommand(gw);
		aboutCommand = new AboutInformationCommand();
		helpCommand = new HelpInformationCommand();
		positionCommand = new PositionCommand(this);
		playPauseCommand = new PlayPauseCommand(this, gw);
		
		//Add all command objects to title bar.
		Toolbar myToolbar = new Toolbar();
		myToolbar.setOnTopSideMenu(false);
		setToolbar(myToolbar);
		myToolbar.setTitle("Ladybug Game");
		myToolbar.addCommandToRightBar(helpCommand);
		
		//Create sound checkbox.
		soundCheck = new CheckBox("Sound");
		soundCheck.getAllStyles().setBgTransparency(255);
		soundCheck.getAllStyles().setBgColor(ColorUtil.LTGRAY);
		soundCheck.setSelected(true);
		soundCheck.setCommand(soundCommand);
		
		//Add all command objects to side menu.
		myToolbar.addCommandToSideMenu(acclCommand);
		myToolbar.addComponentToSideMenu(soundCheck);
		myToolbar.addCommandToSideMenu(aboutCommand);
		myToolbar.addCommandToSideMenu(exitCommand);
		
		//Bind all command objects to keys.
		addKeyListener('a', acclCommand);
		addKeyListener('b', brakeCommand);
		addKeyListener('l', leftCommand);
		addKeyListener('r', rightCommand);
		addKeyListener('x', exitCommand);
		
		//Set the Game layout.
		setLayout(new BorderLayout());
		
		//Create the control containers
		Container leftContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		leftContainer.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		Container bottomContainer = new Container(new FlowLayout(Component.CENTER));
		bottomContainer.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		Container rightContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		rightContainer.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		
		//Create all the buttons.
		acclButton = new Button("Accelerate");
		brakeButton = new Button("Brake");
		leftButton = new Button("Left");
		rightButton = new Button("Right");
		positionButton = new Button("Position");
		playPauseButton = new Button("Pause");
		
		//Change button styles.
		changeButtonStyle(acclButton);
		changeButtonStyle(brakeButton);
		changeButtonStyle(leftButton);
		changeButtonStyle(rightButton);
		changeButtonStyle(positionButton);
		changeButtonStyle(playPauseButton);
		
		//Add buttons to control containers.
		leftContainer.addComponent(acclButton);
		leftContainer.addComponent(leftButton);
		bottomContainer.addComponent(positionButton);
		bottomContainer.addComponent(playPauseButton);
		rightContainer.addComponent(brakeButton);
		rightContainer.addComponent(rightButton);
		
		//Add all command objects to the buttons.
		acclButton.setCommand(acclCommand);
		brakeButton.setCommand(brakeCommand);
		leftButton.setCommand(leftCommand);
		rightButton.setCommand(rightCommand);
		positionButton.setCommand(positionCommand);
		playPauseButton.setCommand(playPauseCommand);
		
		positionButton.setEnabled(false);
		
		//Add control containers, MapView and ScoreView to the form.
		add(BorderLayout.WEST, leftContainer);
		add(BorderLayout.SOUTH, bottomContainer);
		add(BorderLayout.EAST, rightContainer);
		add(BorderLayout.CENTER, mv);
		add(BorderLayout.NORTH, sv);
		
		this.show();
		gw.init(mv.getWidth(), mv.getHeight());
		gameClock.schedule(refreshRate, true, this);
	}
	
	private void changeButtonStyle(Button btn) {
		btn.getUnselectedStyle().setBgTransparency(255);
		btn.getUnselectedStyle().setBgColor(ColorUtil.CYAN);
		btn.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		btn.getDisabledStyle().setBgColor(ColorUtil.BLUE);
		btn.getDisabledStyle().setFgColor(ColorUtil.BLUE);
		btn.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLUE));
		btn.getAllStyles().setPadding(Component.TOP, 5);
		btn.getAllStyles().setPadding(Component.BOTTOM, 5);
		btn.getAllStyles().setMargin(5,5,5,5);
	}
	
	public void setPauseModeCommands() {
		//Disable all the buttons.
		acclButton.setEnabled(false);
		brakeButton.setEnabled(false);
		leftButton.setEnabled(false);
		rightButton.setEnabled(false);
	
		//Bind all command objects to keys.
		removeKeyListener('a', acclCommand);
		removeKeyListener('b', brakeCommand);
		removeKeyListener('l', leftCommand);
		removeKeyListener('r', rightCommand);
		
		//Disable sideMenuCommands
		acclCommand.setEnabled(false);
		soundCommand.setEnabled(false);
		soundCheck.setEnabled(false);
		
		positionButton.setEnabled(true);
	}
	
	public void setPlayModeCommands() {
		//Enable all the buttons.
		acclButton.setEnabled(true);
		brakeButton.setEnabled(true);
		leftButton.setEnabled(true);
		rightButton.setEnabled(true);
		
		//Enable all the keyListeners back.
		addKeyListener('a', acclCommand);
		addKeyListener('b', brakeCommand);
		addKeyListener('l', leftCommand);
		addKeyListener('r', rightCommand);
		
		//Enable sideMenuCommands
		acclCommand.setEnabled(true);
		soundCommand.setEnabled(true);
		soundCheck.setEnabled(true);
		
		positionButton.setEnabled(false);
	}
	
	public void run() {
		gw.clockTick(refreshRate);
	}
	
	public UITimer getGameClock() {
		return gameClock;
	}
	
	public void setGameClock(UITimer gClock) {
		this.gameClock = gClock;
	}
	
	public Button getPlayPauseButton() {
		return playPauseButton;
	}
	
	public void setPlayPauseButton(Button playPauseButton) {
		this.playPauseButton = playPauseButton;
	}
	
	public boolean getIsPaused() {
		return isPaused;
	}
	
	public void setIsPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}
	
	public int getRefreshRate() {
		return refreshRate;
	}
	
	public void setRefreshRate(int refreshRate) {
		this.refreshRate = refreshRate;
	}
	
	public MapView getMapView() {
		return mv;
	}
	
}
