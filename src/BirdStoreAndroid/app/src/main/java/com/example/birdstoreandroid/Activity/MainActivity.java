package com.example.birdstoreandroid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.birdstoreandroid.Feature.Cart.CartActivity;
import com.example.birdstoreandroid.Feature.GetProduct.GetProductActivity;
import com.example.birdstoreandroid.Feature.Notification.CustomNotification;
import com.example.birdstoreandroid.R;
import com.example.birdstoreandroid.Feature.chatbot.ChatbotActivity;

public class MainActivity extends AppCompatActivity {
    ImageView ivChatbot;
    Button btnProductMenu, cart;
    Button btnTestNoti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivChatbot = (ImageView) findViewById(R.id.ivChatbot);
        btnProductMenu = (Button) findViewById(R.id.btnProductMenu);
        cart = (Button) findViewById(R.id.Cartbtn);

        ivChatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChatbotActivity.class);
                startActivity(intent);
            }
        });

        btnProductMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetProductActivity.class);
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                }
        });
        btnTestNoti = findViewById(R.id.btnTestNoti);
        btnTestNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomNotification.class);
                startActivity(intent);
            }
        });
    }
}