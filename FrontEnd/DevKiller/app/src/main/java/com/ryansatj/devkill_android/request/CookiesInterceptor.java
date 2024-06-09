package com.ryansatj.devkill_android.request;

import android.content.SharedPreferences;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CookiesInterceptor implements Interceptor {
    private SharedPreferences sharedPreferences;

    public CookiesInterceptor(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        // Retrieve cookies from SharedPreferences
        Set<String> cookies = sharedPreferences.getStringSet("cookies", new HashSet<>());
        for (String cookie : cookies) {
            builder.addHeader("Cookie", cookie);
        }

        // Proceed with the request
        Response response = chain.proceed(builder.build());

        // Handle Set-Cookie headers in the response
        if (!response.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookieSet = new HashSet<>(response.headers("Set-Cookie"));
            sharedPreferences.edit().putStringSet("cookies", cookieSet).apply();
        }

        return response;
    }
}
