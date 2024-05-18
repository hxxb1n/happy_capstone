package com.example.happy_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_app.api.ApiClient;
import com.example.happy_app.api.CreateMemberRequest;
import com.example.happy_app.api.CreateMemberResponse;
import com.example.happy_app.api.MemberApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextLoginPassword = findViewById(R.id.editTextPassword);
        Button loginButton = findViewById(R.id.buttonLogin);
        Button signUpButton = findViewById(R.id.buttonSignUp);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idString = editTextUsername.getText().toString();
                String password = editTextLoginPassword.getText().toString();


                if (idString.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "아이디와 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                long id;
                try {
                    id = Long.parseLong(idString);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "유효한 아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }


                CreateMemberRequest request = new CreateMemberRequest(id, password);
                MemberApi apiService = ApiClient.getApiService();

                apiService.login(request).enqueue(new Callback<CreateMemberResponse>() {
                    @Override
                    public void onResponse(Call<CreateMemberResponse> call, Response<CreateMemberResponse> response) {
                        if (response.isSuccessful()) {
                            CreateMemberResponse responseBody = response.body();
                            assert responseBody != null;
                            Toast.makeText(MainActivity.this, "로그인 성공: " + responseBody.getName(), Toast.LENGTH_SHORT).show();

                            String authority = responseBody.getAuthority();

                            Intent intent;
                            if ("DELIVERYMAN".equals(authority)) {
                                intent = new Intent(MainActivity.this, DeliverymanActivity.class);
                            } else {
                                // 쇼핑몰 이동
                                intent = new Intent(MainActivity.this, ShoppingActivity.class);
                            }
                            intent.putExtra("memberName", responseBody.getName());
                            intent.putExtra("memberId", responseBody.getId());
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "아이디나 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateMemberResponse> call, Throwable t) {
                        Log.e("MainActivity", "로그인 요청 실패: " + t.getMessage(), t);
                        Toast.makeText(MainActivity.this, "로그인 요청 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // 회원 가입 버튼 클릭
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
