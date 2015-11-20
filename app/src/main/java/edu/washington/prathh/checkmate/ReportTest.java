package edu.washington.prathh.checkmate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import edu.washington.prathh.checkmate.report_test.Thanks;
import edu.washington.prathh.checkmate.user_flows.Login;

public class ReportTest extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button login = (Button)findViewById(R.id.test_submission);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(ReportTest.this).create();
                alertDialog.setTitle("Submission confirmation");
                alertDialog.setMessage("Submitting these results will anonymously notify any individuals within your network that may potentially be exposed to an STI.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "SUBMIT",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent myIntent = new Intent(ReportTest.this, Thanks.class);
                                startActivity(myIntent);
                            }
                        });
                alertDialog.show();
            }
        });
    }

}
