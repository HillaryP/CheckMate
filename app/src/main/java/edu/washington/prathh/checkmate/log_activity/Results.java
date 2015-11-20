package edu.washington.prathh.checkmate.log_activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.washington.prathh.checkmate.R;

public class Results extends ActionBarActivity {
    private ParseUser curr;
    private Set<String> seen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.curr = ParseUser.getCurrentUser();
        this.seen = new HashSet<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        getUser("Oscar", "Wong", "2532698643", true);

    }

    private void getUser(final String first, final String last, final String num, final boolean prot) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("firstName", first);
        query.whereEqualTo("lastName", last);
        query.whereEqualTo("phoneNo", num);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, ParseException e) {
                // Create new user
                if (list.isEmpty()) {
                    ParseUser newUser = new ParseUser();
                    newUser.put("hasSTI", false);
                    newUser.put("phoneNo", num);
                    newUser.put("firstName", first);
                    newUser.put("lastName", last);
                    newUser.put("connections", new ArrayList<ParseObject>());
                }
                for (final ParseUser user : list) {
                    final ParseObject newInteraction = new ParseObject("Interaction");
                    newInteraction.put("oneUser", curr.getObjectId());
                    newInteraction.put("otherUser", user.getObjectId());
                    newInteraction.put("protected", prot);
                    newInteraction.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            ParseQuery<ParseObject> getUserInt = ParseQuery.getQuery("UserInteraction");
                            try {
                                getUserInt.whereEqualTo("UserId", newInteraction.getString("oneUser"));
                                getUserInt.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> list, ParseException e) {
                                        for (ParseObject innerUser : list) {
                                            innerUser.add("connections", newInteraction.getObjectId());
                                            innerUser.saveEventually();
                                        }
                                    }
                                });
                            } catch (Exception e3) {
                                Log.i("Connections", "OneUser " + e.getMessage());
                            }
                            ParseQuery<ParseObject> getUserInteractions = ParseQuery.getQuery("UserInteraction");
                            try {
                                getUserInteractions.whereEqualTo("UserId", newInteraction.getString("otherUser"));
                                getUserInteractions.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> list, ParseException e) {
                                        for (ParseObject innerUser : list) {
                                            innerUser.add("connections", newInteraction.getObjectId());
                                            innerUser.saveEventually();
                                        }
                                    }
                                });
                            } catch (Exception e2) {
                                Log.i("Connections", "Other User " + e.getMessage());
                            }
                            searchForProblems();
                        }
                    });

                }
            }
        });
    }

    private void searchForProblems() {
        crawlOnUser(ParseUser.getCurrentUser());
    }

    private void checkConns(List<String> connections) {
        if(!connections.isEmpty()) {
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
                } catch (Exception e) {
                    Log.i("Alerter", "getting Interactions " + e.getMessage());
                }
            }
        }
    }

    private void handleInteractions(List<ParseObject> list) {
        // For each interaction, find that user
        for (ParseObject interaction : list) {
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
        }
    }

    private void handleUsers(List<ParseUser> list) {
        for (ParseUser user : list) {
            if (!seen.contains(user.getObjectId())) {
                seen.add(user.getObjectId());
                boolean hasSTI = user.getBoolean("hasSTI");
                if (hasSTI) {
                    showAlert();
                } else {
                    crawlOnUser(user);
                }
            }
        }
    }

    public void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(Results.this).create();
        alertDialog.setTitle("You may want to get tested.");
        alertDialog.setMessage("We found evidence of STIs in your network. Click \"OK\" to get helpful clinic information.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void crawlOnUser(ParseUser user) {
        ParseQuery<ParseObject> getUserInt = ParseQuery.getQuery("UserInteraction");
        try {
            getUserInt.whereEqualTo("UserId", user.getObjectId());
            getUserInt.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, ParseException e) {
                    for (ParseObject object : list) {
                        List<String> objects = object.getList("connections");
                        checkConns(objects);
                    }
                }
            });
        } catch (Exception e3) {
            Log.i("Connections", "OneUser " + e3.getMessage());
        }
    }
}
