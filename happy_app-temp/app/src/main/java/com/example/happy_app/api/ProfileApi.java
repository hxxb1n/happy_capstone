package com.example.happy_app.api;

import com.example.happy_app.dto.ProfileUpdateRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public interface ProfileApi {

    @PUT("/api/members/updateAddress")
    Call<Void> updateAddress(@Body ProfileUpdateRequest request);

}
