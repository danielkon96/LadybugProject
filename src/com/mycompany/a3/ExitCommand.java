package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;

public class ExitCommand extends Command {
	private GameWorld gwRef;

	public ExitCommand(GameWorld gwRef) {
		super("Exit");
		this.gwRef = gwRef;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Label yesNo = new Label("Are you sure you want to exit?");
		
		Command okCommand = new Command("Ok");
		Command cancelCommand = new Command("Cancel");
		Command[] commands = new Command[]{okCommand, cancelCommand};
		
		Command c = Dialog.show("Exit", yesNo, commands);
		if (c == okCommand) {
			gwRef.exit();
		} else {
			//Do nothing.
		}
	}

}
