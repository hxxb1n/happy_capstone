package com.example.happyness;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Binder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginButton = findViewById(R.id.logbt);
        Button joinButton = findViewById(R.id.joinbt);
        Button finishButton = findViewById(R.id.finishbt);

        EditText editTextPhoneNumber = findViewById(R.id.logid);
        EditText editTextPassword = findViewById(R.id.logpw);




        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 입력된 아이디와 비밀번호 가져오기
                String phoneNumber = editTextPhoneNumber.getText().toString();
                String password = editTextPassword.getText().toString();

                // 지정된 아이디와 비밀번호
                String correctPhoneNumber = "1";
                String correctPassword = "1";

                // 아이디와 비밀번호가 일치하는지 확인
                if (phoneNumber.equals(correctPhoneNumber) && password.equals(correctPassword)) {
                    // 로그인 성공
                    Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    // Intent 시작
                    startActivity(intent);

                } else {
                    // 로그인 실패
                    Toast.makeText(MainActivity.this, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // JoinActivity로 이동하는 Intent 생성
                Intent intent = new Intent(MainActivity.this,JoinActivity.class);
                // Intent 시작
                startActivity(intent);
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }
}