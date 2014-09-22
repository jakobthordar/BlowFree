package com.example.BlowFreeApp.settings;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.CheckBox;
import android.widget.Toast;
import com.example.BlowFreeApp.PackLevelFactory;
import com.example.BlowFreeApp.R;
import com.example.BlowFreeApp.sound.SoundPlayer;

public class GameSettings extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        CheckBoxPreference pref = (CheckBoxPreference) findPreference("sound_Theme_sound");
        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                CheckBoxPreference c = (CheckBoxPreference) preference;
                if ((!c.isChecked())) {

                    PackLevelFactory.getSoundPlayer().getThemePlayer().stop();
                    return true;
                }else{
                    PackLevelFactory.getSoundPlayer().playTheme();
                }
                return false;
            }
        });
    }
}