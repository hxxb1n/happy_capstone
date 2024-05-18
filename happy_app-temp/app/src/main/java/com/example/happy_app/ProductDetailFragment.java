package com.example.happy_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.happy_app.R;

public class ProductDetailFragment extends Fragment {

    private TextView textViewProductName;
    private TextView textViewProductDescription;
    private TextView textViewProductPrice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        textViewProductName = view.findViewById(R.id.textViewProductName);
        textViewProductDescription = view.findViewById(R.id.textViewProductDescription);
        textViewProductPrice = view.findViewById(R.id.textViewProductPrice);

        // 데이터 가져오기 및 설정
        if (getArguments() != null) {
            String productName = getArguments().getString("productName");
            String productDescription = getArguments().getString("productDescription");
            int productPrice = getArguments().getInt("productPrice");
            textViewProductName.setText(productName);
            textViewProductDescription.setText(productDescription);
            textViewProductPrice.setText(String.valueOf(productPrice));
        }

        return view;
    }

    public void updateProductDetails(String name, String description, int price) {
        textViewProductName.setText(name);
        textViewProductDescription.setText(description);
        textViewProductPrice.setText(String.valueOf(price));
    }
}
