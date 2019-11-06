package com.constraintlayout.githubapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
                TextView tv;
                switch (githubUserResource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        fragmentTransaction.commit();
                        break;
                    case ERROR:

                        break;
                }
            }
        });
    }

    private void fetchUserProfile(String searchInput) {
        githubViewModel.fetchAndDownloadUser(searchInput).observe(this, githubUserResource -> {
            if (githubUserResource != null) {
                TextView tv;
                switch (githubUserResource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
//                        tv = findViewById(R.id.textView);
                        if (githubUserResource.data != null) {
//                            tv.setText(githubUserResource.data.getName());
                        } else {
//                            tv.setText("Empty");
                        }
                        break;
                    case ERROR:
//                        tv = findViewById(R.id.textView);
//                        tv.setText("ERROR");

                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadInitialFragment(String searchInput) {
        Fragment initialFragment = UserProfileFragment.newInstance(searchInput);
        fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, initialFragment);
    }

    private void performTransition() {
        // more on this later
    }
}
