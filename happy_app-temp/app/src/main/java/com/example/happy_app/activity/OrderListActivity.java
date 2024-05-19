package com.example.happy_app.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_app.R;

public class OrderListActivity extends AppCompatActivity {

    private static final String TAG = "OrderListActivity";

    private TextView textViewOrderList;
    private long memberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        initViews();
        memberId = getIntent().getLongExtra("memberId", -1);

        if (memberId == -1) {
            showToast("Invalid member ID");
            finish();
            return;
        }

        setupOrderList();
    }

    private void initViews() {
        textViewOrderList = findViewById(R.id.textViewOrderList);
    }

    private void setupOrderList() {
        textViewOrderList.setText("회원 ID: " + memberId + "\n주문 목록 표시");
    }

    private void showToast(String message) {
        Toast.makeText(OrderListActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
