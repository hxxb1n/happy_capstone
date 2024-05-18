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

    private EditText editTextId, editTextName, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextId = findViewById(R.id.editTextId);
        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonSignUp = findViewById(R.id.buttonSignUp);

        // 회원 가입 버튼 클릭
        buttonSignUp.setOnClickListener(v -> {
            Long id = Long.valueOf(editTextId.getText().toString());
            String name = editTextName.getText().toString();
            String password = editTextPassword.getText().toString();

            CreateMemberRequest request = new CreateMemberRequest(id, name, password);
            Gson gson = new Gson();
            String json = gson.toJson(request);

            MemberApi apiService = ApiClient.getApiService();

            apiService.joinMember(request).enqueue(new Callback<CreateMemberResponse>() {
                @Override
                public void onResponse(Call<CreateMemberResponse> call, Response<CreateMemberResponse> response) {
                    if (response.isSuccessful()) {
                        CreateMemberResponse responseBody = response.body();
                        assert response.body() != null;

                        assert responseBody != null;
                        if (Objects.equals(responseBody.getName(), request.getName())) {
                            Toast.makeText(SignUpActivity.this, "회원 가입 완료: " + responseBody.getName(), Toast.LENGTH_SHORT).show();
                            navigateToMainActivity();
                        } else if (Objects.equals(responseBody.getName(), "1")) {
                            Toast.makeText(SignUpActivity.this, request.getId() + " 는 이미 중복된 아이디 입니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUpActivity.this, "기타 메시지: " + responseBody.getName(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CreateMemberResponse> call, Throwable t) {
                    Log.e("SignUpActivity", "회원 가입 요청 실패: " + t.getMessage(), t);
                    Toast.makeText(SignUpActivity.this, "회원 가입 요청 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        Button cancelButton = findViewById(R.id.buttonCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
