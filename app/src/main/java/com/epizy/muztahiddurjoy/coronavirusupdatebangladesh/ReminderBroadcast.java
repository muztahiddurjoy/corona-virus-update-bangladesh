package com.epizy.muztahiddurjoy.coronavirusupdatebangladesh;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Random;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Random random = new Random();
        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"coronavirusbangladeshupdate").
                setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("COVID-19 Update")
                .setContentText("Click to see today's update")
                .setContentIntent(resultPendingIntent)
                .setVibrate(new long[]{100,500,1000,500})
                .setPriority(NotificationCompat.PRIORITY_MAX);
        NotificationManagerCompat compat = NotificationManagerCompat.from(context);
        compat.notify(random.nextInt(),builder.build());
    }
}
