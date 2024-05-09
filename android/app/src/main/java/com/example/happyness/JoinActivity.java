package com.example.happyness;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JoinActivity extends Activity{


    private boolean joinsucess = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);
        Button backButton = findViewById(R.id.joinbbt);
        Button SignUpButton = findViewById(R.id.buttonSignUp);

        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        EditText editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MainActivity로 되돌아가는 Intent 생성
                Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                // Intent 시작
                startActivity(intent);
            }
        });

        SignUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String membername = editTextName.getText().toString().trim();
                String phnumber = editTextPhoneNumber.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString();

                if (TextUtils.isEmpty(membername)) {
                    Toast.makeText(getApplicationContext(), "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    if (TextUtils.isEmpty(phnumber)) {
                        Toast.makeText(getApplicationContext(), "전화번호를 입력하세요.", Toast.LENGTH_SHORT).show();

                    } else {
                        if (TextUtils.isEmpty(phnumber)) {
                            Toast.makeText(getApplicationContext(), "전화번호를 입력하세요.", Toast.LENGTH_SHORT).show();

                        } else {
                            if (TextUtils.isEmpty(password)) {
                                Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                            } else {
                                if (TextUtils.isEmpty(confirmPassword)) {
                                    Toast.makeText(getApplicationContext(), "비밀번호 확인을 입력하세요.", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (password.equals(confirmPassword)) {
                                        Toast.makeText(JoinActivity.this, "비밀번호 일치", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(JoinActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                        joinsucess = true;
                                        //JSONObject jsonObject = new JSONObject(); //head오브젝트와 body오브젝트를 담을 JSON오브젝트
/*
                                        try {
                                            jsonObject.put("name", membername);
                                            jsonObject.put("id", phnumber);
                                            jsonObject.put("password", password);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
*/

                                        setContentView(R.layout.activity_main);
                                        User user = new User(membername, phnumber, password);
                                        Gson gson = new Gson();
                                        String json = gson.toJson(user);
                                        ApiService apiService = ApiClient.getClient().create(ApiService.class);
                                        Call<Void> call = apiService.createUser(user);
                                        call.enqueue(new Callback<Void>() {
                                            public void onResponse( Call<Void> call,  Response<Void> response) {
                                                if (response.isSuccessful()) {
                                                    Log.d("MainActivity", "서버로 데이터 전송이 성공했습니다.");
                                                } else {
                                                    Log.e("MainActivity", "서버로 데이터 전송이 실패했습니다.");
                                                }
                                            }

                                            public void onFailure(Call<Void> call, Throwable t) {
                                                Log.e("MainActivity", "서버 통신 중 에러가 발생했습니다.", t);
                                            }
                                        });
                                        datasave();
                                        Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                                        // Intent 시작
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(JoinActivity.this, "비밀번호 일치하지 않음", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    }
                }

            }
        });
    }
    private void datasave(){
        if (joinsucess) {

            Toast.makeText(JoinActivity.this, "데이터 저장 완료", Toast.LENGTH_SHORT).show();

        }
    }

}
