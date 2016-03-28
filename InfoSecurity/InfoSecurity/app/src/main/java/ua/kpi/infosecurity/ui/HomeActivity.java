package ua.kpi.infosecurity.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Date;

import butterknife.Bind;
import io.realm.Realm;
import io.realm.RealmResults;
import ua.kpi.infosecurity.R;
import ua.kpi.infosecurity.base.BaseActivity;
import ua.kpi.infosecurity.model.FileObj;
import ua.kpi.infosecurity.model.LogEvent;
import ua.kpi.infosecurity.model.User;

/**
 * Created on 3/28/16.
 */
public class HomeActivity extends BaseActivity {

    public static final int INTERVAL_MILIS = 1000 * 60 * 1;
    private static final String TAG = "HomeActivity";

    @Bind(R.id.home_list_view)
    ListView listView;

    @Bind(R.id.home_user_name)
    TextView userName;

    private Handler handler;

    private AlertDialog captchaDialog;

    private Runnable firedRunnable = new Runnable() {
        @Override
        public void run() {
            showCaptcha();
            handler.postDelayed(firedRunnable, INTERVAL_MILIS);
        }
    };

    private void showCaptcha() {
        Log.d("HomeActivity", "fired alarm");

        if (captchaDialog != null && captchaDialog.isShowing()) {
            return;
        }

        User user = getLoggedInUser();
        if (user == null) {
            logOut();
            return;
        }
        int result = 2 * user.getSecret() + 4;

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        final EditText edittext = new EditText(this);
        alert.setMessage("Enter: 2 * X + 4 = ");
        alert.setTitle("Verification");

        alert.setView(edittext);

        alert.setPositiveButton("Check", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String verificationText = edittext.getText().toString().trim();
                if (String.valueOf(result).equals(verificationText)) {
                    // good
                    logger.logEvent(LogEvent.TYPE_LOG, user.toString() + " passed verification");
                } else {
                    logger.logEvent(LogEvent.TYPE_ERROR, user.toString() + " NOT passed verification");

                    Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            int maxAttempts = user.getAllowedAuthFailures() - 1;
                            if (maxAttempts <= 0) {
                                logger.logEvent(LogEvent.TYPE_ERROR, user.toString() + " maximum failure attempts exceeded, deleting user");
                                user.removeFromRealm();
                            } else {
                                user.setAllowedAuthFailures(maxAttempts);
                            }
                        }
                    });

                    logOut();
                }
                captchaDialog = null;
            }
        });

        alert.setNegativeButton("Log Out", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                dialog.cancel();
                captchaDialog = null;
                logOut();
            }
        });

        alert.setCancelable(false);
        logger.logEvent(LogEvent.TYPE_LOG, "Verification requested for " + user.toString());
        captchaDialog = alert.show();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();

        User loggedInUser = getLoggedInUser();
        if (loggedInUser != null) {
            userName.setText(loggedInUser.getName() + " / " + loggedInUser.getEmail());

            RealmResults<FileObj> files = Realm.getDefaultInstance().where(FileObj.class).findAll();
            FilesAdapter adapter = new FilesAdapter(this, files, loggedInUser.getUuid());
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    logger.logEvent(LogEvent.TYPE_WARNING, loggedInUser.toString() + " tried to open file : " + files.get(position));
                }
            });

            if (!loggedInUser.isAdmin()) {
                handler.removeCallbacks(firedRunnable);
                handler.postDelayed(firedRunnable, INTERVAL_MILIS);
            }

        } else {
            goToLogIn();
        }
    }

    private User getLoggedInUser() {
        return Realm.getDefaultInstance().where(User.class).equalTo("uuid", prefs.getUserId()).findFirst();
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(firedRunnable);
        firedRunnable = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        User loggedInUser = getLoggedInUser();
        if (loggedInUser != null && loggedInUser.isAdmin()) {
            MenuItem menuItemStatistic = menu.findItem(R.id.home_print_statistic);
            menuItemStatistic.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_log_out:
                logOut();
                return true;
            case R.id.home_print_statistic:
                printStatistic();
                return true;
        }
        return false;
    }

    private void wow() {
//        int x = 1;
//        Bundle b = new Bundle();
//        b.put("key", x);
//        FragmentManager fm = getFragmentManager();
//
//        fm.beginTransaction().add
    }

    private void printStatistic() {

        Realm realm = Realm.getDefaultInstance();
        int hour = 1000 * 60 * 60;
        Date dateHourBefore = new Date(System.currentTimeMillis() - hour);
        RealmResults<LogEvent> events = realm.where(LogEvent.class)
                .greaterThan("createdAt", dateHourBefore)
                .findAllSorted("createdAt");

        int totalEvents = events.size();
        RealmResults<LogEvent> eventsCritical = realm.where(LogEvent.class)
                .greaterThan("createdAt", dateHourBefore)
                .equalTo("type", LogEvent.TYPE_ERROR)
                .findAllSorted("createdAt");

        RealmResults<LogEvent> eventsWarnings = realm.where(LogEvent.class)
                .greaterThan("createdAt", dateHourBefore)
                .equalTo("type", LogEvent.TYPE_WARNING)
                .findAllSorted("createdAt");

        int criticalEvents = eventsCritical.size();
        int warningEvents = eventsWarnings.size();

        Log.d(TAG, "statistic for last hour");
        Log.d(TAG, "total events: " + totalEvents);
        Log.d(TAG, "critical events: " + criticalEvents);
        Log.d(TAG, "warning events: " + warningEvents);

    }

    private void logOut() {
        prefs.setUserId("");
        goToLogIn();
    }

    private void goToLogIn() {
        prefs.setUserId("");
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
