package com.example.birdstoreandroid.Feature.Notification;


import android.annotation.SuppressLint;
import android.widget.RemoteViews;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.birdstoreandroid.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Date;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            sendNotification(title, body);
        }
    }

    @SuppressLint("MissingPermission")
    private void sendNotification(String title, String body) {
        // Create custom views for notification
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.small_notification_layout);
        notificationLayout.setTextViewText(R.id.notification_title_small, title);

        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.large_notification_layout);
        notificationLayoutExpanded.setTextViewText(R.id.notification_title_large, title);
        notificationLayoutExpanded.setTextViewText(R.id.notification_body_large, body);

        // Build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationHelper.CHANNEL_ID_2)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_bird_small_icon)
                .setCustomContentView(notificationLayout)
                .setCustomBigContentView(notificationLayoutExpanded)
                .setColor(getResources().getColor(R.color.orange));

        // Show notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(getNotificationId(), builder.build());
    }

    private int getNotificationId() {
        return (int) new Date().getTime();
    }
}
