package org.denevell.android.serialisation;

import org.denevell.android.serialisation.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Activity to add a some text.
 * Returns the added text in the Intet.
 */
public class Add extends Activity {
	public static final int ADDED_SOMETHING = 0;
	public static final int ADDED_NOTHING = 1;
	public static String EXTRA_ADDED_TEXT = "text";
	private EditText mEdittext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        this.mEdittext = (EditText) findViewById(R.id.add_edittext_addtext);
	}
	
	/**
	 * Set an Intent result with the text in it
	 */
	@Override
	public void onBackPressed() {
		Intent i = new Intent();
		String s = this.mEdittext.getText().toString();
		int res;
		if(s.length()>0) {
			i.putExtra(Add.EXTRA_ADDED_TEXT, s);
			res = Add.ADDED_SOMETHING;
			setResult(res, i);			
		} else {
			res = Add.ADDED_NOTHING;
			setResult(res, null);			
		}
		super.onBackPressed();
	}

}