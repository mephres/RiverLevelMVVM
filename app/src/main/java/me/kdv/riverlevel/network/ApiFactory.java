package me.kdv.riverlevel.network;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static me.kdv.riverlevel.util.Util.API_ENDPOINT;

public final class ApiFactory {

    private static OkHttpClient sClient;
    private static volatile ApiRequest sService;

    private ApiFactory() {
    }

    public static ApiRequest getInstance() {
        ApiRequest service = sService;

        if (service == null) {
            synchronized (ApiFactory.class) {
                service = sService;
                if (service == null) {
                    service = sService = buildRetrofit().create(ApiRequest.class);
                }
            }
        }

        return service;
    }

    private static Retrofit buildRetrofit() {

        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
                .client(getClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static OkHttpClient getClient() {
        OkHttpClient client = sClient;

        if (client == null) {
            synchronized (ApiFactory.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = buildClient();
                }
            }
        }
        return client;
    }

    private static OkHttpClient buildClient() {
        OkHttpClient okHttpClient = new OkHttpClient();

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        okHttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Content-Type",
                        "application/x-www-form-urlencoded; charset=utf-8").build();

                return chain.proceed(request);
            }
        });
        okHttpClientBuilder.addNetworkInterceptor(
                new Interceptor() {
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request original = chain.request();

                        return chain.proceed(original);
                    }
                });

        okHttpClientBuilder.connectionPool(new ConnectionPool(50, 5L, TimeUnit.MINUTES));
        okHttpClient = okHttpClientBuilder
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES).build();


        return okHttpClient;
    }
}

