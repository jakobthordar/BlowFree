package com.example.BlowFreeApp;


import com.example.BlowFreeApp.activities.Pack;

import java.util.ArrayList;
import java.util.List;

public class Global {

    public List<Pack> mPacks;
    public List<PackLevels> mPacksLevels;
    public List<GameInfo> mGameInfo;
    public GameInfo activeGame;

    private static Global mInstance = new Global();


    public static Global getInstance() {
        return mInstance;
    }

    private Global()
    {
    }

}