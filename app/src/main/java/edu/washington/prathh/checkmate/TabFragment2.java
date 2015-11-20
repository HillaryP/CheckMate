package edu.washington.prathh.checkmate;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.washington.prathh.checkmate.log_activity.VerifyPartner;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment2 extends Fragment {

    Button myButton;
    public TabFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_tab_fragment2, container, false);
        myButton = (Button) myView.findViewById(R.id.report_test);
        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent myIntent = new Intent(getActivity(), ReportTest.class);
                startActivity(myIntent);
            }
        });
        return myView;
    }


}
