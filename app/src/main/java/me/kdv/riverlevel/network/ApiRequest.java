package me.kdv.riverlevel.network;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRequest {
    @GET("urovni-rek")
    Call<String> getRiverLevel();
}
