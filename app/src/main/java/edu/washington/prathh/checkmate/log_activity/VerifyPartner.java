package edu.washington.prathh.checkmate.log_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import edu.washington.prathh.checkmate.R;
import edu.washington.prathh.checkmate.ReportTest;

/**
 * Created by lilymdwyer on 11/20/15.
 */
public class VerifyPartner extends Activity{
    Button verifyButton;

    public VerifyPartner() {
        // Required empty public constructor
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_partner);
        verifyButton = (Button) findViewById(R.id.verifyButton);
        verifyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(VerifyPartner.this, ReportTest.class);
                startActivity(myIntent);
            }
        });
    }
}
