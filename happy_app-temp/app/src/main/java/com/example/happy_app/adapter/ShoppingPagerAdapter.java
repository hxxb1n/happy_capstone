package com.example.happy_app.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.happy_app.ProductDetailFragment;
import com.example.happy_app.ProductListFragment;

public class ShoppingPagerAdapter extends FragmentStateAdapter {

    private final Fragment[] fragments;

    public ShoppingPagerAdapter(@NonNull FragmentActivity fragmentActivity, ProductDetailFragment productDetailFragment) {
        super(fragmentActivity);
        fragments = new Fragment[] { new ProductListFragment(), productDetailFragment };
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments[position];
    }

    @Override
    public int getItemCount() {
        return fragments.length;
    }
}
