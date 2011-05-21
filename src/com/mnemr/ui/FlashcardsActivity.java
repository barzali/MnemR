/*

 * Copyright (c) 2011: mnemr.com contributors. All rights reserved.
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

package com.mnemr.ui;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorTreeAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mnemr.provider.Mnem;

public class FlashcardsActivity extends Activity {

	protected static final String TAG = "FlasCards";
	private CardsView mCardsView;
	private CursorTreeAdapter mAdapter;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mCardsView.onTouchEvent(event);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mCardsView = new CardsView(this);
		setContentView(mCardsView);

		setCuAdapter(new CursorTreeAdapter(getContentResolver().query(
				Mnem.CONTENT_URI, Mnem.PROJECTION, null, null, null), this) {

			@Override
			protected View newGroupView(Context context, Cursor cursor,
					boolean isExpanded, ViewGroup parent) {
				TextView v = new TextView(context);
				v.setBackgroundColor(Color.BLACK);
				v.setTextColor(Color.GRAY);
				v.setGravity(Gravity.CENTER_HORIZONTAL
						| Gravity.CENTER_VERTICAL);
				v.setText("hui");
				return v;
			}

			@Override
			protected void bindGroupView(View view, Context context,
					Cursor cursor, boolean isExpanded) {
				Log.d(TAG, cursor.getString(1));
				((TextView) view).setText(cursor.getString(1));
				scaleToFit((TextView) view);
			}

			@Override
			protected Cursor getChildrenCursor(Cursor groupCursor) {

				return getContentResolver().query(
						Uri.withAppendedPath(Mnem.CONTENT_URI, "/"
								+ groupCursor.getInt(0) + "/related"),
						Mnem.PROJECTION, null, null, null);

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
				scaleToFit((TextView) view);
			}
		});
		mCardsView.setAdapter(getCuAdapter());
		// registerForContextMenu(mCardsView);
		mCardsView.setOnCreateContextMenuListener(this);
	}

	/**
	 * @param mAdapter
	 *            the mAdapter to set
	 */
	public void setCuAdapter(CursorTreeAdapter mAdapter) {
		this.mAdapter = mAdapter;
	}

	/**
	 * @return the mAdapter
	 */
	public CursorTreeAdapter getCuAdapter() {
		return mAdapter;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 123, 0, "Delete")
				.setIcon(android.R.drawable.ic_menu_delete);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 123:
			Log.d("id", "" + mAdapter.getCursor().getInt(0));
			getContentResolver().delete(Mnem.CONTENT_URI,
					Mnem._ID + "=" + mAdapter.getCursor().getInt(0), null);
			mAdapter.getCursor().requery();
			 
			mCardsView = new CardsView(this);
			
			//mCardsView.setAdapter(mAdapter);
			mCardsView.invalidate();
			mCardsView.postInvalidate();
			setContentView(mCardsView);
			Toast.makeText(this, "cleared", Toast.LENGTH_LONG).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void scaleToFit(TextView view) {
		float factor = (getWindowManager().getDefaultDisplay().getWidth() - 42)
				/ view.getPaint().measureText(view.getText().toString());
		view.setTextSize(view.getTextSize() * factor);
	}

}
