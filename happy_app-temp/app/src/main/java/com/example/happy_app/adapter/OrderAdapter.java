package com.example.happy_app.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
        private final Button buttonOrderCancel;
        private final LinearLayout orderItemLayout;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderId = itemView.findViewById(R.id.textViewOrderId);
            textViewOrderDate = itemView.findViewById(R.id.textViewOrderDate);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            textViewItemCount = itemView.findViewById(R.id.textViewItemCount);
            textViewOrderPrice = itemView.findViewById(R.id.textViewOrderPrice);
            textViewOrderStatus = itemView.findViewById(R.id.textViewOrderStatus);
            buttonBuyNow = itemView.findViewById(R.id.buttonBuyNow);
            buttonOrderCancel = itemView.findViewById(R.id.buttonOrderCancel);
            orderItemLayout = itemView.findViewById(R.id.orderItemLayout);

            buttonBuyNow.setOnClickListener(v -> fetchBarcode(getOrderIdFromText()));
            buttonOrderCancel.setOnClickListener(v -> showCancelConfirmationDialog(getOrderIdFromText()));
        }

        @SuppressLint("SetTextI18n")
        public void bind(OrderResponseDto order) {
            textViewOrderId.setText("주문 번호: " + order.getOrderId());
            textViewOrderDate.setText("주문 날짜: " + order.getOrderDate());
            textViewItemName.setText("상품 이름: " + order.getItemName());
            textViewItemCount.setText("개수: " + order.getCount());
            textViewOrderPrice.setText("주문 가격: " + order.getOrderPrice() + " ₩");
            textViewOrderStatus.setText("주문 상태: " + order.getOrderStatus());

            if (order.getOrderStatus().equals("CANCEL")) {
                orderItemLayout.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.colorCancel));
                buttonBuyNow.setVisibility(View.GONE);
                buttonOrderCancel.setVisibility(View.GONE);
            } else if (order.getOrderStatus().equals("DELIVERED")) {
                orderItemLayout.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.colorComplete));
                buttonBuyNow.setVisibility(View.GONE);
                buttonOrderCancel.setVisibility(View.GONE);
            } else {
                orderItemLayout.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
                buttonBuyNow.setVisibility(View.VISIBLE);
                buttonOrderCancel.setVisibility(View.VISIBLE);
            }

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
                        showToast("바코드 불러오기 실패");
                    }
                }

                @Override
                public void onFailure(Call<ParcelDto> call, Throwable t) {
                    showToast("요청 지연: " + t.getMessage());
                }
            });
        }

        private void showCancelConfirmationDialog(long orderId) {
            Context context = itemView.getContext();
            Dialog dialog = new Dialog(context, R.style.CustomDialog);
            dialog.setContentView(R.layout.dialog_custom_confirmation);
            Button buttonYes = dialog.findViewById(R.id.buttonYes);
            Button buttonNo = dialog.findViewById(R.id.buttonNo);
            buttonYes.setOnClickListener(v -> {
                cancelOrder(orderId);
                dialog.dismiss();
            });
            buttonNo.setOnClickListener(v -> dialog.dismiss());
            dialog.show();
        }

        private void cancelOrder(long orderId) {
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            Call<String> call = apiService.cancelOrder(orderId);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful() && "1".equals(response.body())) {
                        textViewOrderStatus.setText("주문 상태: CANCEL");
                        orderItemLayout.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.colorCancel));
                        buttonBuyNow.setVisibility(View.GONE);
                        buttonOrderCancel.setVisibility(View.GONE);
                        showToast("주문을 취소했어요.");
                    } else {
                        showToast("이미 배송이 완료된 주문이에요.");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    showToast("주문 취소 오류: " + t.getMessage());
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
