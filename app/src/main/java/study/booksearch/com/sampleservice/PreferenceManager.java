package study.booksearch.com.sampleservice;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

public class PreferenceManager {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    private static void initialize(Context c){
        if(preferences == null){
            preferences = c.getSharedPreferences("pref",Context.MODE_PRIVATE);
            editor = preferences.edit();
        }

    }


    public static String getString(Context c, String key) {
        String aa = null;
        initialize(c);
        if (preferences.getBoolean("Auto_EditText_Write", false)) {
            aa =  preferences.getString(key,"");
         }
        return aa;
    }

    public static void setString(Context c, String key ,String s) {
        initialize(c);
        editor.putString(key,s);
        editor.putBoolean("Auto_EditText_Write", true);
        editor.commit();
    }
}
