package com.example.happy_app.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.happy_app.ProductDetailFragment;
import com.example.happy_app.ProductListFragment;

import java.util.HashMap;
import java.util.Map;

public class ShoppingPagerAdapter extends FragmentStateAdapter {

    private final Map<Integer, Fragment> fragmentMap = new HashMap<>();

    public ShoppingPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragmentMap.put(0, new ProductListFragment());
        fragmentMap.put(1, new ProductDetailFragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentMap.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentMap.size();
    }

    public void setFragment(int position, Fragment fragment) {
        fragmentMap.put(position, fragment);
        notifyItemChanged(position);
    }
}
