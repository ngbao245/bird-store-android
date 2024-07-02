package com.example.birdstoreandroid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.birdstoreandroid.R;
import com.example.birdstoreandroid.Feature.chatbot.ChatbotActivity;

public class MainActivity extends AppCompatActivity {
    ImageView ivChatbot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivChatbot = (ImageView) findViewById(R.id.ivChatbot);

        ivChatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChatbotActivity.class);
                startActivity(intent);
            }
        });
    }
}