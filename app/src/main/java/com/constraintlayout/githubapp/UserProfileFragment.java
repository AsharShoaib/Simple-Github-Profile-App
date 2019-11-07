package com.constraintlayout.githubapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.constraintlayout.githubapp.data.GithubViewModel;
import com.constraintlayout.githubapp.data.model.GithubUser;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

public class UserProfileFragment extends Fragment {

    public static final String TAG = "UserProfileFragment";
    private String searchInput;
    private GithubViewModel githubViewModel;
    private LiveData<GithubUser> userProfile;

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
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        githubViewModel = ViewModelProviders.of(this).get(GithubViewModel.class);
        userProfile = githubViewModel.getUser(searchInput);
        userProfile.observe(getViewLifecycleOwner(), githubUser -> {
            TextView userName = getView().findViewById(R.id.tv_user_name);
            ImageView userProfileImage = getView().findViewById(R.id.iv_user_profile);
            userName.setText(githubUser.getName());
            Picasso.with(getView().getContext()).load(githubUser.getAvatar_url()).into(userProfileImage);

        });
    }

    public void setSearchInput(String searchInput) {
        this.searchInput = searchInput;
    }
}
