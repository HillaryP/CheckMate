package edu.washington.prathh.checkmate.report_test;

import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hillaryprather on 11/19/15.
 */
public class NetworkAlerter {
    private Set<String> seen;
    private Set<String> phoneNumbers;
    public NetworkAlerter() {
        this.seen = new HashSet<>();
        this.phoneNumbers = new HashSet<>();
    }

    public void alert() {
        ParseUser current = ParseUser.getCurrentUser();
        if (current != null) {
            Log.i("Alerter", "current user: " + current.getUsername());
            current.put("hasSTI", true);
            current.saveEventually();
            crawlOnUser(current);
        }
    }

    private void checkConns(List<String> connections) {
        Log.i("Alerter", "connections: " + connections);
        if (!connections.isEmpty()) {
            // For each connection, get that interaction
            for (String oneConnection : connections) {
                ParseQuery<ParseObject> getInteractions = ParseQuery.getQuery("Interaction");
                try {
                    getInteractions.get(oneConnection);
                    getInteractions.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> list, ParseException e) {
                            handleInteractions(list);
                        }
                    });
                } catch(Exception e) {
                    Log.i("Alerter", "getting Interactions " + e.getMessage());
                }
            }
        }
    }

    private void handleInteractions(List<ParseObject> list) {
        Log.i("Alerter", "interactions: " + list);
        // For each interaction, find that user
        for (ParseObject interaction : list) {
            boolean wasProtected = interaction.getBoolean("protected");
            //if (!wasProtected) {
                ParseQuery<ParseUser> getUser = ParseUser.getQuery();
                try {
                    getUser.get(interaction.getString("otherUser"));
                    getUser.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> list, ParseException e) {
                            handleUsers(list);
                        }
                    });
                } catch(Exception e2) {
                    Log.i("Alerter", "getting Users " + e2.getMessage());
                }
            //}
        }
    }

    private void handleUsers(List<ParseUser> list) {
        Log.i("Alerter", "users: " + list);
        for (ParseUser user : list) {
            if (!seen.contains(user.getObjectId())) {
                seen.add(user.getObjectId());

                String phoneNo = user.getString("phoneNo");
                Log.i("Alerter", phoneNo);
                this.phoneNumbers.add(phoneNo);
                crawlOnUser(user);
            }
        }
    }

    private void crawlOnUser(ParseUser user) {
        ParseQuery<ParseObject> getUserInt = ParseQuery.getQuery("UserInteraction");
        try {
            getUserInt.whereEqualTo("UserId", user.getObjectId());
            getUserInt.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, ParseException e) {
                    for (ParseObject object : list) {
                        Log.i("Alerter", "parseobject: " + object);
                        List<String> objects = object.getList("connections");
                        checkConns(objects);
                    }
                }
            });
        } catch (Exception e3) {
            Log.i("Connections", "OneUser " + e3.getMessage());
        }
    }

    public Set<String> getPhoneNumbers() {
        return this.phoneNumbers;
    }
}
