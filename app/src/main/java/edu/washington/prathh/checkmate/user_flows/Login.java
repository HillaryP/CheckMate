package edu.washington.prathh.checkmate.user_flows;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import edu.washington.prathh.checkmate.CheckMateApp;
import edu.washington.prathh.checkmate.FontsOverride;
import edu.washington.prathh.checkmate.MainActivity;
import edu.washington.prathh.checkmate.R;
import edu.washington.prathh.checkmate.Signup;
import edu.washington.prathh.checkmate.report_test.Thanks;

public class Login extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/Avenir.ttc");
        setContentView(R.layout.activity_login);
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        Button signup = (Button) findViewById(R.id.landing_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Login.this, Signup.class);
                startActivity(myIntent);
            }
        });
    }

    public void login() {
        ParseUser.logInInBackground(((EditText)findViewById(R.id.login_username)).getText().toString(), ((EditText)findViewById(R.id.login_password)).getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (e != null) {
                    AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();
                    alertDialog.setTitle("Login Error");
                    alertDialog.setMessage("Your username or password is incorrect. Please try again.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                    installation.put("user", ParseUser.getCurrentUser());
                    installation.saveInBackground();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
