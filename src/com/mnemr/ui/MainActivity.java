/**
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
import android.app.SearchManager;
import android.content.ContentUris;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mnemr.R;
import com.mnemr.provider.Mnem;


public class MainActivity extends Activity implements OnTouchListener {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ImageButton addbtn = (ImageButton) findViewById(R.id.addmemo_id);

		ImageButton mnmrListbtn = (ImageButton) findViewById(R.id.mnemrlist_id);

		ImageButton cardsbtn = (ImageButton) findViewById(R.id.mnemrcards_id);

		addbtn.setOnTouchListener(this);
		mnmrListbtn.setOnTouchListener(this);
		cardsbtn.setOnTouchListener(this);

	}

	public boolean onTouch(View v, MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_UP) {

			if (v instanceof ImageButton) {
				ImageButton imButton = (ImageButton) v;

				if (imButton.getId() == R.id.addmemo_id) {
					Toast.makeText(MainActivity.this, "ad mnemo !", Toast.LENGTH_SHORT).show();
					
					Intent intent = new Intent(Intent.ACTION_INSERT, Mnem.CONTENT_URI);
					startActivity(intent);
				}
				if (imButton.getId() == R.id.mnemrlist_id) {
//					Toast.makeText(MainActivity.this, "list of mnemos ;)",
//							Toast.LENGTH_SHORT).show();
					
					Intent listIntent = new Intent(MainActivity.this,MnemListActivity.class);
					startActivity(listIntent);
					
					
				}
				if (imButton.getId() == R.id.mnemrcards_id) {
					Intent listIntent = new Intent(MainActivity.this,FlashcardsActivity.class);
					startActivity(listIntent);
				}
				if (imButton.getId() == R.id.search) {
					 
					onSearchRequested();
				}
			}

		}
		return false;
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	    
		// search
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	      String query = intent.getStringExtra(SearchManager.QUERY);
	      if (query == null) query = intent.getDataString(); // touch
	      Toast.makeText(this, "Search: "+query, Toast.LENGTH_LONG).show();
	    }

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
}
