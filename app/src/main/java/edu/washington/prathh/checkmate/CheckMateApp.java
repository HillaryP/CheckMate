package edu.washington.prathh.checkmate;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;

/**
 * Created by hillaryprather on 11/19/15.
 */
public class CheckMateApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "vMk45PBuOmB4H3J2vNHNqlgBQlgHJLuNJ35mHBYs", "yinOMwQjv6RYjr9rcVYhZBNpsnH01wE6bOSPOvxs");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        if (ParseUser.getCurrentUser() != null) {
            ParseUser.logOut();
        }
    }
}
