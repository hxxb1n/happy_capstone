package com.example.happy_app.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_app.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewProfile = findViewById(R.id.textViewProfile);

        // Intent로부터 유저 정보 받기
        String memberName = getIntent().getStringExtra("memberName");
        long memberId = getIntent().getLongExtra("memberId", -1);

        // 유저 정보 설정
        textViewProfile.setText("회원 이름: " + memberName + "\n회원 ID: " + memberId);
    }
}
