package edu.byuh.cis.cs300.hello.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.byuh.cis.cs300.hello.TView;

public class MainActivity extends AppCompatActivity {

    private TView thaddeus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thaddeus = new TView(this);
        setContentView(thaddeus);
    }
    @Override
    public void onPause() {
        super.onPause();
        thaddeus.justGotBackgrounded();
    }

    @Override
    public void onResume() {
        super.onResume();
        thaddeus.returningToForeground();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thaddeus.prepareForShutdown();
    }


}


