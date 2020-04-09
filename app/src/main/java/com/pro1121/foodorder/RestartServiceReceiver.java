package com.pro1121.foodorder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.pro1121.foodorder.service.AdminNotifyService;
import com.pro1121.foodorder.service.UserNotifyService;

public class RestartServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        if (intent.getExtras().getString("request").equals("admin"))
        {
            Intent restartServiceIntent = new Intent(context, AdminNotifyService.class);
            context.startService(restartServiceIntent);
        }
        else
        {
            if (intent.getExtras().getString("request").equals("user"))
            {
                Intent restartServiceIntent = new Intent(context, UserNotifyService.class);
                context.startService(restartServiceIntent);
            }
        }

        throw new UnsupportedOperationException("Not yet implemented");

    }
}
