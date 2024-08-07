package com.example.birdstoreandroid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.birdstoreandroid.Feature.Cart.CartActivity;
import com.example.birdstoreandroid.Feature.GetProduct.GetProductActivity;
import com.example.birdstoreandroid.Feature.GoogleMap.MapsActivity;
import com.example.birdstoreandroid.Feature.Notification.CustomNotification;
import com.example.birdstoreandroid.Feature.Order.OrderActivity;
import com.example.birdstoreandroid.Feature.ZaloPay.OrderPayment;
import com.example.birdstoreandroid.R;
import com.example.birdstoreandroid.Feature.chatbot.ChatbotActivity;

public class MainActivity extends AppCompatActivity {
    ImageView ivChatbot;
    Button btnProductMenu, cart;
    Button btnTestNoti;
    Button btnMap;
    Button orderList;
    Button btnZaloPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivChatbot = (ImageView) findViewById(R.id.ivChatbot);
        btnProductMenu = (Button) findViewById(R.id.btnProductMenu);
        cart = (Button) findViewById(R.id.Cartbtn);
        orderList = (Button) findViewById(R.id.buttonOrderList);

        btnMap = (Button) findViewById(R.id.btnMap);

        btnZaloPay = (Button) findViewById(R.id.btnZaloPay);

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
                startActivity(intent);
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

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
        orderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });

//        btnZaloPay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                double total = 10000;
//                Intent intent = new Intent(MainActivity.this, OrderPayment.class);
//                intent.putExtra("total", total);
//                startActivity(intent);
//            }
//        });
    }
}