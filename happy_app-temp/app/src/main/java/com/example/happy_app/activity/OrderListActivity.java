package com.example.happy_app.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_app.R;
import com.example.happy_app.adapter.OrderAdapter;
import com.example.happy_app.dto.MemberRequestDto;
import com.example.happy_app.dto.OrderResponseDto;
import com.example.happy_app.api.ApiClient;
import com.example.happy_app.api.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListActivity extends AppCompatActivity {

    private static final String TAG = "OrderListActivity";

    private RecyclerView recyclerViewOrderList;
    private long memberId;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        initViews();
        memberId = getIntent().getLongExtra("memberId", -1);

        if (memberId == -1) {
            showToast("Invalid member ID");
            finish();
            return;
        }

        setupRecyclerView();
        fetchOrders();
    }

    private void initViews() {
        recyclerViewOrderList = findViewById(R.id.recyclerViewOrderList);
    }

    private void setupRecyclerView() {
        recyclerViewOrderList.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter();
        recyclerViewOrderList.setAdapter(orderAdapter);
    }

    private void fetchOrders() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        MemberRequestDto memberRequestDto = new MemberRequestDto();
        memberRequestDto.setMemberId(memberId);

        Call<List<OrderResponseDto>> call = apiService.getOrders(memberRequestDto);
        call.enqueue(new Callback<List<OrderResponseDto>>() {
            @Override
            public void onResponse(Call<List<OrderResponseDto>> call, Response<List<OrderResponseDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orderAdapter.setOrderList(response.body());
                } else {
                    showToast("Failed to load orders");
                    Log.e(TAG, "Response unsuccessful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<OrderResponseDto>> call, Throwable t) {
                Log.e(TAG, "Error fetching orders", t);
                showToast("Error fetching orders: " + t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(OrderListActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
