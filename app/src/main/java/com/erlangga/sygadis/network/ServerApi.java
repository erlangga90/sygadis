package com.erlangga.sygadis.network;

import com.erlangga.sygadis.admin.PresensiRequest;
import com.erlangga.sygadis.admin.PresensiResponse;
import com.erlangga.sygadis.login.LoginRequest;
import com.erlangga.sygadis.login.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServerApi {

    @POST("loginScanner/index_post")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("Presensi/index_post")
    Call<PresensiResponse> presensi(@Body PresensiRequest presensiRequest);

}


