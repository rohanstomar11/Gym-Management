package com.rohansingh.gymmanagement;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AnnouncementsService extends Service {
    public AnnouncementsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}