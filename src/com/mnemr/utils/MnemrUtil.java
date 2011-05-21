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

package com.mnemr.utils;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Environment;

import com.mnemr.R;

/**
 * 
 */

/**
 * @author barzali.
 *
 */
public class MnemrUtil {

	public static final String Log ="MnemrUtil";
	
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
	
	
	/**
	 * - make sure we have a mounted SDCard 
	 * - this method creates the App Folder to
	 *  
	 *  

	 * @param context
	 *            {@link Context}
	 */
	public static void CreateAppFolder(Context context) {
		try {

			// make sure we have a mounted SDCard
			String externalStorageState = Environment
					.getExternalStorageState();
			if (!Environment.MEDIA_MOUNTED.equals(externalStorageState)) {
				// they don't have an SDCard, give them an error message and
				// quit
			  
				android.util.Log.e(Log,
						"Don't have an SDCard, give them an error message and quit ! ");
				
				if (context instanceof Activity) {
					((Activity) context).finish();
				}
				

			} else {
				// there's an SDCard available, continue
				// File root = Environment.getExternalStorageDirectory();

				String directoryname = context.getString(R.string.app_name);
				 String dirName = "/sdcard/" + directoryname + "";
				File root =    new File(dirName);
				boolean exists = root.exists();
				if (!exists) {
					root.mkdirs();
				}

			}

		} catch (Exception e) {
			android.util.Log.e(Log, "by creating folder " + e+"");
			{

				 

				android.util.Log.e(Log, e.toString());

			}
		}

	}
	
	
}
