package com.theidiomsbook.libraries;

import android.content.Context;
import android.widget.Toast;

public class UIHelper {

	public UIHelper() {
		super();
	}

	public static void showMessage (Context context, String sMessage) {
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, sMessage, duration);
		toast.show();
		return;
	}
}
