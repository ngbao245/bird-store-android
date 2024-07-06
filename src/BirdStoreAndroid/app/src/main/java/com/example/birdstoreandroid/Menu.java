package com.example.birdstoreandroid;

import android.content.Context;
import android.content.Intent;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;

public class Menu {

    public static void onCreateOptionsMenu(Context context, android.view.Menu menu) {
        MenuInflater inflater = new MenuInflater(context);
        inflater.inflate(R.menu.main_menu, menu);
    }

    public static boolean onOptionsItemSelected(Context context, @NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.lab5_1:
//                Intent lab5_1 = new Intent(context, Lab5_1.class);
//                context.startActivity(lab5_1);
                return true;
            case R.id.lab5_2:
//                Intent lab5_2 = new Intent(context, Lab5_2.class);
//                context.startActivity(lab5_2);
                return true;
            case R.id.lab6:
//                Intent lab6 = new Intent(context, Lab6.class);
//                context.startActivity(lab6);
                return true;
            case R.id.lab7_1:
//                Intent lab7_1 = new Intent(context, Lab7_1.class);
//                context.startActivity(lab7_1);
                return true;
            case R.id.lab7_2:
//                Intent lab7_2 = new Intent(context, Lab7_2.class);
//                context.startActivity(lab7_2);
                return true;
            default:
                return false;
        }
    }
}
