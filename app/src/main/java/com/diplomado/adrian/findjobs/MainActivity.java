package com.diplomado.adrian.findjobs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private UtilTools utilTools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        utilTools = new UtilTools(MainActivity.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_menu_icon);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }

        setupNavigationDrawerContent(navigationView);
        
        //Asignamos el primer fragmento en la pantalla inicial
        
        setFragment(0);
    }

    private void setFragment(int position) {



        switch (position){
            case 0:
                loadMainFragment();
                break;
            case 1:
                utilTools.getJobsData(false);
                loadMainFragment();
                Toast.makeText(this, R.string.sync_db, Toast.LENGTH_LONG).show();
                break;
        }
    }


    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_navigation_drawer_jobs:
                                menuItem.setChecked(true);
                                setFragment(0);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_reload:
                                menuItem.setChecked(true);
                                setFragment(1);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_post:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent = new Intent(MainActivity.this, PostJob.class);
                                startActivity(intent);
                                return true;
                        }
                        return true;
                    }
                });
    }

    public void loadMainFragment(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        JobFragment jobFragment = new JobFragment();
        fragmentTransaction.replace(R.id.fragment,jobFragment);
        fragmentTransaction.commit();
    }
    public void DetailJobs(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView viewId = (TextView) v.findViewById(R.id.id_job);
                Long idJob = Long.valueOf(viewId.getText().toString());

                Bundle arguments = new Bundle();
                arguments.putLong("ID_JOB", idJob);

                Intent intent = new Intent(MainActivity.this, DetailJob.class);
                intent.putExtras(arguments);
                startActivity(intent);
            }
        });
    }

}