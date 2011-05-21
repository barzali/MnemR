<<<<<<< HEAD
//package com.mnemr.controller;
//
//import com.mnemr.provider.Mnem; 
//
//import android.content.Context;
//import android.database.Cursor;
//import android.graphics.Color;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AbsListView;
//import android.widget.CursorTreeAdapter;
//import android.widget.TextView;
//
//public class MnemoListAdapter extends CursorTreeAdapter {
//
//	public MnemoListAdapter(Cursor cursor, Context context) {
//		super(cursor, context);
//		// TODO Auto-generated constructor stub
//	}
//public MnemoListAdapter(     ) {
//	// TODO Auto-generated constructor stub
//}
//	@Override
//	protected View newGroupView(Context context, Cursor cursor, boolean isExpanded, ViewGroup parent) {
//		// Layout parameters for the ExpandableListView
//		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
//				ViewGroup.LayoutParams.MATCH_PARENT, 64);
//
//		TextView textView = new TextView( context);
//		textView.setLayoutParams(lp);
//		// Center the text vertically
//		textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//		// Set the text starting position
//		textView.setPadding(36, 0, 0, 0);
//		return textView;
//		
//	}
//
//	@Override
//	protected void bindGroupView(View view, Context context, Cursor cursor, boolean isExpanded) {
//		Log.d(getClass().getSimpleName(), cursor.getString(1));
//		((TextView) view).setText(cursor.getString(1));
//	//	scaleToFit((TextView) view);
//	}
//
//	@Override
//	protected Cursor getChildrenCursor(Cursor groupCursor) {
//		return getContentResolver()
//				.query(Mnem.CONTENT_URI, Mnem.PROJECTION, null, null, null);
//		//TODO machen!
//	}
//
//	@Override
//	protected View newChildView(Context context, Cursor cursor,
//			boolean isLastChild, ViewGroup parent) {
//		TextView v = new TextView(context);
//		v.setBackgroundColor(Color.BLACK);
//		v.setTextColor(Color.LTGRAY);
//		v.setGravity(Gravity.CENTER_HORIZONTAL
//				| Gravity.CENTER_VERTICAL);
//		return v;
//	}
//
//	@Override
//	protected void bindChildView(View view, Context context,
//			Cursor cursor, boolean isLastChild) {
//		((TextView) view).setText(cursor.getString(1));
//		//scaleToFit((TextView) view);
//	}
//}
//
//	  
//}
=======
/**
  * Copyright (c) 2011: mnemr.com cobntributors. All rights reserved.
  *
  * This program is free software: you can redistribute it and/or modify
  * it under the terms of the GNU General Public License as published 
  * the Free Software Foundation, either version 3 of the License, 
  * (at your option) any later version.
  *
  * program is distributed in the hope that it will be 
  * but WITHOUT ANY WARRANTY; without even the implied warranty 
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See 
  * GNU General Public License for more details.
  *
  * You should have received a copy of the GNU General Public 
  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
  *
  **/

package com.mnemr.controller;

import android.content.Context;
import android.database.Cursor;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CursorTreeAdapter;
import android.widget.TextView;

public class MnemoListAdapter extends CursorTreeAdapter {

	private Context context;
	
	public MnemoListAdapter(Cursor cursor, Context context) {
		super(cursor, context);
		setContext(context);
	}


	// Sample data set. children[i] contains the children (String[]) for
	// groups[i].
	private String[] groups = { "People Names", "Dog Names", "Cat Names",
			"Fish Names" };
	private String[][] children = { { "Arnold", "Barry", "Chuck", "David" },
			{ "Ace", "Bandit", "Cha-Cha", "Deuce" }, { "Fluffy", "Snuggles" },
			{ "Goldy", "Bubbles" } };

	public Cursor getChild(int groupPosition, int childPosition) {
		return null;
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public int getChildrenCount(int groupPosition) {
		return children[groupPosition].length;
	}

	public TextView getGenericView() {
		// Layout parameters for the ExpandableListView
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, 64);

		TextView textView = new TextView( getContext());
		textView.setLayoutParams(lp);
		// Center the text vertically
		textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		// Set the text starting position
		textView.setPadding(36, 0, 0, 0);
		return textView;
	}

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		TextView textView = getGenericView();
		textView.setText(getChild(groupPosition, childPosition)+"");
		return textView;
	}

	public Cursor getGroup(int groupPosition) {
		return null;
	}

	public int getGroupCount() {
		return groups.length;
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		TextView textView = getGenericView();
		textView.setText(getGroup(groupPosition) +"");
		return textView;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean hasStableIds() {
		return true;
	}

	/**
	 * @param context
	 *            the context to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * @return the context
	 */
	public Context getContext() {
		return context;
	}

	@Override
	protected void bindChildView(View view, Context context, Cursor cursor,
			boolean isLastChild) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void bindGroupView(View view, Context context, Cursor cursor,
			boolean isExpanded) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Cursor getChildrenCursor(Cursor groupCursor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected View newChildView(Context context, Cursor cursor,
			boolean isLastChild, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected View newGroupView(Context context, Cursor cursor,
			boolean isExpanded, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
>>>>>>> 2d0c0719d233b5110f544d045640a55a91554b08
