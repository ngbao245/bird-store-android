package com.example.birdstoreandroid.Feature.Notification;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.birdstoreandroid.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomNotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_noti);

        // Request permission for notifications
        PermissionHelper.requestPermission(this);

        // Create notification channels
        NotificationHelper.createNotificationChannels(this);

        // Subscribe to Firebase topic
        FirebaseMessaging.getInstance().subscribeToTopic("FirebaseTestNoti")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscription successful";
                        if (!task.isSuccessful()) {
                            msg = "Subscription failed";
                        }
                        System.out.println(msg);
                    }
                });

        // Button to trigger custom notification
        Button btn = findViewById(R.id.btnTestNoti1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCustomNotification(
                        "Title custom notification",
                        "Large title custom notification",
                        "Large message custom notification");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Handle permission request result
        PermissionHelper.handlePermissionResult(requestCode, grantResults, this);
    }

    @SuppressLint("MissingPermission")
    private void sendCustomNotification(String smallTitle, String largeTitle, String largeMessage) {
        // Create RemoteViews for custom notification layout
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.small_notification_layout);
        notificationLayout.setTextViewText(R.id.notification_title_small, smallTitle);

        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.large_notification_layout);
        notificationLayoutExpanded.setTextViewText(R.id.notification_title_large, largeTitle);
        notificationLayoutExpanded.setTextViewText(R.id.notification_body_large, largeMessage);

        // Add timestamp to the custom notification
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String strDate = sdf.format(new Date());
        notificationLayout.setTextViewText(R.id.tv_time_custom_notification, strDate);

        // Build and display the custom notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationHelper.CHANNEL_ID_2)
                .setSmallIcon(R.drawable.ic_bird_small_icon)
                .setCustomContentView(notificationLayout)
                .setCustomBigContentView(notificationLayoutExpanded)
                .setColor(getResources().getColor(R.color.orange));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(getNotificationId(), builder.build());
    }

    private int getNotificationId() {
        return (int) System.currentTimeMillis();
    }
}
