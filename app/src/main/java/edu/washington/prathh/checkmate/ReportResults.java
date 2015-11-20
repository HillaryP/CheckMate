package edu.washington.prathh.checkmate;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class ReportResults extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/Avenir.ttc");
        setContentView(R.layout.activity_report_results);
    }

}
