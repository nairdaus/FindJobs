package com.diplomado.adrian.findjobs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import DaoGen.com.greendao.DaoGen.JobEntity;
import DaoGen.com.greendao.DaoGen.JobEntityDao;
/**
 * A simple {@link Fragment} subclass.
 */
public class JobFragment extends Fragment{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;

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
