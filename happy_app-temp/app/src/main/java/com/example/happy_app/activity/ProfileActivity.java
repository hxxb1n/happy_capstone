package com.example.happy_app.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.happy_app.R;
import com.example.happy_app.api.ApiClient;
import com.example.happy_app.api.MemberApi;
import com.example.happy_app.model.Address;
import com.example.happy_app.model.Member;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewProfile;
    private Button buttonUpdateAddress;
    private long memberId;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewProfile = findViewById(R.id.textViewProfile);
        buttonUpdateAddress = findViewById(R.id.buttonUpdateAddress);

        // Intent로부터 유저 ID 받기
        memberId = getIntent().getLongExtra("memberId", -1);

        // 사용자 정보 로드
        loadMemberDetails(memberId);

        // 주소 수정 버튼 클릭 시 UpdateAddressActivity로 이동
        buttonUpdateAddress.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, UpdateAddressActivity.class);
            intent.putExtra("memberId", memberId);
            startActivity(intent);
        });
    }

    public void loadMemberDetails(long memberId) {
        MemberApi apiService = ApiClient.getMemberApiService();
        Call<Member> call = apiService.getMemberById(memberId);
        call.enqueue(new Callback<Member>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Member member = response.body();
                    Address address = member.getAddress();
                    textViewProfile.setText("회원 이름: " + member.getName() +
                            "\n회원 ID: " + member.getId() +
                            "\n도시: " + address.getCity() +
                            "\n거리: " + address.getStreet() +
                            "\n우편번호: " + address.getZip() +
                            "\n권한: " + member.getAuthority());
                } else {
                    Toast.makeText(ProfileActivity.this, "Failed to load member details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
