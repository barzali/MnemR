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

package com.mnemr.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mnemr.provider.Mnem;
import com.mnemr.utils.MnemrUtil;

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

	private void scaleToFit(TextView view) {
		float factor = (getWindowManager().getDefaultDisplay().getWidth() - 42)
				/ view.getPaint().measureText(view.getText().toString());
		view.setTextSize(view.getTextSize() * factor);
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
		menu.add(1, 124, 0, "Edit").setIcon(android.R.drawable.ic_menu_edit);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case 123:

			getContentResolver().delete(Mnem.CONTENT_URI,
					Mnem._ID + "=" + mAdapter.getCursor().getString(0), null);

			Toast.makeText(this, "Deleted.", Toast.LENGTH_LONG).show();

			break;

		case 124:

			EditText textView = new EditText(FlashcardsActivity.this);
			String ok_text = "Ok";
			ContentValues values = new ContentValues();
			  Log.d("id",""+ mAdapter.getCursor().getInt(0));
	           getContentResolver().delete(Mnem.CONTENT_URI,Mnem._ID+"="+mAdapter.getCursor().getInt(0) , null);
	           mAdapter.getCursor().requery();
	           mCardsView = new CardsView(this);
	           mCardsView.setAdapter(mAdapter);
	           setContentView(mCardsView);
	            Toast.makeText(this, "cleared", Toast.LENGTH_LONG).show();
	            break;
			/*final int text = getContentResolver().update(Mnem.CONTENT_URI,  ,
					Mnem._ID + "=" + mAdapter.getCursor().getString(0), null);
			AlertDialog infoDialog = new AlertDialog.Builder(
					FlashcardsActivity.this)
					.setIcon(android.R.drawable.ic_dialog_info)
					.setView(textView).setTitle("Info").setMessage(text)
					.setPositiveButton(ok_text, new OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							MnemrUtil.showToast(text+" is updated", FlashcardsActivity.this);
						}
					}).create();

			infoDialog.show();
 
			Toast.makeText(this, "Edit", Toast.LENGTH_LONG).show();
*/
			//break;
		}
		return super.onOptionsItemSelected(item);
	}

}
