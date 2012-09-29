package com.howfun.music.client;

import android.util.Log;

public class Utils {

	private static final boolean DEBUG = true;
	
	public static final int BUILD_QUAN_ZHI = 0;
	public static final int BUILD_POLO = 1;
	public static final int BUILD_POLO_ENG = 2;
	public static final int BUILD_PAD_TYPE = BUILD_POLO_ENG;

	public static void log(String tag, String info) {
		if (DEBUG) {
			Log.d("MusicClient", "=====>" + tag + "---->" + info);
		}
	}
}
