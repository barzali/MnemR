/*
 *  __  __                      ____  
|  \/  |_ __   ___ _ __ ___ |  _ \ 
| |\/| | '_ \ / _ \ '_ ` _ \| |_) |
| |  | | | | |  __/ | | | | |  _ < 
|_|  |_|_| |_|\___|_| |_| |_|_| \_\
                                   


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
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorTreeAdapter;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.mnemr.provider.Mnem;
import com.mnemr.utils.MnemrUtil;

public class FlashcardsActivity extends Activity {

	protected static final String TAG = "FlashCards";
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
				FrameLayout l = new FrameLayout(context);
				TextView v = new TextView(context);
				v.setBackgroundColor(Color.BLACK);
				v.setTextColor(Color.GRAY);
				v.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
				v.setText("hui");
				l.addView(v, new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
				v = new TextView(context);
				v.setBackgroundColor(Color.BLACK);
				v.setTextColor(Color.GREEN);
				v.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
				v.setTextSize(23);
				LayoutParams p = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				p.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
				p.bottomMargin = 11;
				l.addView(v, p);
				return l;
			}

			@Override
			protected void bindGroupView(View view, Context context, Cursor cursor, boolean isExpanded) {
				TextView text = (TextView) ((FrameLayout)view).getChildAt(0);
				text.setText(cursor.getString(1));
				scaleToFit((TextView) text);
				TextView nmb = (TextView) ((FrameLayout)view).getChildAt(1);
				nmb.setText("1/"+(getChildrenCount(cursor.getPosition())+1));
			}

			@Override
			protected Cursor getChildrenCursor(Cursor groupCursor) {
				return getContentResolver().query(Uri.withAppendedPath(Mnem.CONTENT_URI, "/" + 
						groupCursor.getInt(0) + "/related"), Mnem.PROJECTION, null, null, null);

			}

			@Override
			protected View newChildView(Context context, Cursor cursor, boolean isLastChild, ViewGroup parent) {
				return newGroupView(context, cursor, false, parent);
			}

			@Override
			protected void bindChildView(View view, Context context, Cursor cursor, boolean isLastChild) {
				bindGroupView(view, context, cursor, false);
				TextView nmb = (TextView) ((FrameLayout)view).getChildAt(1);
				nmb.setText((cursor.getPosition()+2)+"/"+(cursor.getCount()+1));
			}
		});
		
		if (getIntent().getData() != null) {
			mCardsView.setId(Integer.parseInt(getIntent().getData().getLastPathSegment()));
		}

		mCardsView.setAdapter(getCuAdapter());
		// registerForContextMenu(mCardsView);
		mCardsView.setOnCreateContextMenuListener(this);
	}

	@Override
	protected void onRestart() {
		mAdapter.getCursor().requery();
		mCardsView = new CardsView(this);
		setContentView(mCardsView);
		mCardsView.setAdapter(mAdapter);
		super.onRestart();
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		Log.d("Key", ""+keyCode);
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_LEFT:
			mCardsView.flipCardLeft();
			return true;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			mCardsView.flipCardRight();
			return true;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			mCardsView.prevCard();
			return true;
		case KeyEvent.KEYCODE_DPAD_UP:
			mCardsView.nextCard();
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			mCardsView.prevCard();
			return true;
		case KeyEvent.KEYCODE_VOLUME_UP:
			mCardsView.nextCard();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * @param mAdapter
	 *            the mAdapter to set
	 */
	public void setCuAdapter(CursorTreeAdapter mAdapter) {
		if (mAdapter.getGroupCount() == 0) {
			MnemrUtil.showToast("there are no mnemrs !  ", FlashcardsActivity.this);
			finish();
		}

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
		menu.add(0, 123, 0, "Delete").setIcon(android.R.drawable.ic_menu_delete);
		menu.add(0, 456, 0, "Edit").setIcon(android.R.drawable.ic_menu_edit);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		Uri uri = ContentUris.withAppendedId(Mnem.CONTENT_URI, mAdapter.getCursor().getInt(0));
		
		switch (item.getItemId()) {
		case 123:

			Cursor cursor = mAdapter.getCursor();

			if (cursor != null) {

				int var = cursor.getInt(0);
				Log.d("id", "" + var);
				getContentResolver().delete(Mnem.CONTENT_URI,
						Mnem._ID + "=" + var, null);

				// mCardsView = new CardsView(this);

				// mCardsView.setAdapter(mAdapter);
				// mCardsView.invalidate();
				// mCardsView.postInvalidate();
				// mCardsView.animateCurl(false);
				;
				mCardsView.refresh();
				// setContentView(mCardsView);
				Toast.makeText(this, "cleared", Toast.LENGTH_LONG).show();
			}
			break;
			
		case 456:
			startActivity(new Intent(Intent.ACTION_EDIT, uri));
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void scaleToFit(TextView view) {
		if (view.getText().toString().length() < 5) {
			view.setText("  "+view.getText()+"  ");
		}
		int textSize = 500;
		String[] lines = view.getText().toString().split("\\n");
		for (int i = 0; i < lines.length; i++) {
			textSize = (int) Math.min(textSize, (view.getTextSize() *
					(getWindowManager().getDefaultDisplay().getWidth()-42) / 
					view.getPaint().measureText(lines[i])));
		}
		view.setTextSize(textSize);
	}

}
