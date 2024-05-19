package com.example.happy_app.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://10.0.2.2:8080";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static MemberApi getApiService() {
        return getRetrofitInstance().create(MemberApi.class);
    }

    public static ProductApi getProductApiService() {
        return getRetrofitInstance().create(ProductApi.class);
    }

    public static OrderApi getOrderApiService() {
        return getRetrofitInstance().create(OrderApi.class);
    }

    public static ProfileApi getProfileApiService() {
        return getRetrofitInstance().create(ProfileApi.class);
    }

    public static MemberApi getMemberApiService() {
        return getRetrofitInstance().create(MemberApi.class);
    }

}
