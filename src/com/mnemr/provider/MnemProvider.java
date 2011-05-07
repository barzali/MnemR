package com.mnemr.provider;

import com.mnemr.provider.MnemProvider.DbHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class MnemProvider extends ContentProvider {

	
	private static final UriMatcher uriMatcher;
	private static final int MNEMONS = 1;
	private static final int RELATED = 2;
	private DbHelper db;
	
	@Override
	public boolean onCreate() {
		db = new DbHelper(getContext());
		return false;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long id = db.getWritableDatabase().insert(Mnem.TABLE_NAME, null, values);
		return ContentUris.withAppendedId(Mnem.CONTENT_URI, id);
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		
		Cursor cursor = null;
		switch (uriMatcher.match(uri)) {
		case MNEMONS:
			cursor = db.getReadableDatabase().query(Mnem.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
			break;
		case RELATED:
			cursor = db.getReadableDatabase().query(Mnem.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
			break;

		default:
			break;
		}
		return cursor;
	}
	
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		return db.getWritableDatabase().update(Mnem.TABLE_NAME, values, selection, selectionArgs);
	}

	@Override
	public int delete(Uri arg0, String selection, String[] selectionArgs) {
		return db.getWritableDatabase().delete(Mnem.TABLE_NAME, selection, selectionArgs);
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	class DbHelper extends SQLiteOpenHelper {

		private static final int VERSION = 0;

		public DbHelper(Context context) {
			super(context, "mnemr.db", null, VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE "+Mnem.TABLE_NAME+" (" +
							Mnem._ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
							Mnem.SOUND+" TEXT," +
							Mnem.IMAGE+" TEXT," +
							Mnem.TEXT+" TEXT" +
						");");
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
		}
		
	}
	
	
	
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.mnemr", Mnem.TABLE_NAME, MNEMONS);
		uriMatcher.addURI("com.mnemr", Mnem.TABLE_NAME+"/#/related", RELATED);
	}
	
	

}
