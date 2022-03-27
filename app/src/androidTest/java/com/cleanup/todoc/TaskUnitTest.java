package com.cleanup.todoc;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import android.content.Context;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.cleanup.todoc.database.SaveMyTripDatabase;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.di.DI;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.service.TaskApiService;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TaskUnitTest
{
    private TaskDao taskDao;

    private SaveMyTripDatabase db;
    private TaskApiService apiService;

    final int INIT_TASK_LIST_FIRST_ID = 1;
    final int INIT_TASK_LIST_SIZE = 3;

    final int INIT_PROJECT_LIST_SIZE = 1;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, SaveMyTripDatabase.class).allowMainThreadQueries().build();
        taskDao = db.taskDao();
        apiService = DI.getNewInstanceApiService(db);

        long lastProjectId = (long) 1;

        for (int j = 1; j <= INIT_PROJECT_LIST_SIZE; j++) {
            lastProjectId = (long) j;
            Project newProject = new Project(lastProjectId, "Essai", 0xFFEADAD1);
            db.projectDao().createProject(newProject);
        }

        for (int i = INIT_TASK_LIST_FIRST_ID; i <= INIT_TASK_LIST_SIZE; i++) {
            Task newTask = new Task(i, lastProjectId, "Tâche example " + i, new Date().getTime());
            taskDao.insertAll(newTask);
        }
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    /****** INIT APP ******/

    @Test
    public void givenTasksListWhenInitAppThenListSizeEqualsToInitListSize() throws Exception {
        List<Task> allTasks = apiService.getTasks();
        assertThat(allTasks.size(), equalTo(INIT_TASK_LIST_SIZE));
    }

    @Test
    public void givenProjectsListWhenInitAppThenListSizeEqualsToInitListSize() throws Exception {
        List<Project> allProjects = apiService.getProjects();
        assertThat(allProjects.size(), equalTo(INIT_PROJECT_LIST_SIZE));
    }

    @Test
    public void givenSortMethodWhenInitAppThenSortMethodEqualsToNone() throws Exception {
        TaskApiService.SortMethod sortMethod = apiService.getSortMethod();
        assertThat(sortMethod, equalTo(TaskApiService.SortMethod.NONE));
    }

    /****** END INIT APP ******/

    @Test
    public void givenMaxIdWhenAddTaskThenMaxIdEqualsToListSize() throws Exception {
        long newID = apiService.getMaxId();
        List<Task> allTasks = apiService.getTasks();
        assertThat(newID, equalTo((long) allTasks.size()));
    }

    @Test
    public void givenFirstTaskWhenDeleteTaskThenTaskNotExists() throws Exception {
        List<Task> allTasks = apiService.getTasks();
        Task removeTask = allTasks.get(0);
        apiService.deleteTask(removeTask);
        allTasks = apiService.getTasks();

        assertFalse(allTasks.contains(removeTask));
    }

    @Test
    public void givenLastTaskNameWhenAddTaskThenNewTashHasSameName() throws Exception {
        long id = (long) apiService.getMaxId() + 1;
        Project project = apiService.getProjects().get(0);
        String name = "Test";

        Task newTask = new Task(id, project.getId(), name, new Date().getTime());
        apiService.createTask(newTask);

        List<Task> allTasks = apiService.getTasks();
        Task lastTask = allTasks.get(allTasks.size() - 1);

        assertThat(lastTask.getName(), equalTo(name));
    }

    @Test
    public void givenFirstTaskWhenSetSortableThenTaskIdEqualsToLastId() throws Exception {
        List<Task> initAllTasks = apiService.getTasks();

        for(Task task: initAllTasks) {
            apiService.deleteTask(task);
        }

        long newID = (long) apiService.getMaxId() + 1;
        Project project = apiService.getProjects().get(0);

        Task newTask = new Task(newID, project.getId(), "aaa Tâche example", new Date().getTime());
        apiService.createTask(newTask);

        newID = (long) apiService.getMaxId() + 1;
        newTask = new Task(newID, project.getId(), "zzz Tâche example", new Date().getTime());
        apiService.createTask(newTask);

        newID = (long) apiService.getMaxId() + 1;
        newTask = new Task(newID, project.getId(), "hhh Tâche example", new Date().getTime());
        apiService.createTask(newTask);

        // OLD to RECENT
        apiService.setSortMethod(TaskApiService.SortMethod.OLD_FIRST);
        Task lastTask = apiService.getTasks().get(0);
        assertThat(lastTask.getName(), equalTo("aaa Tâche example"));

        // NAME A to Z
        apiService.setSortMethod(TaskApiService.SortMethod.ALPHABETICAL);
        lastTask = apiService.getTasks().get(0);
        assertThat(lastTask.getName(), equalTo("aaa Tâche example"));

        // NAME Z to A
        apiService.setSortMethod(TaskApiService.SortMethod.ALPHABETICAL_INVERTED);
        lastTask = apiService.getTasks().get(0);
        assertThat(lastTask.getName(), equalTo("zzz Tâche example"));

        // RECENT to OLD
        apiService.setSortMethod(TaskApiService.SortMethod.RECENT_FIRST);
        Task newLastTask = apiService.getTasks().get(0);
        String name = newLastTask.getName();
        assertThat(name, equalTo("hhh Tâche example"));
    }
}