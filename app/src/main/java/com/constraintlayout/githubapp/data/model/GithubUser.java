package com.constraintlayout.githubapp.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "github_user")
public class GithubUser {

    @PrimaryKey
    @NonNull
    private final int id;
    @NonNull
    private final String name;
    private final String avatar_url;
    private String userId;

    public GithubUser(@NonNull int id, @NonNull String name, String avatar_url, String userId) {
        this.id = id;
        this.name = name;
        this.avatar_url = avatar_url;
        this.userId = userId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public String getAvatar_url() {
        return avatar_url;
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
}