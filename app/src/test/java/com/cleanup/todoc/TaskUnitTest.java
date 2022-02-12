package com.cleanup.todoc;

import com.cleanup.todoc.database.SaveMyTripDatabase;
import com.cleanup.todoc.di.DI;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.service.TaskApiService;
import com.cleanup.todoc.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */
@RunWith(AndroidJUnit4.class)
public class TaskUnitTest {

    private SaveMyTripDatabase database;
    private TaskApiService apiService;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, SaveMyTripDatabase.class).build();
        apiService = DI.getNewInstanceApiService(database);
    }

    @After
    public void closeDb() throws IOException {
        database.close();
    }

    @Test
    public void test_projects()
    {
        List<Project> allProjects = apiService.getProjects();

        assertEquals(3, allProjects.size());
    }
//
//    @Test
//    public void test_az_comparator() {
//        final Task task1 = new Task(1, 1, "aaa", 123);
//        final Task task2 = new Task(2, 2, "zzz", 124);
//        final Task task3 = new Task(3, 3, "hhh", 125);
//
//        final ArrayList<Task> tasks = new ArrayList<>();
//        tasks.add(task1);
//        tasks.add(task2);
//        tasks.add(task3);
//        Collections.sort(tasks, new Task.TaskAZComparator());
//
//        assertSame(tasks.get(0), task1);
//        assertSame(tasks.get(1), task3);
//        assertSame(tasks.get(2), task2);
//    }
//
//    @Test
//    public void test_za_comparator() {
//        final Task task1 = new Task(1, 1, "aaa", 123);
//        final Task task2 = new Task(2, 2, "zzz", 124);
//        final Task task3 = new Task(3, 3, "hhh", 125);
//
//        final ArrayList<Task> tasks = new ArrayList<>();
//        tasks.add(task1);
//        tasks.add(task2);
//        tasks.add(task3);
//        Collections.sort(tasks, new Task.TaskZAComparator());
//
//        assertSame(tasks.get(0), task2);
//        assertSame(tasks.get(1), task3);
//        assertSame(tasks.get(2), task1);
//    }
//
//    @Test
//    public void test_recent_comparator() {
//        final Task task1 = new Task(1, 1, "aaa", 123);
//        final Task task2 = new Task(2, 2, "zzz", 124);
//        final Task task3 = new Task(3, 3, "hhh", 125);
//
//        final ArrayList<Task> tasks = new ArrayList<>();
//        tasks.add(task1);
//        tasks.add(task2);
//        tasks.add(task3);
//        Collections.sort(tasks, new Task.TaskRecentComparator());
//
//        assertSame(tasks.get(0), task3);
//        assertSame(tasks.get(1), task2);
//        assertSame(tasks.get(2), task1);
//    }
//
//    @Test
//    public void test_old_comparator() {
//        final Task task1 = new Task(1, 1, "aaa", 123);
//        final Task task2 = new Task(2, 2, "zzz", 124);
//        final Task task3 = new Task(3, 3, "hhh", 125);
//
//        final ArrayList<Task> tasks = new ArrayList<>();
//        tasks.add(task1);
//        tasks.add(task2);
//        tasks.add(task3);
//        Collections.sort(tasks, new Task.TaskOldComparator());
//
//        assertSame(tasks.get(0), task1);
//        assertSame(tasks.get(1), task2);
//        assertSame(tasks.get(2), task3);
//    }
}