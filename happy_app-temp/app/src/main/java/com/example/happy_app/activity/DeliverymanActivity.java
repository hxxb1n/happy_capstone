package com.example.happy_app.activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_app.R;
import com.example.happy_app.adapter.DeliveryAdapter;
import com.example.happy_app.dto.DeliveryResponseDto;
import com.example.happy_app.api.ApiClient;
import com.example.happy_app.api.DeliveryApi;
import com.example.happy_app.utils.BarcodeUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliverymanActivity extends AppCompatActivity {

    private static final String TAG = "DeliverymanActivity";

    private RecyclerView recyclerViewOrderList;
    private DeliveryAdapter deliveryAdapter;
    private Handler handler = new Handler();
    private Runnable fetchOrdersRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveryman);

        initViews();
        setupRecyclerView();
        setupFetchOrdersRunnable();
        startFetchingOrders();
    }

    private void initViews() {
        recyclerViewOrderList = findViewById(R.id.recyclerViewOrderList);
    }

    private void setupRecyclerView() {
        recyclerViewOrderList.setLayoutManager(new LinearLayoutManager(this));
        deliveryAdapter = new DeliveryAdapter(this::showBarcodeDialog);
        recyclerViewOrderList.setAdapter(deliveryAdapter);
    }

    private void setupFetchOrdersRunnable() {
        fetchOrdersRunnable = new Runnable() {
            @Override
            public void run() {
                fetchOrders();
                handler.postDelayed(this, 2000);
            }
        };
    }

    private void startFetchingOrders() {
        handler.post(fetchOrdersRunnable);
    }

    private void stopFetchingOrders() {
        handler.removeCallbacks(fetchOrdersRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopFetchingOrders();
    }

    private void fetchOrders() {
        DeliveryApi apiService = ApiClient.getDeliveryApiService();

        Call<List<DeliveryResponseDto>> call = apiService.getAllDeliveryOrders();
        call.enqueue(new Callback<List<DeliveryResponseDto>>() {
            @Override
            public void onResponse(Call<List<DeliveryResponseDto>> call, Response<List<DeliveryResponseDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    deliveryAdapter.setOrderList(response.body());
                } else {
                    showToast("주문정보 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<List<DeliveryResponseDto>> call, Throwable t) {
                showToast("오류 메세지: " + t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(DeliverymanActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void showBarcodeDialog(String trackingNumber) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_barcode);

        ImageView barcodeImageView = dialog.findViewById(R.id.barcodeImageView);
        TextView trackingNumberTextView = dialog.findViewById(R.id.trackingNumberTextView);

        Bitmap barcodeBitmap = BarcodeUtils.generateBarcode(trackingNumber);
        barcodeImageView.setImageBitmap(barcodeBitmap);

        trackingNumberTextView.setText("송장번호: " + trackingNumber);

        dialog.setOnDismissListener(dialogInterface -> barcodeImageView.setImageBitmap(null));
        dialog.show();
    }
}
