package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PositionCommand extends Command {
	private Game gRef;

	public PositionCommand(Game gRef) {
		super("Position");
		this.gRef = gRef;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gRef.getMapView().posCommandActivated();
	}
}
