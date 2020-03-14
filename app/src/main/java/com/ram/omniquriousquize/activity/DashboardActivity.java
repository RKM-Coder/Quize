package com.ram.omniquriousquize.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.ram.omniquriousquize.R;
import com.ram.omniquriousquize.event.StartNewGameEvent;
import com.ram.omniquriousquize.session.GameSession;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by RKM on 3/14/20.
 */
public class DashboardActivity extends AppCompatActivity {
    //Tag
    private static final String TAG = DashboardActivity.class.getName();

    //Variables
    private EventBus mEventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mEventBus = EventBus.getDefault();
        mEventBus.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEventBus.unregister(this);
    }

    @Subscribe
    public void OnStartNewGameClicked(StartNewGameEvent event) {
        GameSession.getInstance().resetSession();
        startActivity(new Intent(this, QuizActivity.class));
    }
}
