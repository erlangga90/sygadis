package com.erlangga.sygadis.network;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public enum ServerManager {

    INSTANCE;

    // Ubah sesuai dengan IP Address laptop
    private static final String BASE_URL = "http://192.168.0.196/Proyek/";

    private Retrofit retrofit = new retrofit2.Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(
            new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
        ))
        .build();
    final ServerApi serverApi = retrofit.create(ServerApi.class);

    public ServerApi getServerApi() {
        return serverApi;
    }

}
