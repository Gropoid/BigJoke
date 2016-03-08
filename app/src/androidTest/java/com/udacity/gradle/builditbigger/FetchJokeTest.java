package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;



public class FetchJokeTest extends AndroidTestCase {

    public void test_fetchTask() throws Exception {
        FetchJokeTask task = new FetchJokeTask();
        String fetchedJoke = task.execute(this.getContext()).get();
        assertNotNull(fetchedJoke);
    }

}
