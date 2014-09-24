package com.example.BlowFreeApp.sound;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import com.example.BlowFreeApp.PackLevelFactory;
import com.example.BlowFreeApp.R;

public class SoundPlayer {

    Context context;
    MediaPlayer mediaPlayerWin;
    MediaPlayer mediaPlayerConnect;



    public SoundPlayer(Context c)
    {
        this.context = c;
    }

    public void playConnect(){

        if(PackLevelFactory.getBoolForSound()) {
            mediaPlayerConnect = MediaPlayer.create(context, R.raw.connect);
            mediaPlayerConnect.start();
            mediaPlayerConnect.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                };
            });
        }

    }

    public void playWin(){
        if(PackLevelFactory.getBoolForSound()) {
            mediaPlayerWin = MediaPlayer.create(context, R.raw.win);
            mediaPlayerWin.start();
            mediaPlayerWin.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();

                }

                ;
            });
            Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);
        }
    }


}
