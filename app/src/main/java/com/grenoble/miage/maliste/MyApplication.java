package com.grenoble.miage.maliste;

import android.app.Application;

import com.grenoble.miage.maliste.persistence.StorageService;

/**
 * Created by Romain on 20/02/2017.
 */

public class MyApplication extends Application {
    private StorageService storageService ;

    @Override
    public void onCreate() {
        super.onCreate();
        storageService = new SQLiteStorageServiceImpl();
    }

    public StorageService getStorageService() {
        return storageService;
    }
}
