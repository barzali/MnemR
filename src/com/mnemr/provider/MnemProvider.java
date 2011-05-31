/**
  *  __  __                      ___
  * |  \/  |_ __   ___ _ __ ___ |  _ \ 
  * | |\/| | '_ \ / _ \ '_ ` _ \| |_) |
  * | |  | | | | |  __/ | | | | |  _ < 
  * |_|  |_|_| |_|\___|_| |_| |_|_| \_\
  *
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
package com.mnemr.provider;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class MnemProvider extends ContentProvider {

	
	private static final UriMatcher uriMatcher;
	private static final int MNEMONS = 1;
	private static final int RELATED = 2;
	private static final int SEARCH = 3;
	private static final int MNEMON = 4;
	private DbHelper db;
	
	
	class DbHelper extends SQLiteOpenHelper {

		private static final int VERSION = 1;

		public DbHelper(Context context) {
			super(context, "mnemr.db", null, VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE "+Mnem.TABLE_NAME+" (" +
							Mnem._ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
							Mnem.SOUND+" TEXT," +
							Mnem.IMAGE+" TEXT," +
							Mnem.TEXT+" TEXT," +
							Mnem.RELATED_ID+" INTEGER" +
						");");
			
			db.execSQL("INSERT INTO "+Mnem.TABLE_NAME+" VALUES (1, 'sound', 'image', 'MnemR', NULL);");
			db.execSQL("INSERT INTO "+Mnem.TABLE_NAME+" VALUES (2, 'sound', 'image', 'Memer', 1);");
			db.execSQL("INSERT INTO "+Mnem.TABLE_NAME+" VALUES (3, 'sound', 'image', 'content tool', 1);");
			db.execSQL("INSERT INTO "+Mnem.TABLE_NAME+" VALUES (4, 'sound', 'image', 'griech: mnem', NULL);");
			db.execSQL("INSERT INTO "+Mnem.TABLE_NAME+" VALUES (5, 'sound', 'image', 'lat: mem', 4);");
			db.execSQL("INSERT INTO "+Mnem.TABLE_NAME+" VALUES (6, 'sound', 'image', 'Hallo', NULL);");
			db.execSQL("INSERT INTO "+Mnem.TABLE_NAME+" VALUES (7, 'sound', 'image', 'Nihao', 6);");
			db.execSQL("INSERT INTO "+Mnem.TABLE_NAME+" VALUES (8, 'sound', 'image', 'A9', NULL);");
			db.execSQL("INSERT INTO "+Mnem.TABLE_NAME+" VALUES (9, 'sound', 'image', 'Wien->München', 8);");
			db.execSQL("INSERT INTO "+Mnem.TABLE_NAME+" VALUES (10, 'sound', 'image', 'erbaut 1953', 8);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
		}
		
	}
	
	
	@Override
	public boolean onCreate() {
		db = new DbHelper(getContext());
		return false;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if (uriMatcher.match(uri) == RELATED)
			values.put(Mnem.RELATED_ID, uri.getPathSegments().get(1));
		long id = db.getWritableDatabase().insert(Mnem.TABLE_NAME, null, values);
		getContext().getContentResolver().notifyChange(Mnem.CONTENT_URI, null);
		return ContentUris.withAppendedId(Mnem.CONTENT_URI, id);
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		
		Cursor cursor = null;
		switch (uriMatcher.match(uri)) {
		case MNEMONS:
			cursor = db.getReadableDatabase().query(Mnem.TABLE_NAME, projection, Mnem.RELATED_ID+" ISNULL", selectionArgs, null, null, sortOrder);
			break;
		case MNEMON:
			selection = Mnem._ID+"="+uri.getLastPathSegment();
			cursor = db.getReadableDatabase().query(Mnem.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
			break;
		case RELATED:
			cursor = db.getReadableDatabase().query(Mnem.TABLE_NAME, projection, Mnem.RELATED_ID+"="+uri.getPathSegments().get(1), selectionArgs, null, null, sortOrder);
			break;
		case SEARCH:
			if (uri.getPathSegments().size() > 1)
				selection = "text LIKE '"+uri.getLastPathSegment()+"%'";
			cursor = db.getReadableDatabase().query(Mnem.TABLE_NAME, new String[] {
					Mnem._ID, Mnem._ID +
					" AS "+SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID,
					Mnem.TEXT+" AS "+SearchManager.SUGGEST_COLUMN_TEXT_1
					}, selection, selectionArgs, null, null, sortOrder);
			break;
		default:
			break;
		}
		cursor.setNotificationUri(getContext().getContentResolver(), Mnem.CONTENT_URI);
		return cursor;
	}
	
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		if (uriMatcher.match(uri) == RELATED)
			values.put(Mnem.RELATED_ID, uri.getPathSegments().get(1));
		selection = Mnem._ID+"="+uri.getLastPathSegment();
		return db.getWritableDatabase().update(Mnem.TABLE_NAME, values, selection, selectionArgs);
	}

	@Override
	public int delete(Uri arg0, String selection, String[] selectionArgs) {
		return db.getWritableDatabase().delete(Mnem.TABLE_NAME, selection, selectionArgs);
	}

	@Override
	public String getType(Uri uri) {
		return "vnd.android.cursor.dir/vnd.mnemr.mnemon";
	}
	
	

	
	
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.mnemr", Mnem.TABLE_NAME, MNEMONS);
		uriMatcher.addURI("com.mnemr", Mnem.TABLE_NAME+"/#", MNEMON);
		uriMatcher.addURI("com.mnemr", Mnem.TABLE_NAME+"/#/related", RELATED);
		uriMatcher.addURI("com.mnemr", SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH);
		uriMatcher.addURI("com.mnemr", SearchManager.SUGGEST_URI_PATH_QUERY+"/*", SEARCH);
	}
	
	

}
