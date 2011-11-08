package org.denevell.android.serialisation.ui;

import test.sugar.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Helper class that simple returns a view for 
 * each item in the AndroidSerialisation class.
 * Used in the SerialAdapter class.
 */
public class ListRow  {

	public static View getView(Context c, String text) {

		LayoutInflater inflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		TextView tv = (TextView) inflator.inflate(R.layout.listrow, null);
		tv.setText(text);
		
		return tv;
	}

}
