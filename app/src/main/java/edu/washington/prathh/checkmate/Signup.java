package edu.washington.prathh.checkmate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;

public class Signup extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button signup = (Button) findViewById(R.id.signup_new);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

    public void createUser() {
        String firstName = ((EditText) findViewById(R.id.first_name)).getText().toString();
        String lastName = ((EditText) findViewById(R.id.last_name)).getText().toString();
        String username = ((EditText)findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        String phoneNo = ((EditText) findViewById(R.id.phone_no)).getText().toString();
        boolean hasSTI = ((CheckBox) findViewById(R.id.has_sti)).isChecked();

        ParseUser user = new ParseUser();
        user.put("firstName", firstName);
        user.put("lastName", lastName);
        user.setUsername(username);
        user.setPassword(password);
        user.put("phoneNo", phoneNo);
        user.put("hasSTI", hasSTI);
        user.put("connections", new ArrayList<ParseObject>());

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    AlertDialog alertDialog = new AlertDialog.Builder(Signup.this).create();
                    alertDialog.setTitle("Sign Up Error");
                    alertDialog.setMessage(e.getMessage());
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
                    Intent intent = new Intent(Signup.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}
