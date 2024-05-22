package com.example.happy_app.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_app.R;
import com.example.happy_app.api.ApiClient;
import com.example.happy_app.api.ApiService;
import com.example.happy_app.dto.OrderRequestDto;
import com.example.happy_app.dto.OrderResponseDto;
import com.example.happy_app.dto.ParcelDto;
import com.example.happy_app.utils.BarcodeUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<OrderResponseDto> orderList = new ArrayList<>();

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderResponseDto order = orderList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void setOrderList(List<OrderResponseDto> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewOrderId;
        private final TextView textViewOrderDate;
        private final TextView textViewItemName;
        private final TextView textViewItemCount;
        private final TextView textViewOrderPrice;
        private final TextView textViewOrderStatus;
        private final Button buttonBuyNow;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderId = itemView.findViewById(R.id.textViewOrderId);
            textViewOrderDate = itemView.findViewById(R.id.textViewOrderDate);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            textViewItemCount = itemView.findViewById(R.id.textViewItemCount);
            textViewOrderPrice = itemView.findViewById(R.id.textViewOrderPrice);
            textViewOrderStatus = itemView.findViewById(R.id.textViewOrderStatus);
            buttonBuyNow = itemView.findViewById(R.id.buttonBuyNow);

            buttonBuyNow.setOnClickListener(v -> fetchBarcode(getOrderIdFromText()));
        }

        @SuppressLint("SetTextI18n")
        public void bind(OrderResponseDto order) {
            textViewOrderId.setText("주문 번호: " + order.getOrderId());
            textViewOrderDate.setText("주문 날짜: " + order.getOrderDate());
            textViewItemName.setText("상품 이름: " + order.getItemName());
            textViewItemCount.setText("개수: " + order.getCount());
            textViewOrderPrice.setText("주문 가격: " + order.getOrderPrice() + " ₩");
            textViewOrderStatus.setText("주문 상태: " + order.getOrderStatus());
        }

        private long getOrderIdFromText() {
            return Long.parseLong(textViewOrderId.getText().toString().replace("주문 번호: ", ""));
        }

        private void fetchBarcode(long orderId) {
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<ParcelDto> call = apiService.getParcelTrackingNumber(new OrderRequestDto(orderId));

            call.enqueue(new Callback<ParcelDto>() {
                @Override
                public void onResponse(Call<ParcelDto> call, Response<ParcelDto> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String trackingNumber = response.body().getTrackingNumber();
                        showBarcodeDialog(trackingNumber);
                    } else {
                        showToast("Failed to load barcode");
                    }
                }

                @Override
                public void onFailure(Call<ParcelDto> call, Throwable t) {
                    showToast("Error fetching barcode: " + t.getMessage());
                }
            });
        }

        private void showBarcodeDialog(String trackingNumber) {
            Context context = itemView.getContext();
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_barcode);

            ImageView barcodeImageView = dialog.findViewById(R.id.barcodeImageView);
            TextView trackingNumberTextView = dialog.findViewById(R.id.trackingNumberTextView);

            Bitmap barcodeBitmap = BarcodeUtils.generateBarcode(trackingNumber);
            barcodeImageView.setImageBitmap(barcodeBitmap);

            trackingNumberTextView.setText("송장 번호: " + trackingNumber);

            dialog.setOnDismissListener(dialogInterface -> barcodeImageView.setImageBitmap(null));
            dialog.show();
        }

        private void showToast(String message) {
            Toast.makeText(itemView.getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}
