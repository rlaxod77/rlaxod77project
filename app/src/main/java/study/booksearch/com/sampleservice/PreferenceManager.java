package study.booksearch.com.sampleservice;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

public class PreferenceManager {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    private static void initialize(Context c) {
        if (preferences == null) {
            preferences = c.getSharedPreferences("pref", Context.MODE_PRIVATE);
            editor = preferences.edit();
        }

    }


    public static String getString(Context c, String key) {
        String keyword = null;
        initialize(c);
        if (preferences.contains(key)) {
            keyword = preferences.getString(key, "");
        }
        return keyword;
    }

    public static void setString(Context c, String key, String value) {
        initialize(c);
        editor.putString(key, value);
        editor.commit();
    }
}
