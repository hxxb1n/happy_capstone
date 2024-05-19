package com.example.happy_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.happy_app.ProductDetailFragment;
import com.example.happy_app.ProductListFragment;
import com.example.happy_app.R;
import com.example.happy_app.adapter.ShoppingPagerAdapter;
import com.example.happy_app.model.Address;
import com.example.happy_app.model.Product;
import com.example.happy_app.model.Member;
import com.example.happy_app.api.ApiClient;
import com.example.happy_app.api.MemberApi;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingActivity extends AppCompatActivity implements ProductListFragment.OnProductSelectedListener {

    private TextView textViewMemberName;
    private Button buttonProfile;
    private Button buttonOrderList;
    private ViewPager2 viewPager;
    private ShoppingPagerAdapter pagerAdapter;
    private ProductDetailFragment productDetailFragment;
    private long memberId;
    private String memberName;
    private String memberCity;
    private String memberStreet;
    private String memberZip;
    private String memberAuthority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        textViewMemberName = findViewById(R.id.textViewMemberName);
        buttonProfile = findViewById(R.id.buttonProfile);
        buttonOrderList = findViewById(R.id.buttonOrderList);
        viewPager = findViewById(R.id.viewPager);

        // Intent로부터 유저 ID 받기
        memberId = getIntent().getLongExtra("memberId", -1);

        // 서버에서 유저 정보 조회
        fetchMemberDetails(memberId);
    }

    private void fetchMemberDetails(long memberId) {
        MemberApi apiService = ApiClient.getMemberApiService();
        Call<Member> call = apiService.getMemberById(memberId);

        call.enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Member member = response.body();
                    Address address = member.getAddress();
                    memberName = member.getName();
                    memberCity = address.getCity();
                    memberStreet = address.getStreet();
                    memberZip = address.getZip();
                    memberAuthority = member.getAuthority();

                    textViewMemberName.setText(memberName + "님, 환영합니다!");

                    setupViewPager();
                    setupButtons();
                }
            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                // 에러 처리
            }
        });
    }

    private void setupViewPager() {
        productDetailFragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putLong("memberId", memberId);
        productDetailFragment.setArguments(args);

        pagerAdapter = new ShoppingPagerAdapter(this, productDetailFragment);
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

    private void setupButtons() {
        // 내 정보 보기 버튼 클릭 리스너 설정
        buttonProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ShoppingActivity.this, ProfileActivity.class);
            intent.putExtra("memberName", memberName);
            intent.putExtra("memberId", memberId);
            intent.putExtra("memberCity", memberCity);
            intent.putExtra("memberStreet", memberStreet);
            intent.putExtra("memberZip", memberZip);
            intent.putExtra("memberAuthority", memberAuthority);
            startActivity(intent);
        });

        // 주문 목록 보기 버튼 클릭 리스너 설정
        buttonOrderList.setOnClickListener(v -> {
            Intent intent = new Intent(ShoppingActivity.this, OrderListActivity.class);
            intent.putExtra("memberId", memberId);
            startActivity(intent);
        });
    }

    @Override
    public void onProductSelected(Product product) {
        Bundle bundle = new Bundle();
        bundle.putLong("productId", product.getId());
        bundle.putLong("memberId", memberId);
        productDetailFragment.setArguments(bundle);
        productDetailFragment.loadProductDetails(product.getId());
        productDetailFragment.updateProductDetails(product.getId());
        viewPager.setCurrentItem(1);
    }
}
