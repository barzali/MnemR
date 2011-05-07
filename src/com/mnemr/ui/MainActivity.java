package com.mnemr.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        ImageButton addbtn = (ImageButton) findViewById(R.id.addmemo_id);
        ImageButton mnmrListbtn = (ImageButton) findViewById(R.id.mnemrlist_id);
        ImageButton cardsbtn = (ImageButton) findViewById(R.id.mnemrcards_id);
        
        
        
        
        
        
        
        
    }
}