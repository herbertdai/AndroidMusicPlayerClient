package com.howfun.music.client;

import com.howfun.music.control.MusicData;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

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
	    
	    mServiceConnection.registerOnConnectRunnable(new Runnable() {

	    	@Override
	    	public void run() {
	    		Utils.log(TAG, "connected");
	    		showTitle(); 
	    	}
	    });
	}

	private void showTitle() {
		String title = "";

		if (mServiceConnection.isServiceConnected()) {
			MusicServiceManager mMusicServiceManager = mServiceConnection.getManager();
			if (mMusicServiceManager != null) {
				MusicData data = mMusicServiceManager.getMusicData();
				if (data != null)
					title = data.getTitle();

			}
		}

		Utils.log(TAG, "Get music title = " + title);

		TextView titleText = (TextView)findViewById(R.id.music_title);
		if (titleText != null) 
			titleText.setText("Now playing: " + title);
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
			showTitle();
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

	 enum State {
        Stopped,    // media player is stopped and not prepared to play
        Preparing,  // media player is preparing...
        Playing,    // playback active (media player ready!). (but the media player may actually be
                    // paused in this state if we don't have audio focus. But we stay in this state
                    // so that we know we have to resume playback once we get focus back)
        Paused      // playback paused (media player ready!)
    };
}
