package com.diplomado.adrian.findjobs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import DaoGen.com.greendao.DaoGen.ContactEntityDao;
import DaoGen.com.greendao.DaoGen.DaoMaster;
import DaoGen.com.greendao.DaoGen.DaoSession;
import DaoGen.com.greendao.DaoGen.JobEntityDao;

/**
 * Created by Adrian on 21/10/2015.
 */
public class DaoDriver {
    public DaoDriver() {
    }
    public JobEntityDao getJobDao(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "JobEntity", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        JobEntityDao jobEntityDao= daoSession.getJobEntityDao();
        return jobEntityDao;
    }
    public ContactEntityDao getContactDao(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "ContactEntity", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        ContactEntityDao contactEntityDao= daoSession.getContactEntityDao();
        return contactEntityDao;
    }
}
