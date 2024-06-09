package com.ryansatj.devkill_android.request;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl, Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient(context))
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(getCustomGson()))
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient okHttpClient(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(new CookiesInterceptor(sharedPreferences))
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request newRequest = originalRequest.newBuilder()
                            .addHeader("Ryan-Safa", "changemepls")
                            .build();
                    return chain.proceed(newRequest);
                })
                .build();
    }

    private static Gson getCustomGson() {
        return new GsonBuilder()
                .setDateFormat("MMMM dd, yyyy hh:mm:ss")
                .create();
    }
}
