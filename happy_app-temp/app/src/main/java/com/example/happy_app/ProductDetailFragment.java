package com.example.happy_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.happy_app.activity.ShoppingActivity;
import com.example.happy_app.api.ApiClient;
import com.example.happy_app.api.OrderApi;
import com.example.happy_app.api.ProductApi;
import com.example.happy_app.model.OrderDto;
import com.example.happy_app.model.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailFragment extends Fragment {

    private TextView textViewProductName;
    private TextView textViewProductDescription;
    private TextView textViewProductPrice;
    private EditText editTextProductQuantity;
    private Button buttonAddToCart;
    private Button buttonBuyNow;
    private long productId;
    private long memberId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        textViewProductName = view.findViewById(R.id.textViewProductName);
        textViewProductDescription = view.findViewById(R.id.textViewProductDescription);
        textViewProductPrice = view.findViewById(R.id.textViewProductPrice);
        editTextProductQuantity = view.findViewById(R.id.editTextProductQuantity);
        buttonAddToCart = view.findViewById(R.id.buttonAddToCart);
        buttonBuyNow = view.findViewById(R.id.buttonBuyNow);

        if (getArguments() != null) {
            memberId = getArguments().getLong("memberId", -1);
            productId = getArguments().getLong("productId", -1);
            if (productId != -1 && memberId != -1) {
                loadProductDetails(productId);
            }
        }

        buttonAddToCart.setOnClickListener(v -> addToCart());
        buttonBuyNow.setOnClickListener(v -> buyNow());

        return view;
    }

    public void updateProductDetails(long productId) {
        this.productId = productId;
        loadProductDetails(productId);
    }

    private void loadProductDetails(long productId) {
        ProductApi apiService = ApiClient.getProductApiService();
        Call<Product> call = apiService.getProductById(productId);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Product product = response.body();
                    textViewProductName.setText(product.getName());
                    textViewProductDescription.setText(product.getDescription());
                    textViewProductPrice.setText(String.format("%s ₩", product.getPrice()));
                } else {
                    Toast.makeText(getContext(), "Failed to load product details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToCart() {
        String quantityStr = editTextProductQuantity.getText().toString();
        if (quantityStr.isEmpty()) {
            Toast.makeText(getContext(), "수량을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity = Integer.parseInt(quantityStr);
        Toast.makeText(getContext(), "장바구니에 추가되었습니다.", Toast.LENGTH_SHORT).show();
    }

    private void buyNow() {
        String quantityStr = editTextProductQuantity.getText().toString();
        if (quantityStr.isEmpty()) {
            Toast.makeText(getContext(), "수량을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity = Integer.parseInt(quantityStr);
        OrderDto orderDto = new OrderDto(memberId, productId, quantity);

        OrderApi apiService = ApiClient.getOrderApiService();
        Call<String> call = apiService.createOrder(orderDto);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && "1".equals(response.body())) {
                    Toast.makeText(getContext(), "주문이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                    // 쇼핑액티비티를 다시 호출
                    Intent intent = new Intent(getActivity(), ShoppingActivity.class);
                    intent.putExtra("memberId", memberId);
                    intent.putExtra("memberName", getArguments().getString("memberName"));
                    startActivity(intent);

                    editTextProductQuantity.setText("");

                } else {
                    Toast.makeText(getContext(), "내 정보에서 먼저 주소를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), "서버 연결 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
