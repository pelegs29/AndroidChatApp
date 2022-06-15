package com.example.chatapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class firebaseService extends FirebaseMessagingService {
    public firebaseService() {
    }
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        int a = 1;
    }
//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
}