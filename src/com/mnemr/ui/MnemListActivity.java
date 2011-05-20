/**
 * 
 */
package com.mnemr.ui;

 
 
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CursorTreeAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mnemr.R;
import com.mnemr.provider.Mnem;
 

/**
 * @author Hamid
 *
 */
public class MnemListActivity extends Activity implements OnTouchListener{

	private ExpandableListAdapter adapter;
	private ExpandableListView expandableListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
     setContentView(R.layout.actionbar_main_layout);
     
     setExpandableListView((ExpandableListView) findViewById(R.id.listView));
	// search
	Intent intent = getIntent();
	if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	String query = intent.getStringExtra(SearchManager.QUERY);
	if (query == null) query = intent.getDataString(); // touch
	Toast.makeText(this, "Search: "+query, Toast.LENGTH_LONG).show();
	}

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
	// scaleToFit((TextView) view);
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
	 getExpandableListView().setAdapter(adapter);
	 
	 ImageButton addbtn = (ImageButton) findViewById(R.id.addmemo_id);

		ImageButton mnmrListbtn = (ImageButton) findViewById(R.id.mnemrlist_id);

		ImageButton cardsbtn = (ImageButton) findViewById(R.id.mnemrcards_id);

		addbtn.setOnTouchListener(this);
		mnmrListbtn.setOnTouchListener(this);
		cardsbtn.setOnTouchListener(this);

	}

	/**
	 * @param expandableListView the expandableListView to set
	 */
	public void setExpandableListView(ExpandableListView expandableListView) {
		this.expandableListView = expandableListView;
	}

	/**
	 * @return the expandableListView
	 */
	public ExpandableListView getExpandableListView() {
		return expandableListView;
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 123, 0, "Delete All")
        	.setIcon(android.R.drawable.ic_menu_delete);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case 123:
           	getContentResolver().delete(Mnem.CONTENT_URI, null, null);
           	Toast.makeText(this, "Deleted.", Toast.LENGTH_LONG).show();
            break;
        }
        return super.onOptionsItemSelected(item);
    }
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		
		onSearchRequested();
	}

	public boolean onTouch(View v, MotionEvent event) {
		if (v instanceof ImageButton) {
			ImageButton imButton = (ImageButton) v;

			if (imButton.getId() == R.id.addmemo_id) {
				Toast.makeText(MnemListActivity.this, "ad mnemo !", Toast.LENGTH_SHORT).show();
				
				Intent intent = new Intent(Intent.ACTION_INSERT, Mnem.CONTENT_URI);
				startActivity(intent);
			}
			if (imButton.getId() == R.id.mnemrlist_id) {
//				Toast.makeText(MainActivity.this, "list of mnemos ;)",
//						Toast.LENGTH_SHORT).show();
				
				Intent listIntent = new Intent(MnemListActivity.this,MnemListActivity.class);
				startActivity(listIntent);
				
				
			}
			if (imButton.getId() == R.id.mnemrcards_id) {
				Intent listIntent = new Intent(MnemListActivity.this,FlashcardsActivity.class);
				startActivity(listIntent);
			}
		}

	 
		return false;
	}
	
	}