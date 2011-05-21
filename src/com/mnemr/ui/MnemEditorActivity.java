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

import com.mnemr.R;
import com.mnemr.provider.Mnem;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.Toast;

public class MnemEditorActivity extends Activity {

	protected static final String TAG = "Editor";
	private EditText text;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.editor);
	    text = (EditText) findViewById(R.id.text);
	    
	    text.setOnKeyListener(new OnKeyListener() {
			
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					// scale text to fit screen
					float textSize = text.getTextSize() * 
						(getWindowManager().getDefaultDisplay().getWidth() - 42 ) / 
						text.getPaint().measureText(text.getText().toString()+"iii");
					Log.d(TAG, "textSize: "+textSize);
					text.setTextSize(Math.min(105, textSize));
				}
		        return false;
			}
			
		});
	    
	    findViewById(R.id.image).setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				ContentValues values = new ContentValues();
				values.put(Mnem.TEXT, text.getText().toString());
				getContentResolver().insert(Mnem.CONTENT_URI, values);
				((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(30);
				Toast.makeText(MnemEditorActivity.this, "saved", Toast.LENGTH_SHORT).show();
				text.setText("");
			}
		});
	    
	}


}
