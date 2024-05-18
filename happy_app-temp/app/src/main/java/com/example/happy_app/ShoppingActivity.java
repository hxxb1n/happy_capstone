package com.example.happy_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingActivity extends AppCompatActivity {

    private TextView textViewMemberName;
    private RecyclerView recyclerViewProducts;
    private ProductAdapter productAdapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        textViewMemberName = findViewById(R.id.textViewMemberName);
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);

        String memberName = getIntent().getStringExtra("memberName");
        textViewMemberName.setText(memberName + "님, 환영합니다!");

        // 상품 목록 설정
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter(getDummyProducts());
        recyclerViewProducts.setAdapter(productAdapter);
    }

    // 임시 상품 목록 생성
    private List<Product> getDummyProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("상품 1", "상품 1 설명", 10000));
        products.add(new Product("상품 2", "상품 2 설명", 20000));
        products.add(new Product("상품 3", "상품 3 설명", 30000));
        return products;
    }
}
