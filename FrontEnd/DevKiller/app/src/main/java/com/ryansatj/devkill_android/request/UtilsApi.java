package com.ryansatj.devkill_android.request;

import android.content.Context;

public class UtilsApi {
    public static final String BASE_URL_API = "https://devkill-pm-system.vercel.app/";

    public static BaseApiService getApiService(Context context) {
        return RetrofitClient.getClient(BASE_URL_API, context).create(BaseApiService.class);
    }
}
