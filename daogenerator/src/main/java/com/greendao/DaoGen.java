package com.greendao;

import java.lang.Exception;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;


public class DaoGen {
    public static void main(String args[])throws Exception{
        Schema schema = new Schema(1,"com.greendao.DaoGen");
        Entity jobEntity = schema.addEntity("JobEntity");
        jobEntity.addIdProperty();
        jobEntity.addStringProperty("title");
        jobEntity.addStringProperty("description");
        jobEntity.addDateProperty("posted_date");

        Entity contactEntity = schema.addEntity("ContactEntity");
        contactEntity.addIdProperty();
        contactEntity.addStringProperty("number");

        Property jobId = contactEntity.addLongProperty("contactId").notNull().getProperty();
        ToMany jobToContacts = jobEntity.addToMany(contactEntity, jobId);
        jobToContacts.setName("contacts");

        new DaoGenerator().generateAll(schema,".");
    }
}
