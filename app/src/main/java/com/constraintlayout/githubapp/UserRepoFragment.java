package com.constraintlayout.githubapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.constraintlayout.githubapp.data.GithubViewModel;
import com.constraintlayout.githubapp.data.model.GithubRepo;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserRepoFragment extends Fragment implements CustomAdapter.ShowRepoInfoListener {

    public static final String TAG = "UserRepoFragment";
    private String searchInput;
    private GithubViewModel githubViewModel;
    private LiveData<List<GithubRepo>> userRepo;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;

    public UserRepoFragment(String searchInput) {
        this.searchInput = searchInput;
    }

    public static UserRepoFragment newInstance(String searchInput) {
        return new UserRepoFragment(searchInput);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_repo, container, false);
        recyclerView = v.findViewById(R.id.rv_user_repo);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CustomAdapter(getContext(), this);
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        githubViewModel = ViewModelProviders.of(this).get(GithubViewModel.class);
        userRepo = githubViewModel.getUserRepo(searchInput);
        userRepo.observe(getViewLifecycleOwner(), githubRepos -> {
            adapter.setData(githubRepos);
        });
    }

    public void setSearchInput(String searchInput) {
        this.searchInput = searchInput;
    }

    @Override
    public void onRepoInfoClicked(GithubRepo githubRepo) {
        RepoInfoBottomDialogFragment repoInfoBottomDialogFragment =
                new RepoInfoBottomDialogFragment(githubRepo);
        repoInfoBottomDialogFragment.show(getFragmentManager(), "show_repo_info_dialog_fragment");
    }
}
