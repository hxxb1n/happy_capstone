package com.example.happy_app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_app.adapter.ProductAdapter;
import com.example.happy_app.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends Fragment {

    private OnProductSelectedListener listener;

    public interface OnProductSelectedListener {
        void onProductSelected(Product product);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnProductSelectedListener) {
            listener = (OnProductSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnProductSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ProductAdapter adapter = new ProductAdapter(getDummyProducts(), getContext(), listener);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private List<Product> getDummyProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("상품 1", "상품 1 설명", 10000));
        products.add(new Product("상품 2", "상품 2 설명", 20000));
        products.add(new Product("상품 3", "상품 3 설명", 30000));
        return products;
    }
}
