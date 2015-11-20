package edu.washington.prathh.checkmate;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import edu.washington.prathh.checkmate.log_activity.VerifyPartner;

public class TabFragment1 extends Fragment {
    Button myButton;

    public TabFragment1() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedinstanceState) {
        View myView = inflater.inflate(R.layout.fragment_tab_fragment1, container, false);
        myButton = (Button) myView.findViewById(R.id.submitNumber);
        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent myIntent = new Intent(getActivity(), VerifyPartner.class);
                startActivity(myIntent);
            }
        });
        return myView;
    }
}
