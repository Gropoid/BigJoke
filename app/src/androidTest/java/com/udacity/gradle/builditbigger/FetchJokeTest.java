package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class FetchJokeTest extends AndroidTestCase {

    public void test_fetchTask() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        FetchJokeTask task = new FetchJokeTask() {
            @Override
            protected void onPostExecute(String jokeString) {
                assertNotNull(jokeString);
                latch.countDown();
            }
        };
        task.execute(getContext());
        latch.await(10, TimeUnit.SECONDS);
        assertEquals(0, latch.getCount());
    }

}
