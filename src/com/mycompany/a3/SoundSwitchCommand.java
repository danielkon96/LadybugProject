package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class SoundSwitchCommand extends Command {
	private GameWorld gwRef;

	public SoundSwitchCommand(GameWorld gwRef) {
		super("Sound");
		this.gwRef = gwRef;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gwRef.soundSwitch();
	}
}
