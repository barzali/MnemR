package com.mnemr.utils;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

/**
 * 
 */

/**
 * @author barzali
 *
 */
public class MnemrUtil {

	
	
	public static  void showInfoDialog(String text,  
			final Context context) {
		
		String ok_text = "Ok";
		
		  AlertDialog infoDialog = new AlertDialog.Builder(context)
		.setIcon(android.R.drawable.ic_dialog_info).setItems(null, new OnClickListener() {
			
			 
			public void onClick(DialogInterface dialog, int which) {
				 
				
			}
		})
		.setTitle("Info")
		.setMessage(text)
		 .setPositiveButton(ok_text, new OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				 
				
			}
		})
		.create();
		
		  infoDialog.show();
		  
	}
	
	
	
}
