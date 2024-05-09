package com.example.happyness;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterAsyncTask extends AsyncTask<User, Void, Void> {

    @Override
    protected Void doInBackground(User... users) {
        if (users.length == 0) return null;
        User user = users[0];

        // Retrofit을 초기화하여 서버로의 요청을 준비
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.175.251:8080/") // 서버 주소로 변경
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // ApiService 인터페이스의 createUser 메서드를 호출하여 서버로 데이터 전송
        ApiService apiService = retrofit.create(ApiService.class);
        try {
            apiService.createUser(user).execute(); // 동기적으로 요청을 보냄
            Log.d("RegisterAsyncTask", "서버로 데이터 전송이 성공했습니다.");
        } catch (IOException e) {
            Log.e("RegisterAsyncTask", "서버 통신 중 에러가 발생했습니다.", e);
        }

        return null;
    }
}
