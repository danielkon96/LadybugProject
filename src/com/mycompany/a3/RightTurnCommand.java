package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class RightTurnCommand extends Command {
	private GameWorld gwRef;

	public RightTurnCommand(GameWorld gwRef) {
		super("Right");
		this.gwRef = gwRef;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gwRef.goRight();
	}
}
