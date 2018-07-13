package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class LeftTurnCommand extends Command {
	private GameWorld gwRef;

	public LeftTurnCommand(GameWorld gwRef) {
		super("Left");
		this.gwRef = gwRef;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gwRef.goLeft();
	}
}
