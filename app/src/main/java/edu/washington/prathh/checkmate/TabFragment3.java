package edu.washington.prathh.checkmate;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment3 extends Fragment {


    public TabFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_tab_fragment3, container, false);
        Button b = (Button) v.findViewById(R.id.searchMap);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMap = new Intent(getActivity(), MapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("city", ((EditText) v.findViewById(R.id.city)).getText().toString());
                toMap.putExtras(bundle);
                startActivity(toMap);
            }
        });
        return v;
    }


}
