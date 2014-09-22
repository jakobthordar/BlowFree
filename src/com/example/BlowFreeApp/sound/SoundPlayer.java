package com.example.BlowFreeApp.sound;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import com.example.BlowFreeApp.R;

public class SoundPlayer {

    Context context;
    MediaPlayer mediaPlayerWin;
    MediaPlayer mediaPlayerConnect;
    MediaPlayer mediaPlayerTheme;
    public SoundPlayer(Context c)
    {
        this.context = c;
    }

    public void playConnect(){
        mediaPlayerConnect = MediaPlayer.create(context, R.raw.connect);
        mediaPlayerConnect.start();
    }
    public void playWin(){
        mediaPlayerWin = MediaPlayer.create(context, R.raw.win);
        mediaPlayerWin.start();
        Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);
    }
    public void playTheme(){
        mediaPlayerTheme = MediaPlayer.create(context, R.raw.theme);
        mediaPlayerTheme.start();
    }
    public MediaPlayer getThemePlayer(){
        return mediaPlayerTheme;
    }
}
