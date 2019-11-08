package com.constraintlayout.githubapp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.constraintlayout.githubapp.data.model.GithubRepo;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.Nullable;

public class RepoInfoBottomDialogFragment extends BottomSheetDialogFragment {

    private final GithubRepo githubRepo;

    public RepoInfoBottomDialogFragment(GithubRepo githubRepo) {
        this.githubRepo = githubRepo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.repo_info_bottom_sheet, container,
                false);

        TextView lastUpdatedText = view.findViewById(R.id.tv_lastUpdated);
        TextView Stars = view.findViewById(R.id.tv_stars);
        TextView Forks = view.findViewById(R.id.tv_forks);
        lastUpdatedText.setText(formatDate(githubRepo.getUpdated_at()));
        Stars.setText(String.valueOf(githubRepo.getStargazers_count()));
        Forks.setText(String.valueOf(githubRepo.getForks()));
        return view;

    }

    private String formatDate(String updated_at) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat output = new SimpleDateFormat("MMM d, yyyy hh:mm:ss aaa");

        Date d = null;
        try {
            d = input.parse(updated_at);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(d);
        return formatted;
    }


    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.repo_info_bottom_sheet, null);
        dialog.setContentView(contentView);
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }
}
