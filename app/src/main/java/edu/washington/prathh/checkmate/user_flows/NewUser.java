package edu.washington.prathh.checkmate.user_flows;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;

import edu.washington.prathh.checkmate.R;

public class NewUser extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        Button signup = (Button) findViewById(R.id.signup);
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
        //user.put("connections", new ArrayList<ParseObject>());

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
