package com.example.BlowFreeApp.settings;


import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import com.example.BlowFreeApp.Board.CellpathColors;
import com.example.BlowFreeApp.PackLevelFactory;
import com.example.BlowFreeApp.R;

public class GameSettings extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        CheckBoxPreference pref = (CheckBoxPreference) findPreference("game_sound");
        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                CheckBoxPreference c = (CheckBoxPreference) preference;
                if ((!c.isChecked())) {

                    PackLevelFactory.setBoolForSound(false);
                   // PackLevelFactory.getSoundPlayer().setBoolForSounds(false);

                    return true;
                }else{
                    PackLevelFactory.setBoolForSound(true);
                   // PackLevelFactory.getSoundPlayer().setBoolForSounds(true);
                }
                return false;
            }
        });

        pref = (CheckBoxPreference) findPreference("color_theme_other");
        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                CheckBoxPreference c = (CheckBoxPreference) preference;
                if ((!c.isChecked())) {
                    CellpathColors.getInstance().setColorsTheme1();
                    return true;
                }else{
                    CellpathColors.getInstance().restoreColorTheme();
                }
                return false;
            }
        });
    }
}