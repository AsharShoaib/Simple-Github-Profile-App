package com.constraintlayout.githubapp.data.service;

import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface GithubService {

    Single<List<User>> fetchUserProfile();
}
