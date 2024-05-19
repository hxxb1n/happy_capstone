package com.example.happy_app.api;

import com.example.happy_app.model.Member;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.Call;
import retrofit2.http.Path;

public interface MemberApi {

    @POST("/api/members/join")
    Call<CreateMemberResponse> joinMember(@Body CreateMemberRequest request);

    @POST("/api/members/login")
    Call<CreateMemberResponse> login(@Body CreateMemberRequest request);

    @GET("/api/members/{memberId}")
    Call<Member> getMemberById(@Path("memberId") long memberId);

}
