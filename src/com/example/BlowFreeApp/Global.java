package com.example.BlowFreeApp;


import com.example.BlowFreeApp.activities.Pack;

import java.util.List;

public class Global {

    public List<Pack> mPacks;
    public List<PackLevels> mPacksLevels;
    public  List<GameInfo> mGameInfo;

    private static Global mInstance = new Global();

    public static Global getInstance() {
        return mInstance;
    }

    private Global()
    {
        // empty
    }

}