package com.diplomado.adrian.findjobs;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PostJob extends AppCompatActivity {
    public static  final String ENDPOINT = "https://dipandroid-ucb.herokuapp.com";
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText title, description, contact;
        title = (EditText)findViewById(R.id.editTitle);
        description = (EditText)findViewById(R.id.editDescription);
        contact = (EditText)findViewById(R.id.editContact);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        PostJob.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Job job = new Job();
                List<String> contacts = new ArrayList<>();
                contacts.add(String.valueOf(contact.getText()));

                job.setTitle(title.getText().toString());
                job.setDescription(description.getText().toString());
                job.setContacts(contacts);

                newJob(job);

                Intent intent = new Intent(PostJob.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jobs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.item_navigation_drawer_post) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void newJob(Job job){
        Log.d("El contacto es:", job.toString());
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();

        JobService api = restAdapter.create(JobService.class);
        api.createjob(job, new Callback<Job>() {
            @Override
            public void success(Job job, Response response) {
                Toast.makeText(getApplicationContext(),"Se ha guardado el Trabajo correctamente", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(),"Ha ocurrido un error y no se pudo guardar el trabajo", Toast.LENGTH_SHORT).show();
                Log.d("Error Retrofit", error.toString());
            }
        });


    }

}
