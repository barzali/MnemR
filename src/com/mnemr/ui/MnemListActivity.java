/**
 * 
 */
package com.mnemr.ui;

import com.mnemr.controller.MnemoListAdapter;
import com.mnemr.provider.Mnem;

import android.R;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CursorTreeAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

/**
 * @author Hamid
 *
 */
public class MnemListActivity extends ExpandableListActivity {

	 private ExpandableListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		adapter=new CursorTreeAdapter(getContentResolver().query(Mnem.CONTENT_URI, Mnem.PROJECTION, null, null, null), this) {

			@Override
			protected View newGroupView(Context context, Cursor cursor, boolean isExpanded, ViewGroup parent) {
				// Layout parameters for the ExpandableListView
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, 64);
				
				TextView textView = new TextView( context);
				textView.setLayoutParams(lp);
				// Center the text vertically
				textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
				// Set the text starting position
				textView.setPadding(36, 0, 0, 0);
				return textView;
				
			}

			@Override
			protected void bindGroupView(View view, Context context, Cursor cursor, boolean isExpanded) {
				Log.d(getClass().getSimpleName(), cursor.getString(1));
				((TextView) view).setText(cursor.getString(1));
			//	scaleToFit((TextView) view);
			}

			@Override
			protected Cursor getChildrenCursor(Cursor groupCursor) {
				return getContentResolver()
						.query(Mnem.CONTENT_URI, Mnem.PROJECTION, null, null, null);
				//TODO machen!
			}

			@Override
			protected View newChildView(Context context, Cursor cursor,
					boolean isLastChild, ViewGroup parent) {
				TextView v = new TextView(context);
				v.setBackgroundColor(Color.BLACK);
				v.setTextColor(Color.LTGRAY);
				v.setGravity(Gravity.CENTER_HORIZONTAL
						| Gravity.CENTER_VERTICAL);
				return v;
			}

			@Override
			protected void bindChildView(View view, Context context,
					Cursor cursor, boolean isLastChild) {
				((TextView) view).setText(cursor.getString(1));
				//scaleToFit((TextView) view);
			}
		};
		setListAdapter(adapter);
		
		
	}
}
