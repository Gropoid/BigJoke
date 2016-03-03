package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gropoid.bigjoke.backend.jokeApi.JokeApi;
import com.gropoid.bigjoke.backend.jokeApi.model.JokeBean;
import com.gropoid.jokelibrary.JokeDisplayActivity;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void tellJoke(View view){
        FetchJokeTask task = new FetchJokeTask();
        task.execute();
        //JokeDisplayActivity.startWithJoke(this, "nothing");
    }

    private class FetchJokeTask extends AsyncTask<Void, Void, String> {
        private final String TAG = FetchJokeTask.class.getSimpleName();
        @Override
        protected String doInBackground(Void... params) {
            JokeApi api = JokeResource.getJokeApi();
            try {
                JokeBean joke = api.getJoke().execute();
                return joke.getJoke();
            } catch (IOException ioe) {
                Log.e(TAG, "IOException : " + ioe.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String jokeString) {
            if (jokeString != null) {
                JokeDisplayActivity.startWithJoke(MainActivity.this, jokeString);
            } else {
                Toast.makeText(MainActivity.this, "Could not retrieve joke :o(", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
