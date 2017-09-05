package com.unitn.francesco.rendezvous;

import android.app.Application;
import android.util.Log;

import net.gotev.uploadservice.UploadService;

import static android.content.ContentValues.TAG;

public class Initializer extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // setup the broadcast action namespace string which will
        // be used to notify upload status.
        // Gradle automatically generates proper variable as below.
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;
        Log.d(TAG, "Initialized UploadService.NAMESPACE: "+UploadService.NAMESPACE);
    }
}
