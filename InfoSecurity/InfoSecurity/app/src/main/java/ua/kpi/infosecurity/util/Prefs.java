package ua.kpi.infosecurity.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created on 3/28/16.
 */
public class Prefs {


    private static final String USER_ID = "user_id";
    private static final String IS_INITIALIZED = "is_initialized";


    private static Prefs instance;

    private final Context context;
    private final SharedPreferences prefs;

    @Deprecated
    public Prefs(final Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences("info_secured_prefs", Context.MODE_PRIVATE);
    }

    @SuppressWarnings("deprecation")
    public static Prefs getInstance(Context context) {
        if (instance == null) {
            synchronized (Prefs.class) {
                if (instance == null) {
                    instance = new Prefs(context);
                }
            }
        }
        return instance;
    }

    public void setUserId(String userId) {
        prefs.edit().putString(USER_ID, userId).apply();
    }

    public String getUserId() {
        return prefs.getString(USER_ID, "");
    }

    public boolean isInitialized() {
        return prefs.getBoolean(IS_INITIALIZED, false);
    }

    public void setIsInitialized() {
        prefs.edit().putBoolean(IS_INITIALIZED, true).apply();
    }

}
