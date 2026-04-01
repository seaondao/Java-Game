package edu.byuh.cis.cs300.hello.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.CheckBoxPreference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;

import edu.byuh.cis.cs300.hello.R;
import edu.byuh.cis.cs300.hello.thems.DarkTheme;
import edu.byuh.cis.cs300.hello.thems.StandardTheme;
import edu.byuh.cis.cs300.hello.thems.Theme;

public class Prefs extends AppCompatActivity {
    private static final String MUSIC_PREF = "MUSIC_PREF";
    private static final String MUSIC_TYPE_PREF = "MUSIC_TYPE_PREF";
    private static final String THEME_COLOR_PREF = "THEME_COLOR_PREF";

    private static final String ORDER_PREF = "ORDER_PREF";

    private static final String ANI_SPEED_PREF = "ANI_SPEED_PREF";

    private static final String CHIP_STYLE_PREF = "CHIP_STYLE_PREF";
    private static final String ANIMATION_PREF = "ANIMATION_PREF";

    private static final String PLAY_MODE_PREF = "PLAY_MODE_PREF";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle b, String s) {
            Context context = getPreferenceManager().getContext();
            PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);

            //add preference widgets here

            //Music On OF
            var musicPref = new SwitchPreference(context);
            musicPref.setTitle("Play Background Music");
            musicPref.setSummaryOn("Music will play");
            musicPref.setSummaryOff("Music will not play");
            musicPref.setDefaultValue(true);
            musicPref.setKey(MUSIC_PREF);
            screen.addPreference(musicPref);

            //Chose the background song
            var musicTypePref= new SwitchPreference(context);
            musicTypePref.setTitle("Original or Retro");
            musicTypePref.setSummaryOn("Original Music");
            musicTypePref.setSummaryOff("Retro Music");
            musicTypePref.setDefaultValue(true);
            musicTypePref.setKey(MUSIC_TYPE_PREF);
            screen.addPreference(musicTypePref);

            //Chose the Theme color
            var themePref= new ListPreference(context);
            themePref.setTitle("Theme color");
            themePref.setSummary("Chose Your Theme Color?");
            themePref.setKey(THEME_COLOR_PREF);
            String[] themeS = {"Standard", "Dark"};
            themePref.setEntries(themeS);
            String[] themeV = {"Standard", "Dark"};
            themePref.setEntryValues(themeV);
            themePref.setDefaultValue("Standard");
            screen.addPreference(themePref);

            //Animation Or Teleport Instantly
            var animationPref= new CheckBoxPreference(context);
            animationPref.setTitle("Animate chip or not");
            animationPref.setSummaryOn("Chip will animate");
            animationPref.setSummaryOff("Chip will Teleport");
            animationPref.setDefaultValue(true);
            animationPref.setKey(ANIMATION_PREF);
            screen.addPreference(animationPref);

            //Chose the animation speed of chip moving
            var aniSpeedPref = new ListPreference(context);
            aniSpeedPref.setTitle("Animation Speed");
            aniSpeedPref.setSummary("Chose the speed of chip moves");
            aniSpeedPref.setKey(ANI_SPEED_PREF);
            String[] aniS = {"Slow" , "Normal" , "Fast"};
            aniSpeedPref.setEntries(aniS);
            String[] aniV = {"1", "2", "3"};
            aniSpeedPref.setEntryValues(aniV);
            aniSpeedPref.setDefaultValue("2");
            screen.addPreference(aniSpeedPref);

            //Chose which player go first
            var orderPref = new ListPreference(context);
            orderPref.setTitle("Which player go first");
            orderPref.setSummary("who do you want to start from?");
            orderPref.setKey(ORDER_PREF);
            String[] will = {"Light", "Dark", "Random"};
            orderPref.setEntries(will);
            String[] betero = {"1", "2", "3"};
            orderPref.setEntryValues(betero);
            orderPref.setDefaultValue("20");
            screen.addPreference(orderPref);

            //Chipset (standard, all power chips, no power chips)
            var chipStylePref = new ListPreference(context);
            chipStylePref.setTitle("What Kind of chip sets to play with");
            chipStylePref.setSummary("Chose standard, Al power, or no power chips");
            chipStylePref.setKey(CHIP_STYLE_PREF);
            String[] chipS = {"Standard", "All power", "No Power"};
            chipStylePref.setEntries(chipS);
            String[] chipV = {"1", "9","0"};
            chipStylePref.setEntryValues(chipV);
            chipStylePref.setDefaultValue("1");
            screen.addPreference(chipStylePref);

            //Play Mode (Two Human mode, Human(dark) vs Ai(light) or Human (light) vs AI (dark))
            var playModePref = new ListPreference(context);
            playModePref.setTitle("Chose Play mode");
            playModePref.setSummary("2 person, or One Person mode");
            playModePref.setKey(PLAY_MODE_PREF);
            String[] modeEnt = {"Two Person", "Human (light) vs AI (dark)"};
            playModePref.setEntries(modeEnt);
            String[] modeVal = {"1", "2"};
            playModePref.setEntryValues(modeVal);
            playModePref.setDefaultValue("solo");
            screen.addPreference(playModePref);


            setPreferenceScreen(screen);
        }
    }

    //Facades

    //Music On OF
    public static boolean getMusicPref(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(MUSIC_PREF, true);
    }

    //Chose the background song Original Or Retro
    public static boolean getSong(Context c){
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(MUSIC_TYPE_PREF, true);

    }

   //Animation Or Teleport Instantly
    public static boolean getAnimation(Context c){
        //If true Animate, if not Teleport
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(ANIMATION_PREF, true);

    }

    //return the Theme colo
    public static Theme getTheme(Context c){
        String tmp = PreferenceManager.getDefaultSharedPreferences(c).getString(THEME_COLOR_PREF, "Standard");
        if (tmp.equals("Dark")) {
            return new DarkTheme();
        } else {
            return new StandardTheme();
        }
    }

    //Get the Order of who go first.
    public static float getOrder(Context c) {
        String tmp = PreferenceManager.getDefaultSharedPreferences(c).getString(ORDER_PREF, "2");
        int[] teams = {1,-1};
        if(tmp.equals("3")){
            return teams[((int) ((Math.random() * 2)))];
        }else{
            if(tmp.equals("1")){
                return 1;
            }else{
                return -1;
            }
        }
    }

    //Return Animation speed 1,2 or 3
    public static int getAnimationSpeed(Context c){
        String tmp = PreferenceManager.getDefaultSharedPreferences(c).getString(ANI_SPEED_PREF, "1.5");
        return Integer.parseInt(tmp);
    }

    //Standard(1Power), all power or no power
    public static int getChipStylePref(Context c){
        String tmp = PreferenceManager.getDefaultSharedPreferences(c).getString(CHIP_STYLE_PREF, "1");
        return Integer.parseInt(tmp);
    }

    public static int playModePref(Context c){
        String tmp = PreferenceManager.getDefaultSharedPreferences(c).getString(PLAY_MODE_PREF, "1");
        return Integer.parseInt(tmp);
    }





}