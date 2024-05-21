package com.example.happy_app.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_app.R;
import com.example.happy_app.dto.OrderResponseDto;

import java.util.ArrayList;
import java.util.List;

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

        private TextView textViewOrderId;
        private TextView textViewOrderDate;
        private TextView textViewItemName;
        private TextView textViewItemCount;
        private TextView textViewOrderPrice;
        private TextView textViewOrderStatus;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderId = itemView.findViewById(R.id.textViewOrderId);
            textViewOrderDate = itemView.findViewById(R.id.textViewOrderDate);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            textViewItemCount = itemView.findViewById(R.id.textViewItemCount);
            textViewOrderPrice = itemView.findViewById(R.id.textViewOrderPrice);
            textViewOrderStatus = itemView.findViewById(R.id.textViewOrderStatus);
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
    }
}
