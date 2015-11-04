package com.matata.hakuna.drummachine;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

/**
 * Created by antoniotugucci on 08.05.15.
 */
public class Sampler  {
    SoundPool sp;
    private int kickId;
    private int hhId;
    private int snareId;
    private int percId;
    AudioAttributes attr;
    private Context mycontext;



    public Sampler(Context appcontext){
        Log.d("Try construct a sampler", "Lets");
        try{

        this.mycontext = appcontext;
        // dealing with SoundPool

        // Since SoundPool is deprecated from Lollipop on we need to check whether it is lollipop or lower
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){


            attr = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN).
                            setUsage(AudioAttributes.USAGE_MEDIA).
                            build();
            sp = new SoundPool.Builder().setMaxStreams(4).setAudioAttributes(attr).build();

        }
        // if lower we go with good old SoundPool
        else{
            sp = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        }


        Log.d("Loading ", "Sounds");
        // Loading Sounds from res/raw
        kickId = sp.load(mycontext, R.raw.kick , 1);
        snareId = sp.load(mycontext, R.raw.snare, 1);
        percId = sp.load(mycontext, R.raw.perc, 1);
        hhId = sp.load(mycontext, R.raw.hh,1);

        Log.d("Sampler", "Created");

        }
        catch (Exception e){
            Log.d("exception", "exception", e);
            Log.d("Poo", "Happens");

        }
    }




    public void play(int[] curStep){

        for (int i = 1; i<5;i++){

            if (curStep[i] ==1){
                sp.play(returnDrum(i), 1, 1, 1, 0, 1);

            }

        }



    }

    // returns drum based on row.
    public int returnDrum(int drum){
        switch (drum){
            case 1:
                drum=kickId;
                break;
            case 2:
                drum =hhId;
                break;
            case 3:
                drum = snareId;
                break;
            case 4:
                drum = percId;
                break;

        }
        return drum;
    }


}
