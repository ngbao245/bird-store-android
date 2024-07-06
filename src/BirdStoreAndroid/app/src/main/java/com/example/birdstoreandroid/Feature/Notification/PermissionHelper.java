package com.example.birdstoreandroid.Feature.Notification;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.birdstoreandroid.Activity.MainActivity;

public class PermissionHelper {
    private static final int REQUEST_CODE_POST_NOTIFICATIONS = 1;

    public static void requestPermission(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Toast.makeText(activity, "Permissions not needed for this version of Android", Toast.LENGTH_SHORT).show();
            return;
        }

        if (activity.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(activity, "Permission already granted", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_CODE_POST_NOTIFICATIONS);
        }
    }

    public static void handlePermissionResult(int requestCode, int[] grantResults, Activity activity) {
        if (requestCode == REQUEST_CODE_POST_NOTIFICATIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Permission Denied", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear back stack
                activity.startActivity(intent);
                activity.finish();
            }
        }
    }
}
