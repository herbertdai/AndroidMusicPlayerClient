package com.howfun.music.control;


interface IMusicService
{ 
    void processPlayPauseRequest();
    
    void processPlayNowRequest();
    
    int getPosition();
    
    void setPosition(int pos);
    
    int getState();
    

} 
