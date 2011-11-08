package org.denevell.android.serialisation;

import org.denevell.android.serialisation.R;
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
	
	private EditText mEdittext;
	private String mDataToEdit;
	private int mItemId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        this.mEdittext = (EditText) findViewById(R.id.add_edittext_addtext);
        getIntentData();
        this.mEdittext.setText(this.mDataToEdit);
	}
	
	/**
	 * Set an Intent with the edit text
	 */
	@Override
	public void onBackPressed() {
		Intent i = new Intent();
		String s = this.mEdittext.getText().toString();
		int res;
		if(s.length()>0) {
			i.putExtra(Edit.EXTRA_EDITED_TEXT, s);
			i.putExtra(Edit.EXTRA_ITEM_ID, this.mItemId);
			
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
				this.mDataToEdit = s;
				this.mItemId = id;
				return;
			}
		}
		Log.e("AndroidSerialisation", "No Intent with correct data passed to Edit activity.");
		finish();
	}

}