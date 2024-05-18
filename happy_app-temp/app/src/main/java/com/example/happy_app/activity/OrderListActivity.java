package com.example.happy_app.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_app.R;

public class OrderListActivity extends AppCompatActivity {

    private TextView textViewOrderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        textViewOrderList = findViewById(R.id.textViewOrderList);

        // Intent로부터 유저 정보 받기
        long memberId = getIntent().getLongExtra("memberId", -1);

        // 주문 목록 설정
        textViewOrderList.setText("회원 ID: " + memberId + "\n주문 목록 표시");
    }
}
