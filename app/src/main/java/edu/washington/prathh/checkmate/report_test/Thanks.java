package edu.washington.prathh.checkmate.report_test;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.util.Log;

import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Set;

import edu.washington.prathh.checkmate.MainActivity;
import edu.washington.prathh.checkmate.R;

public class Thanks extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);
        final NetworkAlerter alerter = new NetworkAlerter();
        alerter.alert();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Set<ParseUser> nums = alerter.getPhoneNumbers();
                for (ParseUser num : nums) {
                    try {
                        Log.i("SMS", "sending to " + num);
                        ParseQuery pushQuery = ParseInstallation.getQuery();
                        pushQuery.whereEqualTo("user", num);

                        try {
                            ParsePush push = new ParsePush();
                            push.setQuery(pushQuery);
                            push.setMessage("Someone in your network has reported a potential STI. You may want to consider getting tested.");
                            push.sendInBackground();
                        } catch(Exception e) {
                            Log.i("SMS", e.getMessage());
                        }
                    } catch (Exception e) {
                        Log.i("SMS", e.getMessage());
                    }

                }
            }
        }, 10000);

    }

}
