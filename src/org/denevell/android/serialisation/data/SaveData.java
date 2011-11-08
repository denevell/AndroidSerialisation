package org.denevell.android.serialisation.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.denevell.android.serialisation.AndroidSerialisation;
import org.denevell.android.serialisation.observable.Listener;
import org.denevell.android.serialisation.observable.Observable;

import android.content.Context;

/**
 * Saves, add and deletes the data via serialisation.
 *
 */
public class SaveData implements Observable {
	private String filename;
	//The class we're serialising. 
	private Thing data;
	private ArrayList<Listener> listeners = new ArrayList<Listener>();

	/**
	 * Set the filename for the datastore.
	 * Then read the data for that into the 
	 * class.
	 * If there's nothing there, put some
	 * test data
	 * @param filename
	 */
	public SaveData(String filename) {
		this.filename = filename;
		this.data = this.readObj();		
		if(this.data==null) 
			this.data = new Thing("", 0);		
	}
	
	/**
	 * Add all the data that's been up in the data
	 * field to the filename specified in the constructor.
	 * @param c
	 */
	public void writeData(Context c) {
		FileOutputStream fos;
		try {
		      fos = c.openFileOutput(this.filename, Context.MODE_PRIVATE);
		      ObjectOutputStream oos = new ObjectOutputStream(fos);
		      oos.writeObject(this.data);
		      oos.close();
		      fos.close();           
		} catch (FileNotFoundException e) {
		      e.printStackTrace();
		} catch (IOException e) {
		     e.printStackTrace();
		}
	}
	
	/** 
	 * Read the data from the datastore into a Thing class.
	 * @return
	 */
	public Thing readObj() {
		Context c = AndroidSerialisation.getAppContext();
		FileInputStream fos;
		try {
		      fos = c.openFileInput(this.filename);
		      ObjectInputStream oos = new ObjectInputStream(fos);
		      Thing ret = (Thing) oos.readObject();
		      oos.close();
		      fos.close();      
		      return ret;
		} catch (FileNotFoundException e) {
		      e.printStackTrace();
		} catch (IOException e) {
		     e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
		return null;
	}
	
	/** 
	 * Make a new child for the Thing class. Then add it.
	 * @param text for the child object
	 */	
	public Thing getData() {
		return this.data;
	}
	
	/** 
	 * Make a new child for the Thing class. Then add it.
	 * @param text for the child object
	 */
	public void addToList(String text) {
		Thing c1 = new Thing(text, 0);
		this.data.addChild(c1);
		this.notifyListeners();		
	}	
	
	/** 
	 * Delete a child from the ArrayList in Thing
	 * @param id the num in the ArrayList object
	 */	
	public void deleteFromList(long id) {
		this.data.deleteChild((int) id);
		this.notifyListeners();		
	}

	@Override
	public void addListener(Listener l) {
		this.listeners.add(l);
	}		
	
	private void notifyListeners() {
		for (Iterator<Listener> iterator = this.listeners.iterator(); iterator.hasNext();) {
			iterator.next().observerableUpdated(this.data);
		}
	}

	/**
	 * Edit the class at the specified id
	 * @param id
	 * @param text
	 */
	public void editListItem(int id, String text) {
		this.data.editChild(id, text);
		this.notifyListeners();				
	}
}
