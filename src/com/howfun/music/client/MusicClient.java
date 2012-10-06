package com.howfun.music.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MusicClient extends Activity implements OnClickListener{
	
	private static final String TAG = "MusicClient";
			
	MusicServiceConnection mServiceConnection;

	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
	    mServiceConnection = new MusicServiceConnection(this, new Handler());
	    mServiceConnection.connect();
	    
	    Button playPauseBtn = (Button)findViewById(R.id.play_pause);
	    playPauseBtn.setOnClickListener(this);
	    
	    Button stopBtn = (Button)findViewById(R.id.stop);
	    stopBtn.setOnClickListener(this);
	}

	

    
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.play_pause:
			Utils.log(TAG, "Play  pause");
			if (mServiceConnection.isServiceConnected()) {
				MusicServiceManager mMusicServiceManager = mServiceConnection.getManager();
				if (mMusicServiceManager != null) {
					mMusicServiceManager.playPause();
				}
			}
			break;
		case R.id.stop:
			if (mServiceConnection.isServiceConnected()) {
				MusicServiceManager manager = mServiceConnection.getManager();
				if (manager != null) {
					manager.stop();
				}
			}
		default:
			break;
		}
		
	}
	
	public void onDestroy() {
		super.onDestroy();
		if (mServiceConnection != null) {
			mServiceConnection.disconnect();
			mServiceConnection = null;
		}
	}

}
