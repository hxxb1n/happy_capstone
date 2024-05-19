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

    private static final String TAG = "MainActivity";

    private EditText editTextUsername;
    private EditText editTextLoginPassword;
    private Button loginButton;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupListeners();
    }

    private void initViews() {
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextLoginPassword = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        signUpButton = findViewById(R.id.buttonSignUp);
    }

    private void setupListeners() {
        loginButton.setOnClickListener(this::onLoginButtonClick);
        signUpButton.setOnClickListener(this::onSignUpButtonClick);
    }

    private void onLoginButtonClick(View v) {
        String idString = editTextUsername.getText().toString();
        String password = editTextLoginPassword.getText().toString();

        if (idString.isEmpty() || password.isEmpty()) {
            showToast("아이디와 비밀번호를 입력해 주세요.");
            return;
        }

        long id;
        try {
            id = Long.parseLong(idString);
        } catch (NumberFormatException e) {
            showToast("유효한 아이디를 입력해 주세요.");
            return;
        }

        CreateMemberRequest request = new CreateMemberRequest(id, password);
        MemberApi apiService = ApiClient.getApiService();
        apiService.login(request).enqueue(new Callback<CreateMemberResponse>() {
            @Override
            public void onResponse(Call<CreateMemberResponse> call, Response<CreateMemberResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    handleLoginSuccess(response.body());
                } else {
                    showToast("아이디나 비밀번호가 틀립니다.");
                }
            }

            @Override
            public void onFailure(Call<CreateMemberResponse> call, Throwable t) {
                handleLoginFailure(t);
            }
        });
    }

    private void onSignUpButtonClick(View v) {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    private void handleLoginSuccess(CreateMemberResponse responseBody) {
        showToast("로그인 성공: " + responseBody.getName());

        Intent intent;
        if ("DELIVERYMAN".equals(responseBody.getAuthority())) {
            intent = new Intent(MainActivity.this, DeliverymanActivity.class);
        } else {
            intent = new Intent(MainActivity.this, ShoppingActivity.class);
        }
        intent.putExtra("memberName", responseBody.getName());
        intent.putExtra("memberId", responseBody.getId());
        startActivity(intent);
    }

    private void handleLoginFailure(Throwable t) {
        if (t instanceof IOException) {
            showToast("요청 지연");
        } else {
            Log.e(TAG, "로그인 요청 실패: " + t.getMessage(), t);
            showToast("로그인 요청 실패: " + t.getMessage());
        }
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
