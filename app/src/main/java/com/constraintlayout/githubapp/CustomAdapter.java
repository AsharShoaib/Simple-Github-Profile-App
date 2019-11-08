package com.constraintlayout.githubapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.constraintlayout.githubapp.data.model.GithubRepo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private List<GithubRepo> repos;
    private Context context;
    private LayoutInflater layoutInflater;
    private ShowRepoInfoListener showRepoInfoListener;

    public CustomAdapter(Context context, ShowRepoInfoListener showRepoInfoListener) {
        this.repos = new ArrayList<>();
        ;
        this.context = context;
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.showRepoInfoListener = showRepoInfoListener;
    }

    public interface ShowRepoInfoListener {
        void onRepoInfoClicked(GithubRepo githubRepo);
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.layout_repo_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.bind(repos.get(position));
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public void setData(List<GithubRepo> githubRepos) {
        if (githubRepos != null) {
            DiffCallback diffCallback = new DiffCallback(repos, githubRepos);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

            repos.clear();
            repos.addAll(githubRepos);
            diffResult.dispatchUpdatesTo(this);
        } else {
            repos = githubRepos;
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDescription;
        private CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_repo_name);
            tvDescription = itemView.findViewById(R.id.tv_repo_desc);
            cardView = itemView.findViewById(R.id.cv_repo);
        }

        void bind(final GithubRepo repo) {
            if (repo != null) {
                tvTitle.setText(repo.getName());
                tvDescription.setText(repo.getDescription());
                cardView.setOnClickListener(v ->
                        showRepoInfoListener.onRepoInfoClicked(repo));
            }
        }
    }

    class DiffCallback extends DiffUtil.Callback {

        private final List<GithubRepo> oldPosts, newPosts;

        public DiffCallback(List<GithubRepo> oldPosts, List<GithubRepo> newPosts) {
            this.oldPosts = oldPosts;
            this.newPosts = newPosts;
        }

        @Override
        public int getOldListSize() {
            return oldPosts.size();
        }

        @Override
        public int getNewListSize() {
            return newPosts.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldPosts.get(oldItemPosition).getId() == newPosts.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldPosts.get(oldItemPosition).equals(newPosts.get(newItemPosition));
        }
    }
}
