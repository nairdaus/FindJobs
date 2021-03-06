package com.diplomado.adrian.findjobs;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by Adrian on 20/10/2015.
 */
public interface JobService {
    @GET("/work_posts.json")
    void getJob(Callback<List<Job>> response);

    @POST("/work_posts.json")
    void createJob(@Body Job job, Callback<Job> response);
}

