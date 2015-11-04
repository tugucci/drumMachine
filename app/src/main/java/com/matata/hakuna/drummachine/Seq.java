package com.matata.hakuna.drummachine;

import android.content.Context;

import android.util.Log;

import java.util.ArrayList;


/**
 * Created by antoniotugucci on 08.05.15.
 */
public class Seq {
    int[][] Sequet;
    Sampler mySampler;
    private Context mycontext;
    public boolean playing;
    private int bpm;
    private int step;
    private MainActivity mainAct;
    public Seq(MainActivity act, Context context) {
        this.mycontext = context;

        bpm = 120;
        mainAct = act;
        playing = true;

    Sequet = new int[16][5];
    for (int i = 0; i<16; i++){
        Sequet[i] = new int[]{1, 0, 0, 0, 0};
    }
    outputSequence();
    mySampler  = new Sampler(mycontext);

}


    public void changestep(int row, int column, int value){
        Sequet[row][column] = value;
    }

    public ArrayList returnSequenceForDrum(int row){
        ArrayList<Integer> drumsequence = new ArrayList<>();


        for (int i = 0; i<16; i++){
            int j = Sequet[i][row];
            drumsequence.add(j);
        }

        Log.d("drumsequence",""+drumsequence);
        return drumsequence;
    }





  public void play(){
    playing = true;



      while (playing == true) {
          for (int i = 0; i < 16; i++) {
              mySampler.play(Sequet[i]);
              step = i;
              if(playing ==false){
                  break;
              }
              try {
                  Thread.sleep(60000/(bpm*4));

                  if (i == 15) i = -1;



              } catch (InterruptedException e) {
                  e.printStackTrace();
              }

          }

      }

  }

    public int returnStep() {
    return step;
    }


    public void stop(){

        playing = false;

    }
    // setting bpm for sequence
    public void setBpm(int bpmIn){
        bpm = bpmIn;
    }

    public void outputSequence(){

        for (int j = 0; j<16; j++){
            System.out.println("Step"+ j);

            for (int z =0; z<5; z++){
                System.out.println(Sequet[j][z] + " ");
            }
            System.out.println(" ------ ");

        }
    }




}
