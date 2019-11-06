package com.constraintlayout.githubapp.data.db;

import com.constraintlayout.githubapp.data.model.GithubRepo;
import com.constraintlayout.githubapp.data.model.GithubUser;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface GithubDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(GithubUser githubUser);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRepo(List<GithubRepo> githubRepos);

    @Query("SELECT * FROM github_user WHERE userId = :userId")
    LiveData<GithubUser> getUserById(String userId);

    @Query("SELECT * FROM github_repo WHERE userId = :userId")
    LiveData<List<GithubRepo>> getRepoByUserId(String userId);
}
