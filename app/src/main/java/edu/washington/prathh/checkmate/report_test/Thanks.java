package edu.washington.prathh.checkmate.report_test;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import edu.washington.prathh.checkmate.R;

public class Thanks extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);
        NetworkAlerter alerter = new NetworkAlerter();
        alerter.alert();
    }

}
