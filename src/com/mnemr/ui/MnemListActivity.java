/**
 * 
 */
package com.mnemr.ui;

import com.mnemr.controller.MnemoListAdapter;

import android.app.ExpandableListActivity;
import android.os.Bundle;

/**
 * @author Hamid
 *
 */
public class MnemListActivity extends ExpandableListActivity {

	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new MnemoListAdapter(null, MnemListActivity.this));
		
		
	}
}
