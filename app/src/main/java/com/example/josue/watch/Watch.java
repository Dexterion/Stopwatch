package com.example.josue.watch;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Watch extends AppCompatActivity {
    private Chronometer chronometer;
    private boolean started= false, paused= false;
    long stoptime=0, value=0, Pause =0;
    Button SR;
    ListView list;
    ArrayList<String> tasks;
    String milis="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        SR = (Button)findViewById(R.id.button);
        list = (ListView)findViewById(R.id.listView);
        chronometer.setText("00:00:00");
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

            @Override
            public void onChronometerTick(Chronometer chronometer) {
                CharSequence text = chronometer.getText();
                if (text.length() == 5) {
                    chronometer.setText("00:" + text);
                } else if (text.length() == 7) {
                    chronometer.setText("0" + text);
                }
            }
        });
        tasks = new ArrayList<String>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public void Resume(View view) {
        if ( !started) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            started = true;
            SR.setText("Pause");
        }else {
            paused();
        }
    }
    public void paused() {
        if(!paused ){
            stoptime = SystemClock.elapsedRealtime();
            chronometer.stop();
            paused= true;
            SR.setText("Resume");
        }else {
            Pause = (SystemClock.elapsedRealtime() - stoptime);
            chronometer.setBase(chronometer.getBase() + Pause);
            chronometer.start();
            stoptime = 0;
            paused = false;
            SR.setText("Pause");
        }
    }
    public void Reset(View view) {
        if(started) {
            if (paused) {
                paused();
                value = SystemClock.elapsedRealtime() - chronometer.getBase();
            }else {
                value = SystemClock.elapsedRealtime() - chronometer.getBase();
            }
            long mil=value;
            while (mil >1000) {
            mil-=1000;
            }
            milis= String.valueOf(mil);
            String time=(String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(value),
                    TimeUnit.MILLISECONDS.toMinutes(value) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(value)),
                    TimeUnit.MILLISECONDS.toSeconds(value) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(value))))+":"+milis;
            tasks.add(time);
            ArrayAdapter<String> myarrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tasks);
            list.setAdapter(myarrayAdapter);
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.stop();
            started = false;
            paused = false;
            stoptime = 0;
            SR.setText("Start");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_watch, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
