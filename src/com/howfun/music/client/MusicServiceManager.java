package com.howfun.music.client;
import java.util.ArrayList;

import com.howfun.music.control.IMusicService;
import com.howfun.music.control.MusicData;


import android.graphics.Bitmap;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.util.Log;

/**
 * It is the service interface to control the music play.
 * @author CHEN JIAN WEN
 *
 */
public class MusicServiceManager {


    private IMusicService mService;

	/**
     * Create a new NetServiceManager instance.
     * @param ibinder It is the Binder interface.
     * @param handler It is the target for messages.
     */
    public MusicServiceManager(IBinder ibinder, Handler handler) {
        mService = IMusicService.Stub.asInterface(ibinder);

    }
    
    public void playPause() {
    	if (mService != null) {
    		try {
				mService.processPlayPauseRequest();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
    		
    	}
    }
    
    /**
     * Stop the music and the service;
     */
    public void stop() {
    	if (mService != null) {
    		try {
    			mService.stop();
    		} catch (RemoteException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    public MusicData getMusicData() {
    	MusicData data = null;

    	if (mService != null) {
    		try {
    			data = mService.getMusicData();
    		} catch (Exception e ) {
    			e.printStackTrace();
    		}
    	}
    	return data;
    }

}