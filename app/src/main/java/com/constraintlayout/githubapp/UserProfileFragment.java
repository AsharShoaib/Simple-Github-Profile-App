package com.constraintlayout.githubapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.constraintlayout.githubapp.data.GithubViewModel;
import com.constraintlayout.githubapp.data.model.GithubRepo;
import com.constraintlayout.githubapp.data.model.GithubUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

public class UserProfileFragment extends Fragment {

    private final String searchInput;
    private GithubViewModel githubViewModel;
    private LiveData<GithubUser> userProfile;
    private LiveData<List<GithubRepo>> userRepo;

    public UserProfileFragment(String searchInput) {
        this.searchInput = searchInput;
    }

    public static UserProfileFragment newInstance(String searchInput) {
        return new UserProfileFragment(searchInput);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_profile, container, false);
        githubViewModel = ViewModelProviders.of(this).get(GithubViewModel.class);
        userProfile = githubViewModel.getUser(searchInput);
        userRepo = githubViewModel.getUserRepo(searchInput);
        userProfile.observe(this, githubUser -> {
            TextView userName = v.findViewById(R.id.tv_user_name);
            ImageView userProfileImage = v.findViewById(R.id.iv_user_profile);
            userName.setText(githubUser.getName());
            Picasso.with(v.getContext()).load(githubUser.getAvatar_url()).into(userProfileImage);

        });
        return v;
    }
}
