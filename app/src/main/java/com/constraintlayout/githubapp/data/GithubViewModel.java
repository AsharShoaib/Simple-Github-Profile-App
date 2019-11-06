package com.constraintlayout.githubapp.data;

import com.constraintlayout.githubapp.Resource;
import com.constraintlayout.githubapp.data.model.GithubRepo;
import com.constraintlayout.githubapp.data.model.GithubUser;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class GithubViewModel extends ViewModel {

    private GithubRepository githubRepository = GithubRepository.getInstance();

    public LiveData<Resource<GithubUser>> fetchAndDownloadUser(String userId) {
        return githubRepository.fetchAndDownloadUser(userId);
    }

    public LiveData<Resource<List<GithubRepo>>> fetchAndDownloadUserRepo(String userId) {
        return githubRepository.fetchAndDownloadUserRepo(userId);
    }

    public LiveData<GithubUser> getUser(String userId) {
        return githubRepository.getUserProfile(userId);
    }

    public LiveData<List<GithubRepo>> getUserRepo(String userId) {
        return githubRepository.getUserRepos(userId);
    }
}
