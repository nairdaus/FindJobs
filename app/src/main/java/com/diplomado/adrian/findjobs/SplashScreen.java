package com.diplomado.adrian.findjobs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class SplashScreen extends AppCompatActivity {
    public static  final String ENDPOINT = "https://dipandroid-ucb.herokuapp.com";
    private UtilTools utilTools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        utilTools = new UtilTools(SplashScreen.this);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(1000);
                    utilTools.getJobsData(true);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}
