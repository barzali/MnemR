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
