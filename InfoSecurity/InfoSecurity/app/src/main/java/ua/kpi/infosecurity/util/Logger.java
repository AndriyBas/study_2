package ua.kpi.infosecurity.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.Date;

import io.realm.Realm;
import ua.kpi.infosecurity.model.LogEvent;

/**
 * Created on 3/28/16.
 */
public class Logger {

    public static Logger instance;

    private final Context context;

    public Logger(Context context) {
        this.context = context;
    }

    @SuppressWarnings("deprecation")
    public static Logger getInstance(Context context) {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger(context);
                }
            }
        }
        return instance;
    }

    public void logEvent(String type, String event) {
        if (TextUtils.isEmpty(event)) {
            return;
        }
        Log.d("Secured Logger", event);
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        LogEvent logEvent = realm.createObject(LogEvent.class);
        logEvent.setCreatedAt(new Date());
        logEvent.setType(type);
        logEvent.setText(event);

        realm.commitTransaction();
    }

}
