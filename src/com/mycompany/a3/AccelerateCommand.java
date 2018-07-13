package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class AccelerateCommand extends Command {
	private GameWorld gwRef;

	public AccelerateCommand(GameWorld gwRef) {
		super("Accelerate");
		this.gwRef = gwRef;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gwRef.accelerate();
	}

}
