package com.example.birdstoreandroid.Feature.ZaloPay;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.birdstoreandroid.R;

public class PaymentNotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_notification);

        TextView txtNotification = findViewById(R.id.textViewNotify);

        Intent intent = getIntent();
        String transId = intent.getStringExtra("transactionId");
        txtNotification.setText(intent.getStringExtra("result") + transId);
    }
}