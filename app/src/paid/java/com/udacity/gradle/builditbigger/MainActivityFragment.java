package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gropoid.jokelibrary.JokeDisplayActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = MainActivityFragment.class.getSimpleName();

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        return root;
    }

    public void tellJoke(View view){
        FetchJokeTask task = new FetchJokeTask() {
            @Override
            protected void onPostExecute(String jokeString) {
                if (jokeString != null) {
                    JokeDisplayActivity.startWithJoke(getContext(), jokeString);
                } else {
                    Toast.makeText(getContext(), "Could not retrieve joke :o(", Toast.LENGTH_SHORT).show();
                }
            }
        };
        task.execute(getContext());
    }

}
