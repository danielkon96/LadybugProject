package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class AboutInformationCommand extends Command {

	public AboutInformationCommand() {
		super("About");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String aboutInfo = "Name: Daniel Kondrashevich\n"
				+ "CSC 138 - Section 01\n"
				+ "v2.0";
		
		Dialog.show("About", aboutInfo, "Ok", null);
	}

}
