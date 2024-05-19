package com.example.happy_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_app.R;
import com.example.happy_app.api.ApiClient;
import com.example.happy_app.api.ProfileApi;
import com.example.happy_app.model.Address;
import com.example.happy_app.model.ProfileUpdateRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAddressActivity extends AppCompatActivity {

    private EditText editTextCity, editTextStreet, editTextZip;
    private Button buttonSubmitAddress;
    private long memberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);

        editTextCity = findViewById(R.id.editTextCity);
        editTextStreet = findViewById(R.id.editTextStreet);
        editTextZip = findViewById(R.id.editTextZip);
        buttonSubmitAddress = findViewById(R.id.buttonSubmitAddress);

        // Intent로부터 유저 정보 받기
        memberId = getIntent().getLongExtra("memberId", -1);

        buttonSubmitAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAddress();
            }
        });
    }

    private void updateAddress() {
        String city = editTextCity.getText().toString();
        String street = editTextStreet.getText().toString();
        String zip = editTextZip.getText().toString();

        if (city.isEmpty() || street.isEmpty() || zip.isEmpty()) {
            Toast.makeText(this, "모든 필드를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        Address address = new Address(city, street, zip);
        ProfileUpdateRequest request = new ProfileUpdateRequest(memberId, address);

        ProfileApi apiService = ApiClient.getProfileApiService();
        Call<Void> call = apiService.updateAddress(request);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdateAddressActivity.this, "주소 업데이트 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateAddressActivity.this, ShoppingActivity.class);
                    intent.putExtra("memberId", memberId);
                    startActivity(intent);
                    finish(); // Update success, close this activity and return to profile
                } else {
                    Toast.makeText(UpdateAddressActivity.this, "주소 업데이트 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UpdateAddressActivity.this, "서버 연결 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
