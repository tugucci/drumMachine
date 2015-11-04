package com.matata.hakuna.drummachine;


import android.content.res.Resources;
import android.content.res.TypedArray;

import android.os.Handler;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;


import java.util.List;


public class MainActivity extends ActionBarActivity {

    private int bpm = 120;
    private int MAXBPM = 210;
    private int MINBPM = 30;
    private int row = 1;
    private int column = 0;

    private Button addbpm;
    private Button subtractpm;

    private ToggleButton step1, step2,
    step3, step4,
            step5, step6,
    step7, step8,
            step9, step10,
    step11, step12,
            step13, step14,
    step15, step16;




    private  ToggleButton kick, snare, hh, perc;
    private ImageButton playButton;

    private TextView bpmLabel;
    private Seq mySeq;

    private  ToggleButton[] stepArray;
    private List curDrumSeq;

Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bpmLabel =  (TextView) findViewById(R.id.bpmNumber);
        setBpmLabel();

        addbpm = (Button) findViewById(R.id.addBpm);
        subtractpm = (Button) findViewById(R.id.subtractBpm);

        playButton = (ImageButton) findViewById(R.id.play);

        handler = new Handler();

        // Setting Up BPM stuff.
        View.OnClickListener bpmListener = new View.OnClickListener() {

            public void onClick(View v) {
                Button b = (Button) v;


                if (b.getText().toString().equals("+") && bpm < MAXBPM) {
                    bpm++;
                }
                if (b.getText().toString().equals("-") && bpm > MINBPM) {
                    bpm--;

                }

                setBpmLabel();
                Log.d("Bpm",String.valueOf(bpm));
            }
        };

        // Changing Bpm number By 10
        View.OnLongClickListener longClickListener = new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                Button b = (Button) v;


                if (b.getText().toString().equals("+") && bpm < MAXBPM-10) {
                    bpm=bpm+10;
                }
                if (b.getText().toString().equals("-") && bpm > MINBPM+10) {
                    bpm= bpm-10;

                }

                setBpmLabel();
                mySeq.setBpm(bpm);
                Log.d("Bpm",String.valueOf(bpm));
            return  true;
            }


        };




        addbpm.setOnClickListener(bpmListener);
        subtractpm.setOnClickListener(bpmListener);
        addbpm.setOnLongClickListener(longClickListener);
        subtractpm.setOnLongClickListener(longClickListener);

        final Handler myHandler = new Handler();

        //creating a sequence
        mySeq = new Seq(MainActivity.this, this.getApplicationContext());

        stepArray =  new ToggleButton[] {step1, step2,step3, step4, step5, step6, step7, step8,
                step9, step10, step11, step12,step13, step16, step15, step16};



        Resources res = getResources();
        TypedArray ids= res.obtainTypedArray(R.array.id);

        // StepSequencer ToggleButtons init
        for (int i =0; i<16; i++){

            stepArray[i] = (ToggleButton) findViewById(ids.getResourceId(i,i));
        }



        //Listener
        View.OnClickListener stepListener = new View.OnClickListener() {

            public void onClick(View v) {
                ToggleButton t = (ToggleButton) v;
                column = Integer.parseInt(t.getTag().toString());


                if(t.isChecked()){
                    mySeq.changestep(column, row, 1); //calling Seq method
                }
                else{
                    mySeq.changestep(column ,row, 0);
                }
                mySeq.outputSequence();


                Log.d("this is step number!", " " +t.getTag());
            }
        };
        //setting stepListener
        for (int i =0; i<16; i++){

            stepArray[i].setOnClickListener(stepListener);
        }


        // Drum & row choosing Setup
        kick = (ToggleButton) findViewById(R.id.kick);
        snare = (ToggleButton) findViewById(R.id.snare);
        hh = (ToggleButton) findViewById(R.id.hh);
        perc = (ToggleButton) findViewById(R.id.perc);

        View.OnClickListener drumListener = new View.OnClickListener() {

            public void onClick(View v) {
                ToggleButton t = (ToggleButton) v;
                row = Integer.parseInt(t.getTag().toString());
                Log.d("Row is", "" + t.getTag());//debugging purpose
                drumToggles(row);
                mySeq.outputSequence();//debugging purpose
                restoreStepsForDrum();

            }
        };

        kick.setOnClickListener(drumListener);
        snare.setOnClickListener(drumListener);
        hh.setOnClickListener(drumListener);
        perc.setOnClickListener(drumListener);

}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


/// restoring ToggleButton steps for a specific drum from sequence
    private void restoreStepsForDrum() {
      List sqnc =  mySeq.returnSequenceForDrum(row);
        for (int i =0; i<16; i++){
            int z = (int) sqnc.get(i);
            switch(z){
                case 1:
                    stepArray[i].setChecked(true);
                    break;
                case 0:
                    stepArray[i].setChecked(false);
            }

        }

    }



// set bpm
    private void setBpmLabel(){
        bpmLabel.setText(String.valueOf(bpm));
    }



    // drum choice toggle management
    private void drumToggles(int drumRow){
       switch (drumRow){
           case 1:
               kick.setChecked(true);
               hh.setChecked(false);
               snare.setChecked(false);
               perc.setChecked(false);
               break;
           case 2:
               kick.setChecked(false);
               hh.setChecked(true);
               snare.setChecked(false);
               perc.setChecked(false);
               break;
           case 3:
               kick.setChecked(false);
               hh.setChecked(false);
               snare.setChecked(true);
               perc.setChecked(false);
               break;
           case 4:
               kick.setChecked(false);
               hh.setChecked(false);
               snare.setChecked(false);
               perc.setChecked(true);
               break;
       }

    }



    public void playSeq(View view) {
        Log.d("Playing", "The Sequence");


        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                mySeq.play();


                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }


                });

            }
        });
        myThread.start();
        if(myThread.isAlive() ){

            playButton.setClickable(false);

        }
    }

    public void stopSeq(View view) {

        Log.d("Sequence", "Stopped");
        mySeq.stop();
        playButton.setClickable(true);

    }



    protected void onPause(){
        super.onPause();
        mySeq.stop();
        playButton.setClickable(true);

    }


    protected void onDestroy(){
        super.onDestroy();

    }
}

