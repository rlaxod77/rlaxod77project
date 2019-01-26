package study.booksearch.com.sampleservice;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    private static void initialize(Context c) {
        if (preferences == null) {
            preferences = c.getSharedPreferences(Constant.PERF, Context.MODE_PRIVATE);
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
