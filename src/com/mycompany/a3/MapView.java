package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.plaf.Border;

public class MapView extends Container implements Observer {
	private Game gRef;
	private GameWorld gwRef;
	private boolean posCmdActivated = false;
	
	public MapView(Game gRef, GameWorld gwRef) {
		this.gRef = gRef;
		this.gwRef = gwRef;
		this.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.rgb(245, 50, 10)));
	}

	public void update(Observable o, Object arg) {
		((GameWorld) o).outputMap();
		this.repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		IIterator gameObjs = gwRef.getGameObjects().getIterator();
		Point cmpPosRelPrnt = new Point(getX(), getY());
		
		//Iterate through all the gameObjects and draw them on the mapview.
		while (gameObjs.hasNext()) {
			((GameObject)gameObjs.getNext()).draw(g, cmpPosRelPrnt);
		}
	}

	@Override
	public void pointerPressed(int x, int y) {
		//Location pressed relative to MapView
		x = x - getAbsoluteX();
		y = y - getAbsoluteY();
		
		IIterator gameObjs = gwRef.getGameObjects().getIterator();
		
		boolean somethingSelected = false;
		//If PositionCommand has been pressed...
		if (posCmdActivated) {

			while (gameObjs.hasNext()) {
				Object nextElement = gameObjs.getNext();
				
				//Find a selected object and change the location.
				if (nextElement instanceof ISelectable) {
					
					if (((ISelectable)nextElement).isSelected()) {
						((GameObject)nextElement).setLocationX(x);
						((GameObject)nextElement).setLocationY(y);
						somethingSelected = true;
					}
				}
			}
			posCmdActivated = false;
		}
		
		gameObjs = gwRef.getGameObjects().getIterator();
		//If nothing was selected and we pressed on an object, highlight it.
		if (!somethingSelected) {
			while (gameObjs.hasNext()) {
				Object nextElement = gameObjs.getNext();
				
				//If the nextElement is selectable and is selected, setSelected to true. 
				//Otherwise set all other objects to unselected.
				if (nextElement instanceof ISelectable) {
					ISelectable sObj = (ISelectable)nextElement;
					
					//Points relative to MapView parent.
					Point ptrPosRelPrnt = new Point(x, y);
					Point cmpPosRelPrnt = new Point((int)(((GameObject)nextElement).getLocationX()), (int)(((GameObject)nextElement).getLocationY()));
					
					if (sObj.contains(ptrPosRelPrnt, cmpPosRelPrnt) && gRef.getIsPaused()) {
						unselectAll();
						sObj.setSelected(true);
					} else {
						sObj.setSelected(false);
					}
				}
			}
		}
		//Re-draw all the objects.
		this.repaint();
	}
	
	public void unselectAll() {
		IIterator gameObjs = gwRef.getGameObjects().getIterator();
		while (gameObjs.hasNext()) {
			Object nextElement = gameObjs.getNext();

			if (nextElement instanceof ISelectable) {
				((ISelectable) nextElement).setSelected(false);
			}
		}
		this.repaint();
	}
	
	public void posCommandActivated() {
		posCmdActivated = true;
	}
}
