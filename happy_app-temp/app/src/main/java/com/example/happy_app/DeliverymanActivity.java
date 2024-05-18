package com.example.happy_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DeliverymanActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveryman);

        TextView textView = findViewById(R.id.textViewDeliveryman);
        String memberName = getIntent().getStringExtra("memberName");
        long memberId = getIntent().getLongExtra("memberId", -1);

        textView.setText(memberName + "배달원님 안녕하세요.");
    }
}
