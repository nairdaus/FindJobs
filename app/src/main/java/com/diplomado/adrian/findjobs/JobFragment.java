package com.diplomado.adrian.findjobs;


import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.diplomado.adrian.findjobs.R;
import com.diplomado.adrian.findjobs.MainActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import DaoGen.com.greendao.DaoGen.JobEntity;
import DaoGen.com.greendao.DaoGen.JobEntityDao;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobFragment extends Fragment{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;

    private List<Job> jobList;

    public static  final String ENDPOINT = "https://dipandroid-ucb.herokuapp.com";

    public JobFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_job,container,false);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(R.string.jobs_menu);
        //Aqui las acciones del fragmento
        RecyclerManager(view);

        return view;
    }

    public void RecyclerManager(View view) {
        final View newView=view;

        DaoDriver daoDriver = new DaoDriver();
        JobEntityDao jobDao = daoDriver.getJobDao(this.getContext());

        List<JobEntity> jobs = jobDao.queryBuilder().list();


        recyclerView = (RecyclerView) newView.findViewById(R.id.myRecycler);
        recyclerView.setHasFixedSize(true);

        manager = new LinearLayoutManager(newView.getContext());
        recyclerView.setLayoutManager(manager);

        adapter = new JobAdapter(jobs);
        recyclerView.setAdapter(adapter);

    }

}
