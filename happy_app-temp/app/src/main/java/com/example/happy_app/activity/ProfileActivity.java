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

    private static final String TAG = "ProfileActivity";

    private TextView textViewProfile;
    private Button buttonUpdateAddress;
    private Button buttonLogout;
    private long memberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initViews();
        memberId = getIntent().getLongExtra("memberId", -1);

        if (memberId == -1) {
            showToast("세션 처리 실패");
            finish();
            return;
        }

        loadMemberDetails(memberId);
        setupListeners();
    }

    private void initViews() {
        textViewProfile = findViewById(R.id.textViewProfile);
        buttonUpdateAddress = findViewById(R.id.buttonUpdateAddress);
        buttonLogout = findViewById(R.id.buttonLogout);
    }

    private void setupListeners() {
        buttonUpdateAddress.setOnClickListener(v -> navigateToUpdateAddressActivity());
        buttonLogout.setOnClickListener(v -> logout());
    }

    private void loadMemberDetails(long memberId) {
        MemberApi apiService = ApiClient.getMemberApiService();
        Call<Member> call = apiService.getMemberById(memberId);
        call.enqueue(new Callback<Member>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                if (response.isSuccessful() && response.body() != null) {
                    handleMemberResponse(response.body());
                } else {
                    showToast("사용자 정보 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                handleLoadMemberFailure(t);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void handleMemberResponse(Member member) {
        Address address = member.getAddress();
        String city = address != null && address.getCity() != null ? address.getCity() : "주소가 없어요.";
        String street = address != null && address.getStreet() != null ? address.getStreet() : "";
        String zip = address != null && address.getZip() != null ? address.getZip() : "";

        textViewProfile.setText("회원 이름: " + member.getName() +
                "\n아이디: 0" + member.getId() +
                "\n도시: " + city +
                "\n세부주소: " + street +
                "\n우편번호: " + zip +
                "\n권한: " + member.getAuthority());
    }

    private void handleLoadMemberFailure(Throwable t) {
        showToast("서버 연결 실패");
    }

    private void navigateToUpdateAddressActivity() {
        Intent intent = new Intent(ProfileActivity.this, UpdateAddressActivity.class);
        intent.putExtra("memberId", memberId);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void logout() {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        showToast("로그아웃 성공");
    }

}
