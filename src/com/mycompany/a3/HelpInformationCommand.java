package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class HelpInformationCommand extends Command {

	public HelpInformationCommand() {
		super("Help");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String gameCommands =
				  "a - Accelerate\n"
				+ "b - Brake\n"
				+ "l - Go Left\n"
				+ "r - Go Right\n"
				+ "x - Exit Game";
		
		Dialog.show("Game Commands", gameCommands, "Ok", null);
	}
}
