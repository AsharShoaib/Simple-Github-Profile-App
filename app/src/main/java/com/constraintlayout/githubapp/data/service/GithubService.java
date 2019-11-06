package com.constraintlayout.githubapp.data.service;

import com.constraintlayout.githubapp.data.model.GithubRepo;
import com.constraintlayout.githubapp.data.model.GithubUser;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {

    @GET("users/{userId}")
    Single<GithubUser> fetchUserProfile(@Path("userId") String userId);

    @GET("users/{userId}/repos")
    Single<List<GithubRepo>> fetchUserRepo(@Path("userId") String userId);
}
