package com.MFarrelAthaillahNugrohoJSleepMN.jsleep_android.request;

public class UtilsApi {
    public static final String BASE_URL_API = "https://10.10.37.103:8080";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
