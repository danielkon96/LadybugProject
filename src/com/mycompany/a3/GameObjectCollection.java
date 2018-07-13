package com.mycompany.a3;

import java.util.ArrayList;

public class GameObjectCollection implements ICollection {
	private ArrayList<GameObject> theCollection;
	
	public GameObjectCollection() {
		theCollection = new ArrayList<GameObject>();
	}
	
	public void add(GameObject newObject) {
		theCollection.add(newObject);
	}

	public IIterator getIterator() {
		return new GameObjectIterator();
	}

	private class GameObjectIterator implements IIterator {
		private int currElementIndex;
		
		public GameObjectIterator() {
			currElementIndex = -1;
		}
		
		public boolean hasNext() {
			//If collection is empty, return false.
			if (theCollection.size() <= 0) {
				return false;
			}
			//If the current index is at the end of the collection, return false.
			if (currElementIndex == theCollection.size() - 1) {
				return false;
			}
			
			return true;
		}

		public Object getNext() {
			currElementIndex++;
			return (theCollection.get(currElementIndex));
		}
	} //End private inner class.
} //End outer class.
