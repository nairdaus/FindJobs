package com.diplomado.adrian.findjobs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import DaoGen.com.greendao.DaoGen.ContactEntity;
import DaoGen.com.greendao.DaoGen.ContactEntityDao;
import DaoGen.com.greendao.DaoGen.JobEntity;
import DaoGen.com.greendao.DaoGen.JobEntityDao;

public class DetailJob extends AppCompatActivity {

    Toolbar toolbar;
    public Long ID_JOB = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ID_JOB = bundle.getLong("ID_JOB");


        DaoDriver daoDriver = new DaoDriver();
        JobEntityDao jobDao = daoDriver.getJobDao(this);
        ContactEntityDao contactDao = daoDriver.getContactDao(this);

        JobEntity jobEntity=jobDao.loadByRowId(ID_JOB);

        this.setTitle(jobEntity.getTitle());

        TextView title,description, contacts;

        title = (TextView)findViewById(R.id.label_title_job);
        description = (TextView)findViewById(R.id.label_description);
        contacts = (TextView)findViewById(R.id.label_list_contact);

        String contactos = "Sin contactos";
        List<ContactEntity> listaContacts = contactDao._queryJobEntity_Contacts(jobEntity.getId());
        if (!listaContacts.isEmpty()){
            contactos = "";
            for (ContactEntity contacto: listaContacts){
                contactos = contactos + contacto.getNumber()+"\n";
            }
        }


        title.setText(jobEntity.getTitle());
        description.setText(jobEntity.getDescription());
        contacts.setText(contactos);
    }
}
