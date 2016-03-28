package ua.kpi.infosecurity.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import ua.kpi.infosecurity.util.Logger;
import ua.kpi.infosecurity.util.Prefs;

/**
 * Created on 3/28/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Logger logger;
    protected Prefs prefs;

    protected abstract int getContentViewId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);

        logger = Logger.getInstance(this);
        prefs = Prefs.getInstance(this);

    }

}
