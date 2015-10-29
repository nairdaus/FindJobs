package com.diplomado.adrian.findjobs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import DaoGen.com.greendao.DaoGen.ContactEntity;
import DaoGen.com.greendao.DaoGen.ContactEntityDao;
import DaoGen.com.greendao.DaoGen.JobEntity;
import DaoGen.com.greendao.DaoGen.JobEntityDao;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by Adrian on 26/10/2015.
 */
public class UtilTools {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;
    private DaoDriver daoDriver = new DaoDriver();
    private JobEntityDao jobDao;
    private ContactEntityDao contactDao;

    private static final String ENDPOINT = "https://dipandroid-ucb.herokuapp.com/";
    Activity contentActivity;

    public UtilTools(Activity contentActivity) {
        this.contentActivity = contentActivity;
    }
    public void getJobsData(Boolean initApp) {

        if (isOnline()){
            RestAdapter builder = getRestAdapter();

            JobService api = builder.create(JobService.class);

            api.getJob(new Callback<List<Job>>() {
                @Override
                public void success(List<Job> jobs, Response response) {
                    insertDatabase(jobs);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("Error con retrofit: ", error.toString());
                    Toast.makeText(contentActivity, R.string.no_sync , Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Log.d("Error de internet","No existe conexion a internet");
        }
        if (initApp){
            startApp();
        }


    }
    public void newJob(Job job){
        Log.d("El trabajo es: ", job.toString());
        Gson gson = new Gson();
        String strJob = gson.toJson(job);
        RestAdapter restAdapter = getRestAdapter();
        JobService api = restAdapter.create(JobService.class);

        api.createJob(job, new Callback<Job>() {
            @Override
            public void success(Job job, Response response) {
                Log.d("El objeto exitoso es", job.toString());
                Toast.makeText(contentActivity, R.string.toast_save_job , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(contentActivity, R.string.toast_error_job , Toast.LENGTH_SHORT).show();
                Log.d("Error Retrofit", error.toString());
            }
        });

    }

    public RestAdapter getRestAdapter(){

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();
        return restAdapter;
    }

    public void startApp(){
        DaoDriver daoDriver = new DaoDriver();
        JobEntityDao jobDao = daoDriver.getJobDao(contentActivity.getBaseContext());
        if (!jobDao.loadAll().isEmpty()){
            Intent intent = new Intent(contentActivity, MainActivity.class);
            contentActivity.startActivity(intent);
        }else{
            Toast.makeText(contentActivity, R.string.no_inter_jobs ,Toast.LENGTH_LONG).show();
        }
    }

    public boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager)(contentActivity.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo netinfo= cm.getActiveNetworkInfo();
        if(netinfo != null && netinfo.isConnectedOrConnecting()){
            return true;
        }else{
            return false;
        }
    }

    public void insertDatabase(List<Job> jobs){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        JobEntity jobEntity;
        ContactEntity contactEntity;
        jobDao = daoDriver.getJobDao(contentActivity);
        contactDao = daoDriver.getContactDao(contentActivity);

        jobDao.deleteAll();
        contactDao.deleteAll();

        for (Job job : jobs) {
            try {
                date = sdf.parse(job.getPosted_date());
            } catch (Exception e) {
                Log.d("Error en la fecha", e.toString());
                //Toast.makeText(contentActivity, "Error en el formato de la fecha, se guardara con fecha de hoy", Toast.LENGTH_SHORT).show();
            }

            jobEntity = new JobEntity(null, job.getTitle(), job.getDescription(), date);
            jobDao.insert(jobEntity);

            for (String numero : job.getContacts()) {
                contactEntity = new ContactEntity(null, numero, jobEntity.getId());
                contactDao.insert(contactEntity);
            }
        }
        Toast.makeText(contentActivity, R.string.import_db, Toast.LENGTH_SHORT).show();

    }
    public void RecyclerManager(View view) {
        final View newView=view;

        jobDao = daoDriver.getJobDao(contentActivity.getBaseContext());

        List<JobEntity> jobs = jobDao.loadAll();


        recyclerView = (RecyclerView) newView.findViewById(R.id.myRecycler);
        recyclerView.setHasFixedSize(true);

        manager = new LinearLayoutManager(newView.getContext());
        recyclerView.setLayoutManager(manager);

        adapter = new JobAdapter(jobs);
        recyclerView.setAdapter(adapter);

    }
}
