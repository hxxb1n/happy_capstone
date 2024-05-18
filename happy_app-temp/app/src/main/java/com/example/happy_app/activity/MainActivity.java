package com.example.happy_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_app.R;
import com.example.happy_app.api.ApiClient;
import com.example.happy_app.api.CreateMemberRequest;
import com.example.happy_app.api.CreateMemberResponse;
import com.example.happy_app.api.MemberApi;

import java.io.IOException;

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

        // 로그인 버튼 클릭 시 작동
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idString = editTextUsername.getText().toString();
                String password = editTextLoginPassword.getText().toString();


                // 텍스트 박스에 아이디랑 비밀번호 입력되었는지 검증
                if (idString.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "아이디와 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 입력한 아이디가 숫자 타입인지 검증
                long id;
                try {
                    id = Long.parseLong(idString);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "유효한 아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }


                // 입력한 값으로 dto를 생성하고 api 호출
                CreateMemberRequest request = new CreateMemberRequest(id, password);
                MemberApi apiService = ApiClient.getApiService();

                apiService.login(request).enqueue(new Callback<CreateMemberResponse>() {
                    @Override
                    public void onResponse(Call<CreateMemberResponse> call, Response<CreateMemberResponse> response) {
                        if (response.isSuccessful()) {
                            CreateMemberResponse responseBody = response.body();
                            // 만약에 db에 아이디와 비밀번호 가 일치하지 않으면 null값을 응답함
                            assert responseBody != null;
                            Toast.makeText(MainActivity.this, "로그인 성공: " + responseBody.getName(), Toast.LENGTH_SHORT).show();

                            // 유저인지 배달원인지 dto에서 추출
                            String authority = responseBody.getAuthority();

                            // enum 타입을 비교해서 배달원이면 배달원 화면 아니면 유저화면 호출
                            Intent intent;
                            if ("DELIVERYMAN".equals(authority)) {
                                // 기사뷰 이동
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
                        if (t instanceof IOException) {
                            // 서버가 꺼져있을 때 메세지
                            Toast.makeText(MainActivity.this, "요청 지연", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("MainActivity", "로그인 요청 실패: " + t.getMessage(), t);
                            Toast.makeText(MainActivity.this, "로그인 요청 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
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
