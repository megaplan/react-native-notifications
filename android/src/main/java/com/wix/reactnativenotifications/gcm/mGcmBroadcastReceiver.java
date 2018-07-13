package com.wix.reactnativenotifications.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class mGcmBroadcastReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        GcmMessageHandlerService.enqueueWork(context, intent);
    }
}