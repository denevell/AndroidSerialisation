package org.denevell.android.serialisation.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is the  class that is serialised.
 * It holds its own children, allowing for 
 * multi level serialisation.
 */
public class Thing implements Serializable {
	//Used so that even if the member fields change
	//serialisation will still occur on this class
	private static final long serialVersionUID = -1904519973250789866L;
	private String mText;
	//The class's children
	private ArrayList<Thing> mCc1 = new ArrayList<Thing>(); 

	public Thing(String a, int b) {
		this.mText=a;
	}
		
	public String getA() {
		return mText;
	}
	
	public void setA(String a) {
		this.mText = a;
	}

	public void addChild(Thing cc1) {
		this.mCc1.add(cc1);
	}

	public int getChildNum() {
		return this.mCc1.size();
	}
	
	public Thing getChild(int i) {
		return this.mCc1.get(i);
	}

	public String getChildText(int position) {
		return this.mCc1.get(position).getA();
	}
	
	public void deleteChild(int childNum) {
		this.mCc1.remove(childNum);
	}
	
	public void editChild(int childNum, String newText) {
		Thing e = this.mCc1.get(childNum);
		e.setA(newText);
	}
	
	/**
	 * Grab the text and cc1 fields from the datastore and 
	 * store them in the class.
	 * @param ois
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws IllegalArgumentException
	 */
	//TODO For fields.get will only return an Object at runtime
	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException, IllegalArgumentException {
		ObjectInputStream.GetField fields = ois.readFields();
		mText = (String)  fields.get("text", "default");;
		mCc1 = (ArrayList<Thing>) fields.get("cc1", null);
	}
	
	/**
	 * Write the text and cc1 member fields to the datastore.
	 * @param oos
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream oos) throws IOException {
		  ObjectOutputStream.PutField fields = oos.putFields();
		  fields.put("text", mText);
		  fields.put("cc1", mCc1);
		  oos.writeFields();
	}
	
}
