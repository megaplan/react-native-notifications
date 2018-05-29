package com.wix.reactnativenotifications.gcm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.JobIntentService;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.wix.reactnativenotifications.core.notification.IPushNotification;
import com.wix.reactnativenotifications.core.notification.PushNotification;

import static com.wix.reactnativenotifications.Defs.LOGTAG;

public class GcmMessageHandlerService extends JobIntentService
{
    /**
     * Unique job ID for this service.
     */
    static final int JOB_ID = 1000;

    /**
     * Convenience method for enqueuing work in to this service.
     */
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, GcmMessageHandlerService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(Intent intent)
    {

        Bundle extras = intent.getExtras();
        GoogleCloudMessaging service = GoogleCloudMessaging.getInstance(this);

        String messageType = service.getMessageType(intent);

        Log.d("PUSH", "GCMIntentService received message type : " + messageType);

        if (!extras.isEmpty())
        {
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType))
            {
                try {
                    final IPushNotification notification = PushNotification.get(getApplicationContext(), extras);
                    notification.onReceived();
                } catch (IPushNotification.InvalidNotificationException e) {
                    // A GCM message, yes - but not the kind we know how to work with.
                    Log.v(LOGTAG, "GCM message handling aborted", e);
                }

            }
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

}