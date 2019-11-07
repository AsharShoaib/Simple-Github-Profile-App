package com.constraintlayout.githubapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.constraintlayout.githubapp.data.GithubViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

    private GithubViewModel githubViewModel;
    private FragmentManager mFragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFragmentManager = getSupportFragmentManager();
        githubViewModel = ViewModelProviders.of(this).get(GithubViewModel.class);
        EditText searchText = findViewById(R.id.et_userId);
        Button searchButton = findViewById(R.id.btn_search);
        searchButton.setOnClickListener(v -> {
            String searchInput = searchText.getText().toString();
            if (!TextUtils.isEmpty(searchInput)) {
                loadInitialFragment(searchInput);
                fetchUserProfile(searchInput);
                fetchUserRepos(searchInput);
            }
        });

    }

    private void fetchUserRepos(String searchInput) {
        githubViewModel.fetchAndDownloadUserRepo(searchInput).observe(this, githubUserResource -> {
            if (githubUserResource != null) {
                switch (githubUserResource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        navigateToFragment(UserProfileFragment.TAG, searchInput);
                        break;
                    case ERROR:

                        break;
                }
            }
        });
    }

    private void navigateToFragment(String tag, String searchInput) {
        fragmentTransaction = mFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .addToBackStack(null);
        switch (tag) {
            case UserProfileFragment.TAG:
                fragmentTransaction.replace(R.id.fragment_container_user_profile,
                        UserProfileFragment.newInstance(searchInput),
                        UserProfileFragment.TAG);
                break;
            case UserRepoFragment.TAG:
                fragmentTransaction.replace(R.id.fragment_container_repo,
                        UserRepoFragment.newInstance(searchInput),
                        UserProfileFragment.TAG);
                break;
        }

        fragmentTransaction.commit();
    }

    private void fetchUserProfile(String searchInput) {
        githubViewModel.fetchAndDownloadUser(searchInput).observe(this, githubUserResource -> {
            if (githubUserResource != null) {
                switch (githubUserResource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        navigateToFragment(UserRepoFragment.TAG, searchInput);
                        break;
                    case ERROR:
                        break;
                }
            }
        });
    }

    private void loadInitialFragment(String searchInput) {
        Fragment initialFragment = UserProfileFragment.newInstance(searchInput);
        fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_user_profile, initialFragment);
    }

}
