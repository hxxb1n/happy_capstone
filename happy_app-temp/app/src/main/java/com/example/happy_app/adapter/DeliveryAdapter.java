package com.example.happy_app.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_app.R;
import com.example.happy_app.dto.DeliveryResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.OrderViewHolder> {

    private List<DeliveryResponseDto> orderList = new ArrayList<>();
    private final OnBarcodeButtonClickListener barcodeButtonClickListener;

    public interface OnBarcodeButtonClickListener {
        void onBarcodeButtonClick(String trackingNumber);
    }

    public DeliveryAdapter(OnBarcodeButtonClickListener barcodeButtonClickListener) {
        this.barcodeButtonClickListener = barcodeButtonClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_order, parent, false);
        return new OrderViewHolder(view, barcodeButtonClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        DeliveryResponseDto order = orderList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void setOrderList(List<DeliveryResponseDto> newOrderList) {
        if (!Objects.equals(orderList, newOrderList)) {
            orderList = newOrderList;
            notifyDataSetChanged();
        }
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewOrderId;
        private final TextView textViewCity;
        private final TextView textViewStreet;
        private final TextView textViewZip;
        private final TextView textViewStatus;
        private final Button buttonViewBarcode;
        private final LinearLayout orderItemLayout;
        private String trackingNumber;

        public OrderViewHolder(@NonNull View itemView, OnBarcodeButtonClickListener barcodeButtonClickListener) {
            super(itemView);
            textViewOrderId = itemView.findViewById(R.id.textViewOrderId);
            textViewCity = itemView.findViewById(R.id.textViewCity);
            textViewStreet = itemView.findViewById(R.id.textViewStreet);
            textViewZip = itemView.findViewById(R.id.textViewZip);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            buttonViewBarcode = itemView.findViewById(R.id.buttonViewBarcode);
            orderItemLayout = itemView.findViewById(R.id.orderItemLayout);

            buttonViewBarcode.setOnClickListener(v -> {
                if (trackingNumber != null) {
                    barcodeButtonClickListener.onBarcodeButtonClick(trackingNumber);
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void bind(DeliveryResponseDto order) {
            textViewOrderId.setText("주문 번호: " + order.getOrderId());
            textViewCity.setText("주소: " + order.getCity());
            textViewStreet.setText("상세주소: " + order.getStreet());
            textViewZip.setText("우편번호: " + order.getZip());
            textViewStatus.setText("상태: " + order.getStatus());
            trackingNumber = order.getTrackingNumber();

            if (order.getStatus().equals("CANCEL")) {
                orderItemLayout.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.colorCancel));
                buttonViewBarcode.setVisibility(View.GONE);
            } else if (order.getStatus().equals("DELIVERED")) {
                orderItemLayout.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.colorComplete));
                buttonViewBarcode.setVisibility(View.GONE);
            } else {
                orderItemLayout.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
                buttonViewBarcode.setVisibility(View.VISIBLE);
            }
        }
    }
}
