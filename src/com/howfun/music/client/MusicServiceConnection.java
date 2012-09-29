package com.howfun.music.client;

import java.util.ArrayList;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;

/**
 * It is the connection of the music service.
 * @author CHEN JIAN WEN
 *
 */
public class MusicServiceConnection implements ServiceConnection {

    /**
     * It is the tag of the class.
     */
    @SuppressWarnings("unused")
    private static final String TAG = "MusicServiceConnection";


    /**
     * It is the context.
     */
    protected Context mContext = null;

    /**
     * It is the music service manager.
     */
    protected MusicServiceManager mMSMgr = null;

    /**
     * It is the handler.
     */
    protected Handler mHandler = null;

    /**
     * It contains the runnable that to run when connected.
     */
    protected ArrayList<Runnable> mToRuns;


	private boolean mServiceConnected;

    /**
     * It constructs the connection.
     * @param context It is the user context.
     * @param handler It is the handler to call process.
     */
    public MusicServiceConnection(Context context, Handler handler) {
        mContext = context;
        mHandler = handler;
        mToRuns = new ArrayList<Runnable>();
    }

    /**
     * It is used to clear the process runs when the musice service is connected.
     */
    public void clearOnConnectRunnable() {
        synchronized (mToRuns) {
            mToRuns.clear();
        }
    }

    /**
     * It gets the music manager.
     * @return It returns the music manager.
     */
    public MusicServiceManager getManager() {
        return mMSMgr;
    }

    /**
     * It registers a process that runs when the musice service is connnected.
     * @param run It is the process to register.
     */
    public void registerOnConnectRunnable(Runnable run) {
        synchronized (mToRuns) {
            if (mToRuns.contains(run)) return;

            mToRuns.add(run);
        }
    }

    /**
     * It makes the user connects to the service.
     */
    public void connect() {
        Intent service = new Intent();
        service.setClassName("com.howfun.music",
                "com.howfun.music.MusicService");
        mContext.bindService(service, this, Context.BIND_AUTO_CREATE);
    }

    /**
     * It makes the user disconnect from the service.
     */
    public void disconnect() {
        if (mMSMgr != null) {
            mContext.unbindService(this);
            mMSMgr = null;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) { 
    	Utils.log(TAG, "Service connected...");
    	
        mMSMgr = new MusicServiceManager(service, mHandler);

        mServiceConnected = true;
        synchronized (mToRuns) {
            for (Runnable toRun : mToRuns) {
                if (mHandler != null) {
                    mHandler.post(toRun);
                } else {
                    toRun.run();
                }
            }

            mToRuns.clear();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mMSMgr = null;
        synchronized (mToRuns) {
            mToRuns.clear();
        }
        mServiceConnected = false;
    }

	public boolean isServiceConnected() {
		return mServiceConnected;
	}


}