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
