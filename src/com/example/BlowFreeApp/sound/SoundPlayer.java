package com.example.BlowFreeApp.sound;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import com.example.BlowFreeApp.R;

public class SoundPlayer {

    Context context;
    MediaPlayer mediaPlayer;
    public SoundPlayer(Context c)
    {
        this.context = c;
    }

    public void playConnect(){
        mediaPlayer = MediaPlayer.create(context, R.raw.connect);
        mediaPlayer.start();
    }

    public void playWin(){
        mediaPlayer = MediaPlayer.create(context, R.raw.win);
        mediaPlayer.start();
        Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);
    }
    public void playTheme(){
        mediaPlayer = MediaPlayer.create(context, R.raw.theme);
        mediaPlayer.start();
    }
    public void stopTheme(){
        mediaPlayer = MediaPlayer.create(context, R.raw.theme);
        mediaPlayer.stop();
    }
}
