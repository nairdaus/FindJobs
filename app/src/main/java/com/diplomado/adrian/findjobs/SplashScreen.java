package com.diplomado.adrian.findjobs;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import DaoGen.com.greendao.DaoGen.ContactEntity;
import DaoGen.com.greendao.DaoGen.ContactEntityDao;
import DaoGen.com.greendao.DaoGen.JobEntity;
import DaoGen.com.greendao.DaoGen.JobEntityDao;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SplashScreen extends AppCompatActivity {
    public static  final String ENDPOINT = "https://dipandroid-ucb.herokuapp.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(1000);
                    getJobsData();
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
    public void getJobsData() {

        if (isOnline()){
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(ENDPOINT)
                    .build();

            JobService api = restAdapter.create(JobService.class);
            try {
                api.getJob(new Callback<List<Job>>() {
                    @Override
                    public void success(List<Job> jobs, Response response) {

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = new Date();

                        JobEntity jobEntity;
                        ContactEntity contactEntity;
                        DaoDriver daoDriver = new DaoDriver();
                        JobEntityDao jobDao = daoDriver.getJobDao(SplashScreen.this);
                        ContactEntityDao contactDao = daoDriver.getContactDao(SplashScreen.this);

                        jobDao.deleteAll();

                        for (Job job:jobs){
                            try {
                                date = sdf.parse(job.getPosted_date());
                            }catch (Exception e){
                                Toast.makeText(SplashScreen.this, "Error en el formato de la fecha, se guardara con fecha de hoy", Toast.LENGTH_SHORT).show();
                            }

                            jobEntity = new JobEntity(null, job.getTitle(),job.getDescription(),date);
                            jobDao.insert(jobEntity);
                            Log.d("Nombre del trabajo: ",jobEntity.getTitle());
                            Log.d("Numero de contactos",String.valueOf(job.getContacts().size()));

                            for(String numero:job.getContacts()){
                                contactEntity = new ContactEntity(null,numero,jobEntity.getId());
                                contactDao.insert(contactEntity);
                                Log.d("Numero: ", contactEntity.getNumber()+" :"+contactEntity.getContactId());
                            }
                        }
                        Toast.makeText(SplashScreen.this, "Se ha importado a la base de datos correctamente", Toast.LENGTH_SHORT).show();
                        startApp();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.getStackTraceString(error);
                        Toast.makeText(SplashScreen.this, "No se ha podido sincronizar con el servicio", Toast.LENGTH_SHORT).show();
                        startApp();
                    }
                });
            }catch (Exception e){
                Log.d("El error es:", e.toString());
            }

        }else{
            startApp();
            //Toast.makeText(SplashScreen.this, "No existe conexion a internet", Toast.LENGTH_SHORT).show();

        }

    }
    public void startApp(){
        Intent intent = new Intent(SplashScreen.this,MainActivity.class);
        startActivity(intent);
    }

    public boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager)(SplashScreen.this.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo netinfo= cm.getActiveNetworkInfo();
        if(netinfo != null && netinfo.isConnectedOrConnecting()){
            return true;
        }else{
            return false;
        }
    }
}
