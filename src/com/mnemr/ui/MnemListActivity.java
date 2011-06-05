/**
 *  __  __                      ____  
*  |  \/  |_ __   ___ _ __ ___ |  _ \ 
*  | |\/| | '_ \ / _ \ '_ ` _ \| |_) |
*  | |  | | | | |  __/ | | | | |  _ < 
*  |_|  |_|_| |_|\___|_| |_| |_|_| \_\
                                   
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
import android.app.ExpandableListActivity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.CursorTreeAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mnemr.R;
import com.mnemr.provider.Mnem;
import com.mnemr.utils.MnemrUtil;

/**
 * @author barzali.
 * 
 */
public class MnemListActivity extends ExpandableListActivity implements OnTouchListener {

	private ExpandableListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actionbar_main_layout);

		MnemrUtil.CreateAppFolder(MnemListActivity.this);

		// search
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			if (query == null)
				query = intent.getDataString(); // touch
			Toast.makeText(this, "Search: " + query, Toast.LENGTH_LONG).show();
		}

		adapter = new CursorTreeAdapter(getContentResolver().query(
				Mnem.CONTENT_URI, Mnem.PROJECTION, null, null, null), this) {

			@Override
					public boolean isChildSelectable(int groupPosition,
							int childPosition) {
						// TODO Auto-generated method stub
						return super.isChildSelectable(groupPosition, childPosition);
					}
			
			@Override
			protected View newGroupView(Context context, Cursor cursor,
					boolean isExpanded, ViewGroup parent) {
				View view = createListViewItem(context);
				return view;

			}

			@Override
			protected void bindGroupView(View view, Context context,
					Cursor cursor, boolean isExpanded) {
				 
				// ((TextView) view).setText(cursor.getString(1));

				if (view.getId() == R.id.listItemContainer) {
					
					TextView textView = (TextView) view
							.findViewById(R.id.mnemr_text_id);
					textView.setText(cursor.getString(1));
				//	textView.setOnTouchListener(MnemListActivity.this);
					
					/**
					 *  TextView mnemrFoto = (TextView) view.findViewById(R.id.mnemrphoto_id);
					 
					 mnemrFoto.setOnTouchListener(MnemListActivity.this);
					 
					 TextView mnemrSound = (TextView) view.findViewById(R.id.sound_imagebtn); 
					mnemrSound.setOnTouchListener(MnemListActivity.this);
					 
					String text = cursor.getString(1);
					textView.setText(text);
					 */
				}
			}

			@Override
			protected Cursor getChildrenCursor(Cursor groupCursor) {
				return getContentResolver().query(Uri.withAppendedPath(Mnem.CONTENT_URI, "/"+getCursor().getInt(0)+"/related"),
						Mnem.PROJECTION, null, null, null);

			}

			@Override
			protected View newChildView(Context context, Cursor cursor,
					boolean isLastChild, ViewGroup parent) {
				View view = createListViewItem(context);
				return view;
			}

			private View createListViewItem(Context context) {
				
				
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View textView = inflater.inflate(R.layout.listview_item, null);

				return textView;
			}

			@Override
			protected void bindChildView(View view, Context context,
					Cursor cursor, boolean isLastChild) {

				if (view.getId() == R.id.listItemContainer) { 
					
					
					
					if (view.getId() == R.id.listItemContainer) {
						
						TextView textView = (TextView) view
								.findViewById(R.id.mnemr_text_id);
						textView.setText(cursor.getString(1));
						//.setOnTouchListener(MnemListActivity.this);
						
						/**
						 *  TextView mnemrFoto = (TextView) view.findViewById(R.id.mnemrphoto_id);
						 
						 mnemrFoto.setOnTouchListener(MnemListActivity.this);
						 
						 TextView mnemrSound = (TextView) view.findViewById(R.id.sound_imagebtn); 
						mnemrSound.setOnTouchListener(MnemListActivity.this);
						 
						String text = cursor.getString(1);
						textView.setText(text);
						 */
					}
				}
			}
		};
		setListAdapter(adapter);
		
		ImageButton addbtn = (ImageButton) findViewById(R.id.addmemo_id);

		ImageButton flashbtn = (ImageButton) findViewById(R.id.flash);
		ImageButton searchbtn = (ImageButton) findViewById(R.id.search);

		addbtn.setOnTouchListener(this);
		flashbtn.setOnTouchListener(this);
		searchbtn.setOnTouchListener(this);

	}
	
	
	@Override
	public void onGroupExpand(int groupPosition) {
		startActivity(new Intent(Intent.ACTION_VIEW, ContentUris.withAppendedId(Mnem.CONTENT_URI, groupPosition)));
		super.onGroupExpand(groupPosition);
	}
	
	@Override
	public void onGroupCollapse(int groupPosition) {
		super.onGroupCollapse(groupPosition);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 123, 0, "Delete All").setIcon(
				android.R.drawable.ic_menu_delete);
		menu.add(1, 124, 0, "Info").setIcon(
				android.R.drawable.ic_menu_info_details);
		menu.add(1, 124, 0, "Settings").setIcon(
				android.R.drawable.ic_menu_preferences);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 123:
			getContentResolver().delete(Mnem.CONTENT_URI, null, null);
			Toast.makeText(this, "Deleted.", Toast.LENGTH_LONG).show();

			// refresh the listview!
			if (getExpandableListView() != null) {
				getExpandableListView().invalidate();
			}
			break;
		default:

			MnemrUtil.showInfoDialog(getString(R.string.info_text),
					MnemListActivity.this);

			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);

		// search
		// Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			if (query == null)
				query = intent.getDataString(); // touch
			Toast.makeText(this, "Search: " + query, Toast.LENGTH_LONG).show();
		}

	}

	public boolean onTouch(View v, MotionEvent event) {
		
		if (MotionEvent.ACTION_UP == event.getAction()) {
			
			switch (v.getId()) {

			case R.id.addmemo_id:
				startActivity(new Intent(Intent.ACTION_INSERT, Mnem.CONTENT_URI));
				break;
			case R.id.flash:
				startActivity(new Intent(this, FlashcardsActivity.class));
				break;
			case R.id.search:
				onSearchRequested();
				break;
			}
		}
		return true;
	}


}
