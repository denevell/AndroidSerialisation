package org.denevell.android.serialisation;

import org.denevell.android.serialisation.data.SaveData;
import org.denevell.android.serialisation.observable.Listener;
import org.denevell.android.serialisation.ui.SerialAdapter;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.TextView;

/**
 * Display a list of the data in the serialised datastore
 * Add, edit and delete them.
 * Listens of the serialisation datastore for updates.
 *
 */
public class AndroidSerialisation extends ListActivity implements Listener {
    private static final int RESULT_ADD_TEXT = 0;
	private static final int RESULT_EDIT_TEXT = 1;    
	
	public static final String EXTRA_TEXT_TO_EDIT = "message";
	public static final String EXTRA_ITEM_ID= "item_id";
	
	
	//adapter to show the serialised data in the 
	//ListActivity
	private SerialAdapter mAdapter;
	private String mSealisedfilename = "classy1";
	//class used to save the classes
	private SaveData mSerialsedData;
	private static Context sCONTEXT;

	/** 
	 * Published the Context object as a static field.
	 * Sets up the Serialised object, with the filename.
	 * Sets a context menu for the list.
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	sCONTEXT = getApplicationContext();
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        this.mSerialsedData = new SaveData(this.mSealisedfilename);
        
        //Give it some sample data.
        if(mSerialsedData.getData().getChildNum()==0) {
            for (int i = 0; i < 10; i++) { //if you take this over 2000 you'll app'll slow.
    			mSerialsedData.addToList("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
    		}        	
        }
        
        this.mSerialsedData.addListener(this);
        
        setAdapater();        
        registerForContextMenu(getListView());
    }

    public static Context getAppContext() {
    	return sCONTEXT;
    }
    
	/**
	 * Create the menu. For add.
	 **/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean b =  super.onCreateOptionsMenu(menu);
		MenuInflater inf = new MenuInflater(this);
		inf.inflate(R.menu.optionsmenu, menu);
		return b;
	}
	
	/** 
	 * Only button atm is to add.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i = new Intent(this, Add.class);
		if(item.getItemId()==R.id.add_menu_button) {
			startActivityForResult(i, AndroidSerialisation.RESULT_ADD_TEXT);			
		}
		return super.onOptionsItemSelected(item);
	}
	
	/** 
	 * Create the context menu
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		MenuInflater mif = getMenuInflater();
		mif.inflate(R.menu.contextmenu, menu);
	}

	/** 
	 * When a list item is long clicked
	 */	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		TextView tv = (TextView) info.targetView;
		int itemId = item.getItemId();
		if(itemId==R.id.context_menu_delete) {
			this.mSerialsedData.deleteFromList(info.id);
		} else if(itemId==R.id.context_menu_edit) {			
			Intent i = new Intent(this, Edit.class);
			int adapterPos = info.position;
			i.putExtra(AndroidSerialisation.EXTRA_TEXT_TO_EDIT, tv.getText());
			i.putExtra(AndroidSerialisation.EXTRA_ITEM_ID, adapterPos);			
			startActivityForResult(i, AndroidSerialisation.RESULT_EDIT_TEXT);						
		}
		return super.onContextItemSelected(item);
	}


	/** 
	 * Gets the text from the Add activity. Add it to the data in the
	 * system. 
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(data==null) return;
		if(requestCode == AndroidSerialisation.RESULT_ADD_TEXT 
				&& resultCode == Add.ADDED_SOMETHING) {
			parseAddActivityResult(data);
		} else if(requestCode == AndroidSerialisation.RESULT_EDIT_TEXT 
				&& resultCode == Edit.EDITED_SOMETHING) {
			parseEditActivityResult(data);
		}
	}

	private void parseAddActivityResult(Intent data) {
		String text = data.getExtras().getString(Add.EXTRA_ADDED_TEXT);
		this.mSerialsedData.addToList(text);
	}

	private void parseEditActivityResult(Intent data) {
		String text = data.getStringExtra(Edit.EXTRA_EDITED_TEXT);
		int id = data.getIntExtra(Edit.EXTRA_ITEM_ID, -1);
		if(text!=null && id!=-1) {
			this.mSerialsedData.editListItem(id, text);
		}
	}

	/** 
	 * Called when the SaveData class is updated by a add, del, edit function.
	 */
	@Override
	public void observerableUpdated(Object c) {
	      this.mSerialsedData.writeData(this);    
	      setAdapater();
	}
	
    /** 
     * Read the data from the seralised object.
     * Then set the list's adapter using this data.
     */
	private void setAdapater() {
        this.mAdapter = new SerialAdapter(this.mSerialsedData.getData());
        setListAdapter(this.mAdapter);
	}	

}