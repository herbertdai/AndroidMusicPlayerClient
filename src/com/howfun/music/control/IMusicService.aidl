package com.howfun.music.control;

import com.howfun.music.control.MusicData;

interface IMusicService
{ 
    void processPlayPauseRequest();
    
    void processPlayNowRequest();
    
    int getPosition();
    
    void setPosition(int pos);
    
    int getState();
    
    MusicData getMusicData();

    void stop();
    
    String getCurDisplayStr();
    
    Uri getAlbumUri();

} 
