package com.example.happy_app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_app.adapter.ProductAdapter;
import com.example.happy_app.api.ApiClient;
import com.example.happy_app.api.ProductApi;
import com.example.happy_app.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListFragment extends Fragment {

    private static final String TAG = "ProductListFragment";

    private OnProductSelectedListener listener;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;

    public interface OnProductSelectedListener {
        void onProductSelected(Product product);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnProductSelectedListener) {
            listener = (OnProductSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        initViews(view);
        setupRecyclerView();
        loadProducts();
        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewProducts);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadProducts() {
        ProductApi apiService = ApiClient.getProductApiService();
        Call<List<Product>> call = apiService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = response.body();
                    adapter = new ProductAdapter(products, getContext(), listener);
                    recyclerView.setAdapter(adapter);
                } else {
                    showToast("상품 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                showToast("서버 연결 실패");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}