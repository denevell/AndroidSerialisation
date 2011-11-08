package org.denevell.android.serialisation;

import test.sugar.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

/**
 * Activity to edit text and return
 * it in an Intet.
 *
 */
public class Edit extends Activity {
	//results passed via the returned intent
	public static final int EDITED_SOMETHING = 0;
	public static final int EDITED_NOTHING = 1;
	
	//extra values passed back in the intent
	public static final String EXTRA_ITEM_ID = "item_id";
	public static final String EXTRA_EDITED_TEXT = "text";
	
	private EditText edittext;
	private String dataToEdit;
	private int itemId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        this.edittext = (EditText) findViewById(R.id.add_edittext_addtext);
        getIntentData();
        this.edittext.setText(this.dataToEdit);
	}
	
	/**
	 * Set an Intent with the edit text
	 */
	@Override
	public void onBackPressed() {
		Intent i = new Intent();
		String s = this.edittext.getText().toString();
		int res;
		if(s.length()>0) {
			i.putExtra(Edit.EXTRA_EDITED_TEXT, s);
			i.putExtra(Edit.EXTRA_ITEM_ID, this.itemId);
			
			res = Edit.EDITED_SOMETHING;
			setResult(res, i);			
		} else {
			res = Edit.EDITED_NOTHING;
			setResult(res, null);			
		}
		super.onBackPressed();
	}
	
	/**
	 * Get the text from the Intent passed.
	 */
	private void getIntentData() {
		Intent i = getIntent();
		if(i!=null && i.getExtras()!=null) {
			String s = i.getExtras().getString(AndroidSerialisation.EXTRA_TEXT_TO_EDIT);
			int id = i.getExtras().getInt(AndroidSerialisation.EXTRA_ITEM_ID);		
			if(i.getExtras().containsKey(AndroidSerialisation.EXTRA_ITEM_ID) &&
			   s!=null) { 
				this.dataToEdit = s;
				this.itemId = id;
				return;
			}
		}
		Log.e("AndroidSerialisation", "No Intent with correct data passed to Edit activity.");
		finish();
	}

}