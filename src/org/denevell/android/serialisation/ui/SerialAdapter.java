package org.denevell.android.serialisation.ui;

import org.denevell.android.serialisation.AndroidSerialisation;
import org.denevell.android.serialisation.data.Thing;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * This grabs the child data from the Thing class
 * so that a ListActivity can use it.
 *
 */
public class SerialAdapter extends BaseAdapter {

	private Thing mData;

	public SerialAdapter(Thing data) {
		this.mData = data;
	}

	@Override
	public int getCount() {
		return this.mData.getChildNum();
	}

	@Override
	public Object getItem(int position) {
		return this.mData.getChild(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView!=null) {
			TextView t = (TextView) convertView;
			t.setText(this.mData.getChildText(position));
			return t;
		} else
		 return ListRow.getView(AndroidSerialisation.getAppContext(), this.mData.getChildText(position));
	}

}
