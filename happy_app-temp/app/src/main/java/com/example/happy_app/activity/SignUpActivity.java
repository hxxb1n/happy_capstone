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
import com.google.gson.Gson;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    private EditText editTextId, editTextName, editTextPassword;
    private Button buttonSignUp, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();
        setupListeners();
    }

    private void initViews() {
        editTextId = findViewById(R.id.editTextId);
        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        cancelButton = findViewById(R.id.buttonCancel);
    }

    private void setupListeners() {
        buttonSignUp.setOnClickListener(this::onSignUpButtonClick);
        cancelButton.setOnClickListener(this::onCancelButtonClick);
    }

    private void onSignUpButtonClick(View v) {
        String idStr = editTextId.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (idStr.isEmpty() || name.isEmpty() || password.isEmpty()) {
            showToast("사용할 아이디나 비밀번호 이름을 입력해 주세요.");
            return;
        }

        if (!idStr.matches("\\d{11}")) {
            showToast("전화번호를 정확히 입력해 주세요.");
            return;
        }

        if (name.matches(".*\\d+.*")) {
            showToast("이름에는 숫자가 들어갈 수 없어요.");
            return;
        }

        if (password.length() < 6) {
            showToast("비밀번호는 6자리 이상이어야 해요.");
            return;
        }

        // 전화번호의 첫 번째 0 제거
        String formattedPhoneNumber = idStr.substring(1);

        CreateMemberRequest request = new CreateMemberRequest(Long.valueOf(formattedPhoneNumber), name, password);
        Gson gson = new Gson();
        String json = gson.toJson(request);

        MemberApi apiService = ApiClient.getApiService();
        apiService.joinMember(request).enqueue(new Callback<CreateMemberResponse>() {
            @Override
            public void onResponse(Call<CreateMemberResponse> call, Response<CreateMemberResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    handleSignUpSuccess(response.body(), request);
                } else {
                    Toast.makeText(SignUpActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateMemberResponse> call, Throwable t) {
                handleSignUpFailure(t);
            }
        });
    }

    private void onCancelButtonClick(View v) {
        navigateToMainActivity();
    }

    private void handleSignUpSuccess(CreateMemberResponse responseBody, CreateMemberRequest request) {
        if (Objects.equals(responseBody.getName(), request.getName())) {
            showToast("회원 가입을 완료했어요.: " + responseBody.getName());
            navigateToMainActivity();
        } else if (Objects.equals(responseBody.getName(), "1")) {
            showToast(responseBody.getId() + " 는 이미 중복된 아이디예요.");
        } else {
            showToast("기타 메시지: " + responseBody.getName());
        }
    }

    private void handleSignUpFailure(Throwable t) {
        Log.e(TAG, "회원 가입 요청 실패: " + t.getMessage(), t);
        showToast("회원 가입 요청 실패: " + t.getMessage());
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
