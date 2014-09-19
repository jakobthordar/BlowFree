package com.example.BlowFreeApp.activities;

public class Pack {

    private String mName;
    private String mDescription;
    private String mFile;

    public Pack( String name, String description, String file )  {
        mName = name;
        mDescription = description;
        mFile = file;
    }

    String getName()
    {
        return mName;
    }

    String getDescription() {
        return mDescription;
    }
    String getFile()
    {
        return mFile;
    }


}