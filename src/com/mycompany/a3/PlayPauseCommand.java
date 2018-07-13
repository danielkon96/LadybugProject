package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PlayPauseCommand extends Command {
	private Game gRef;
	private GameWorld gwRef;

	public PlayPauseCommand(Game gRef, GameWorld gwRef) {
		super("Pause");
		this.gRef = gRef;
		this.gwRef = gwRef;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//If the game isPaused, resume. Otherwise, we need to pause the game.
		if (gRef.getIsPaused()) {
			gRef.setIsPaused(false);
			gRef.getPlayPauseButton().setText("Pause");
			gRef.setPlayModeCommands();
			
			//If sound is on, start the background sound from the beginning.
			if (gwRef.getSound() == true) {
				gwRef.getBGSound().run();
			}
			
			gRef.getMapView().unselectAll();
			gRef.getGameClock().schedule(gRef.getRefreshRate(), true, gRef);
		} else {
			gRef.setIsPaused(true);
			gRef.getPlayPauseButton().setText("Play");
			gwRef.getBGSound().pause();
			gRef.setPauseModeCommands();
			
			gRef.getGameClock().cancel();
		}
		
	}
}
