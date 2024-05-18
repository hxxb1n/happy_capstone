package com.example.happy_app.api;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.Call;

public interface MemberApi {

    @POST("/members/join")
    Call<CreateMemberResponse> joinMember(@Body CreateMemberRequest request);

    @POST("/members/login")
    Call<CreateMemberResponse> login(@Body CreateMemberRequest request);

}
