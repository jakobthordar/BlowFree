package com.example.BlowFreeApp;

public class GameInfo {

    int size;
    int id;
    public GameInfo(int size, int id) {
        this.size = size;
        this.id = id;
    }

    public int getSize() {

        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
