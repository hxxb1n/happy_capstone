package com.example.happy_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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

    private static final String TAG = "ShoppingActivity";

    private TextView textViewMemberName;
    private Button buttonProfile;
    private Button buttonOrderList;
    private ViewPager2 viewPager;
    private ShoppingPagerAdapter pagerAdapter;
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

        initViews();
        memberId = getIntent().getLongExtra("memberId", -1);

        if (memberId == -1) {
            finish();
            return;
        }

        fetchMemberDetails(memberId);
    }

    private void initViews() {
        textViewMemberName = findViewById(R.id.textViewMemberName);
        buttonProfile = findViewById(R.id.buttonProfile);
        buttonOrderList = findViewById(R.id.buttonOrderList);
        viewPager = findViewById(R.id.viewPager);
    }

    private void fetchMemberDetails(long memberId) {
        MemberApi apiService = ApiClient.getMemberApiService();
        Call<Member> call = apiService.getMemberById(memberId);

        call.enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                if (response.isSuccessful() && response.body() != null) {
                    handleMemberResponse(response.body());
                } else {
                    finish();
                }
            }
            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                finish();
            }
        });
    }

    private void handleMemberResponse(Member member) {
        Address address = member.getAddress();
        memberName = member.getName();
        memberCity = (address != null && address.getCity() != null) ? address.getCity() : "N/A";
        memberStreet = (address != null && address.getStreet() != null) ? address.getStreet() : "N/A";
        memberZip = (address != null && address.getZip() != null) ? address.getZip() : "N/A";
        memberAuthority = member.getAuthority();

        textViewMemberName.setText(memberName + "님, 환영합니다!");

        setupViewPager();
        setupButtons();
    }

    private void setupViewPager() {
        pagerAdapter = new ShoppingPagerAdapter(this, memberId);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(position == 0 ? "제품" : "상세정보");
        }).attach();
    }

    private void setupButtons() {
        buttonProfile.setOnClickListener(v -> navigateToProfileActivity());
        buttonOrderList.setOnClickListener(v -> navigateToOrderListActivity());
    }

    private void navigateToProfileActivity() {
        Intent intent = new Intent(ShoppingActivity.this, ProfileActivity.class);
        intent.putExtra("memberName", memberName);
        intent.putExtra("memberId", memberId);
        intent.putExtra("memberCity", memberCity);
        intent.putExtra("memberStreet", memberStreet);
        intent.putExtra("memberZip", memberZip);
        intent.putExtra("memberAuthority", memberAuthority);
        startActivity(intent);
    }

    private void navigateToOrderListActivity() {
        Intent intent = new Intent(ShoppingActivity.this, OrderListActivity.class);
        intent.putExtra("memberId", memberId);
        startActivity(intent);
    }

    @Override
    public void onProductSelected(Product product) {
        updateProductDetailFragment(product.getId());
        viewPager.setCurrentItem(1);
    }

    private void updateProductDetailFragment(long productId) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("f1");
        if (fragment instanceof ProductDetailFragment) {
            ((ProductDetailFragment) fragment).updateProductDetails(productId);
        }
    }
}
