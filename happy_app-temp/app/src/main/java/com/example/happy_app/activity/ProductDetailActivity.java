package com.example.happy_app.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.happy_app.R;

public class ProductDetailActivity extends AppCompatActivity {

    private TextView textViewProductName;
    private TextView textViewProductDescription;
    private TextView textViewProductPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("상품 상세 정보");
        }

        textViewProductName = findViewById(R.id.textViewProductName);
        textViewProductDescription = findViewById(R.id.textViewProductDescription);
        textViewProductPrice = findViewById(R.id.textViewProductPrice);

        String productName = getIntent().getStringExtra("productName");
        String productDescription = getIntent().getStringExtra("productDescription");
        int productPrice = getIntent().getIntExtra("productPrice", 0);

        textViewProductName.setText(productName);
        textViewProductDescription.setText(productDescription);
        textViewProductPrice.setText(String.valueOf(productPrice));
    }

    // 뒤로가기 버튼
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
