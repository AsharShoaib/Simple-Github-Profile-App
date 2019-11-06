package com.constraintlayout.githubapp.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "github_repo")
public class GithubRepo {

    @PrimaryKey
    @NonNull
    private final int id;
    @NonNull
    private final String name;
    private final String description;
    private final String updated_at;
    private final int stargazers_count;
    private final int forks;
    private String userId;

    public GithubRepo(@NonNull int id, @NonNull String name, String description, String updated_at, int stargazers_count, int forks, String userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.updated_at = updated_at;
        this.stargazers_count = stargazers_count;
        this.forks = forks;
        this.userId = userId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    public int getForks() {
        return forks;
    }
}