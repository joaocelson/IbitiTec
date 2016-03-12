package com.ibititec.ldapp;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by ct002572 on 07/01/2016.
 */
public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
       //MultiDex.install(this);
//        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
//                .name("realm-turismo.realm")
//                .deleteRealmIfMigrationNeeded()
//                .build();
//
//        Realm.setDefaultConfiguration(realmConfiguration);
    }

    @Override
    protected  void attachBaseContext ( Context base )  {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}