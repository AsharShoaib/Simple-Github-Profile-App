package com.constraintlayout.githubapp.data.db;

import com.constraintlayout.githubapp.MyApplication;
import com.constraintlayout.githubapp.data.model.GithubRepo;
import com.constraintlayout.githubapp.data.model.GithubUser;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {GithubUser.class, GithubRepo.class}, version = 1)
public abstract class GithubDatabase extends RoomDatabase {

    private static GithubDatabase instance;

    private final static String databaseName = "github_room.db";


    public static GithubDatabase getInstance() {
        if (instance == null) {
            Builder<GithubDatabase> githubDatabaseBuilder =
                    Room.databaseBuilder(MyApplication.getContext(),
                    GithubDatabase.class, databaseName)
                    .allowMainThreadQueries();
            githubDatabaseBuilder.setJournalMode(JournalMode.TRUNCATE);
            instance = githubDatabaseBuilder.build();
        }
        return instance;
    }

    public abstract GithubDAO githubDAO();

}

