package com.example.happy_app.activity;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.happy_app.model.Product;
import com.example.happy_app.ProductDetailFragment;
import com.example.happy_app.ProductListFragment;
import com.example.happy_app.R;
import com.example.happy_app.adapter.ShoppingPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ShoppingActivity extends AppCompatActivity implements ProductListFragment.OnProductSelectedListener {

    private TextView textViewMemberName;
    private ViewPager2 viewPager;
    private ShoppingPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        textViewMemberName = findViewById(R.id.textViewMemberName);
        viewPager = findViewById(R.id.viewPager);

        // Intent로부터 유저 정보 받기
        String memberName = getIntent().getStringExtra("memberName");
        textViewMemberName.setText(memberName + "님, 환영합니다!");

        pagerAdapter = new ShoppingPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Products");
            } else {
                tab.setText("Details");
            }
        }).attach();
    }

    @Override
    public void onProductSelected(Product product) {
        Bundle bundle = new Bundle();
        bundle.putString("productName", product.getName());
        bundle.putString("productDescription", product.getDescription());
        bundle.putInt("productPrice", product.getPrice());
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(bundle);
        pagerAdapter.setFragment(1, fragment);
        viewPager.setCurrentItem(1);
    }
}
