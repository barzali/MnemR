package com.mnemr.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class Mnem implements BaseColumns {

	public static final String TABLE_NAME = "mnemons";
	
	public static final String SOUND = "sound";
	public static final String IMAGE = "image";
	public static final String TEXT = "text";

	public static final Uri CONTENT_URI = Uri.parse("content://com.mnemr/mnemons");

	public static final String[] PROJECTION = new String[] {_ID, TEXT, IMAGE, SOUND};

}
