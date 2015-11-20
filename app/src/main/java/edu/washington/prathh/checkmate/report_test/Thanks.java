package edu.washington.prathh.checkmate.report_test;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.util.Log;

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
                Set<String> nums = alerter.getPhoneNumbers();
                for (String num : nums) {
                    PendingIntent pi = PendingIntent.getBroadcast(Thanks.this, 0, new Intent("DoNothing"), 0);
                    try {
                        Log.i("SMS", "sending");
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(num, null,
                                "Hi",
                                null, null);
                    } catch (Exception e) {
                        Log.i("SMS", e.getMessage());
                    }

                }
            }
        }, 10000);

    }

}
