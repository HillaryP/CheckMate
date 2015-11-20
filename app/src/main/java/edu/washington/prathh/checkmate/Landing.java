package edu.washington.prathh.checkmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import edu.washington.prathh.checkmate.user_flows.Login;
import edu.washington.prathh.checkmate.user_flows.NewUser;

public class Landing extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Button login = (Button)findViewById(R.id.go_to_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Landing.this, Login.class);
                startActivity(intent);
            }
        });

        Button signup = (Button)findViewById(R.id.go_to_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Landing.this, NewUser.class);
                startActivity(intent);
            }
        });
    }

}
