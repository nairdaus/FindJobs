package com.diplomado.adrian.findjobs;


import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.diplomado.adrian.findjobs.R;
import com.diplomado.adrian.findjobs.MainActivity;

import DaoGen.com.greendao.DaoGen.JobEntity;

/**
 * Created by Adrian on 19/10/2015.
 */
public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder>{
private List<JobEntity> jobs;

private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static class JobViewHolder extends RecyclerView.ViewHolder{
    public TextView idJob;
    public TextView title;
    public TextView datePost;
    public View view;

    public JobViewHolder(final View itemView){
        super(itemView);
        idJob = (TextView) itemView.findViewById(R.id.id_job);
        title = (TextView) itemView.findViewById(R.id.job_title);
        datePost = (TextView) itemView.findViewById(R.id.job_date);
        }
    }
    public JobAdapter(List<JobEntity> jobs) {
        this.jobs = jobs;
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    @Override
    public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_jobs, parent, false);

        return new JobViewHolder(v);
    }

    @Override
    public void onBindViewHolder(JobViewHolder holder, int position) {
        String dateStr = null;
        try {
            dateStr = dateFormat.format(jobs.get(position).getPosted_date());
        }catch(Exception e){
            dateStr = dateFormat.format(new Date());
        }
        holder.idJob.setText(jobs.get(position).getId().toString());
        holder.title.setText(jobs.get(position).getTitle());
        holder.datePost.setText(dateStr);
    }
}
