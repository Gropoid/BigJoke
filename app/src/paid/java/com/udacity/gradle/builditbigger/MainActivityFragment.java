package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gropoid.jokelibrary.JokeDisplayActivity;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = MainActivityFragment.class.getSimpleName();

    @Bind(R.id.instructions_text_view)
    TextView instructionsView;

    @Bind(R.id.joke_button)
    Button jokeButton;

    @Bind(R.id.progressSpinner)
    ProgressBar progressIndicator;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    public void tellJoke(View view){
        FetchJokeTask task = new FetchJokeTask() {
            @Override
            protected void onPostExecute(String jokeString) {
                if (jokeString != null) {
                    setLoadingState(false);
                    JokeDisplayActivity.startWithJoke(getContext(), jokeString);
                } else {
                    Toast.makeText(getContext(), "Could not retrieve joke :o(", Toast.LENGTH_SHORT).show();
                    setLoadingState(false);
                }
            }
        };
        setLoadingState(true);
        task.execute(getContext());
    }

    private void setLoadingState(boolean loading) {
        if (loading) {
            instructionsView.setVisibility(View.GONE);
            jokeButton.setVisibility(View.GONE);
            progressIndicator.setVisibility(View.VISIBLE);
        } else {
            instructionsView.setVisibility(View.VISIBLE);
            jokeButton.setVisibility(View.VISIBLE);
            progressIndicator.setVisibility(View.GONE);
        }
    }
}
