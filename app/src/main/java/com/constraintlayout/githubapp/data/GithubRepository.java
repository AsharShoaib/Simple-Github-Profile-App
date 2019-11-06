package com.constraintlayout.githubapp.data;

import com.constraintlayout.githubapp.NetworkBoundResource;
import com.constraintlayout.githubapp.Resource;
import com.constraintlayout.githubapp.data.db.GithubDAO;
import com.constraintlayout.githubapp.data.db.GithubDatabase;
import com.constraintlayout.githubapp.data.helper.RetrofitHelper;
import com.constraintlayout.githubapp.data.model.GithubRepo;
import com.constraintlayout.githubapp.data.model.GithubUser;
import com.constraintlayout.githubapp.data.service.GithubService;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import io.reactivex.Single;

public class GithubRepository {

    private static GithubRepository githubRepository;
    private final GithubService githubService;
    private final GithubDAO gitHubDAO;

    public static GithubRepository getInstance() {
        if (githubRepository == null) {
            githubRepository = new GithubRepository(RetrofitHelper.getGithubService(),
                    GithubDatabase.getInstance().githubDAO());
        }
        return githubRepository;
    }

    public GithubRepository(GithubService githubService, GithubDAO githubDAO) {
        this.githubService = githubService;
        this.gitHubDAO = githubDAO;
    }

    public LiveData<Resource<GithubUser>> fetchAndDownloadUser(String userId) {
        return new NetworkBoundResource<GithubUser, GithubUser>() {

            @Override
            protected void saveCallResult(@NonNull GithubUser githubUser) {
                githubUser.setUserId(userId);
                gitHubDAO.insertUser(githubUser);
            }

            @NonNull
            @Override
            protected LiveData<GithubUser> loadFromDb() {
                return gitHubDAO.getUserById(userId);
            }

            @NonNull
            @Override
            protected Single<GithubUser> createCall() {
                return githubService.fetchUserProfile(userId);
            }

            @Override
            protected boolean shouldFetch(@Nullable GithubUser data) {
                return true || data == null;
            }

            @Override
            protected void onFetchFailed() {

            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<GithubRepo>>> fetchAndDownloadUserRepo(String userId) {
        return new NetworkBoundResource<List<GithubRepo>, List<GithubRepo>>() {

            @Override
            protected void saveCallResult(@NonNull List<GithubRepo> githubRepo) {
                for (GithubRepo repo : githubRepo) {
                    repo.setUserId(userId);
                }
                gitHubDAO.insertRepo(githubRepo);
            }

            @NonNull
            @Override
            protected LiveData<List<GithubRepo>> loadFromDb() {
                return gitHubDAO.getRepoByUserId(userId);
            }

            @NonNull
            @Override
            protected Single<List<GithubRepo>> createCall() {
                return githubService.fetchUserRepo(userId);
            }

            @Override
            protected void onFetchFailed() {

            }
        }.getAsLiveData();
    }

    public LiveData<GithubUser> getUserProfile(String userId) {
        return gitHubDAO.getUserById(userId);
    }

    public LiveData<List<GithubRepo>> getUserRepos(String userId) {
       return gitHubDAO.getRepoByUserId(userId);
    }
}
