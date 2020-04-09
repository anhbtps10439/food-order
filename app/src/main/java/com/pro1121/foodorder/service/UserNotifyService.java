package com.pro1121.foodorder.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pro1121.foodorder.R;
import com.pro1121.foodorder.RestartServiceReceiver;
import com.pro1121.foodorder.activity.SignInOut.MainActivity;

public class UserNotifyService extends Service {

    public final static String CHANNEL_ID_USER_NOTI = "123456789";
    public static final int NOTIFICATION_ID_USER_NOTI = 2121;
    private DatabaseReference db;

    public UserNotifyService() {
        this.db = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Service started!", Toast.LENGTH_SHORT).show();
        notifyWhenDataChange();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(getApplicationContext(), "Service End!", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "Service Restarting!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), RestartServiceReceiver.class);
        intent.putExtra("request", "user");
        getApplicationContext().sendBroadcast(intent);
    }

    public void notifyWhenDataChange()
    {
        createNotificationChannel();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//        create intent for Noti
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

//        create Noti

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID_USER_NOTI);
                builder.setSmallIcon(R.drawable.logo_remake);
                builder.setContentTitle("Menu mới cập nhật");
                builder.setContentText("Đặt ngay vài món ăn thôi nào!");
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                builder.setContentIntent(pendingIntent);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
                notificationManagerCompat.notify(NOTIFICATION_ID_USER_NOTI, builder.build());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        db.child("dish").addValueEventListener(valueEventListener);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getApplicationContext().getString(R.string.channel_name);
            String description = getApplicationContext().getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID_USER_NOTI, name, importance);
            channel.setDescription(description);
            // Register the cannel with the system; you can't change the importance
            //            // or other notification behaviors after thish
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
