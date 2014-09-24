package com.example.BlowFreeApp.sound;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import com.example.BlowFreeApp.R;

public class SoundPlayer {

    Context context;
    MediaPlayer mediaPlayerWin;
    MediaPlayer mediaPlayerConnect;

    boolean shouldBePlaying = true;

    public SoundPlayer(Context c)
    {
        this.context = c;
    }

    public void playConnect(){
        if(shouldBePlaying) {
            mediaPlayerConnect = MediaPlayer.create(context, R.raw.connect);
            mediaPlayerConnect.start();

        }
    }
    public void playWin(){
        if(shouldBePlaying) {
            mediaPlayerWin = MediaPlayer.create(context, R.raw.win);
            mediaPlayerWin.start();

            Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);
        }
    }

    public void setBoolForSounds(boolean b){
        shouldBePlaying = b;
    }
}
