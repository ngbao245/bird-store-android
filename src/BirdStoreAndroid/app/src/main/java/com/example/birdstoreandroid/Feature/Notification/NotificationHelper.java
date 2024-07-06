package com.example.birdstoreandroid.Feature.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import com.example.birdstoreandroid.R;

public class NotificationHelper {
    public static final String CHANNEL_ID_1 = "CHANNEL_1";
    public static final String CHANNEL_ID_2 = "CHANNEL_2";

    public static void createNotificationChannels(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Uri funSound = Uri.parse("android.resource://" +
                    context.getPackageName() +
                    "/" + R.raw.raw_fun);

            Uri notiSound = Uri.parse("android.resource://" +
                    context.getPackageName() +
                    "/" + R.raw.raw_notification);

            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();

            // Configuring channel 1
            CharSequence name1 = "Channel 1";
            String description1 = "This is Channel 1";
            int importanceMin = NotificationManager.IMPORTANCE_MIN;
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_ID_1, name1, importanceMin);
            channel1.setDescription(description1);
            channel1.setSound(funSound, attributes);

            // Configuring channel 2
            CharSequence name2 = "Channel 2";
            String description2 = "This is Channel 2";
            int importanceHigh = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel2 = new NotificationChannel(CHANNEL_ID_2, name2, importanceHigh);
            channel2.setDescription(description2);
            channel2.setSound(notiSound, attributes);

            // Register the channels with the system
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel1);
                notificationManager.createNotificationChannel(channel2);
            }
        }
    }
}
