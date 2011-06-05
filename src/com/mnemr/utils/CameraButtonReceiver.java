package com.mnemr.utils;

import com.mnemr.ui.MnemEditorActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;

public class CameraButtonReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		abortBroadcast();
		context.startActivity(new Intent(context, MnemEditorActivity.class)
		.setAction("NEHM_THAT").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS));
	}

}
