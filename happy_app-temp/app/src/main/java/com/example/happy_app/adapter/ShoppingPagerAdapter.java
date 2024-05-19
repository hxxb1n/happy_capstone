package com.example.happy_app.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.happy_app.ProductDetailFragment;
import com.example.happy_app.ProductListFragment;

public class ShoppingPagerAdapter extends FragmentStateAdapter {

    private static final int TAB_COUNT = 2;
    private final long memberId;

    public ShoppingPagerAdapter(@NonNull FragmentActivity fragmentActivity, long memberId) {
        super(fragmentActivity);
        this.memberId = memberId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new ProductListFragment();
        } else {
            ProductDetailFragment fragment = new ProductDetailFragment();
            Bundle args = new Bundle();
            args.putLong("memberId", memberId);
            fragment.setArguments(args);
            return fragment;
        }
    }

    @Override
    public int getItemCount() {
        return TAB_COUNT;
    }
}
