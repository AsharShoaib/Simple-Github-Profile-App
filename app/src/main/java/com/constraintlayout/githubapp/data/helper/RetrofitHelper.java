package com.constraintlayout.githubapp.data.helper;

import android.util.Log;

import com.constraintlayout.githubapp.data.service.GithubService;
import com.squareup.moshi.Moshi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrofitHelper {

    private static volatile GithubService githubService;
    private static final String RETROFIT_LOG_TAG = "OK-HTTP-LOG";

    public static GithubService getGithubService() {
        GithubService localRef = githubService;
        if (localRef == null) {
            synchronized (RetrofitHelper.class) {
                localRef = githubService;
                if (localRef == null) {
                    githubService = localRef = createGithubService();
                }
            }
        }
        return localRef;
    }

    private static GithubService createGithubService() {
        Moshi moshi = new Moshi.Builder().build();
        MoshiConverterFactory moshiConverter = MoshiConverterFactory.create(moshi);
        String baseUrl = "https://api.github.com/users";
        return createRetrofitBuilder().addConverterFactory(moshiConverter)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build()
                .create(GithubService.class);
    }

    private static Retrofit.Builder createRetrofitBuilder() {

        HttpLoggingInterceptor logging =
                new HttpLoggingInterceptor(message -> Log.d(RETROFIT_LOG_TAG, message));
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        return new Retrofit.Builder().client(httpClient.build());
    }

}
