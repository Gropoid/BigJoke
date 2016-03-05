package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.test.AndroidTestCase;

import static org.mockito.Mockito.mock;


public class FetchJokeTest extends AndroidTestCase {

    FetchJokeTask task  = new FetchJokeTask();

    public void test_fetchTask() {
        Context context = mock(Context.class);
        FetchJokeTask task = new FetchJokeTask();
        task.execute(context);
        assertNotNull("joke");
    }

}
