package com.cleanup.todoc.service;

import com.cleanup.todoc.database.SaveMyTripDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.Collections;
import java.util.List;

public class DummyTaskApiService implements TaskApiService {

    private List<Task> tasks;
    private List<Project> projects;

    private final SaveMyTripDatabase database;
    private SortMethod sortMethod = SortMethod.NONE;

    public DummyTaskApiService(SaveMyTripDatabase database)
    {
        this.database = database;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Task> getTasks() {
        List<Task> allTasks = database.taskDao().getAll();

        switch (sortMethod) {
            case ALPHABETICAL:
                Collections.sort(allTasks, new Task.TaskAZComparator());
                break;
            case ALPHABETICAL_INVERTED:
                Collections.sort(allTasks, new Task.TaskZAComparator());
                break;
            case RECENT_FIRST:
                Collections.sort(allTasks, new Task.TaskRecentComparator());
                break;
            case OLD_FIRST:
                Collections.sort(allTasks, new Task.TaskOldComparator());
                break;
        }

        return allTasks;
    }

    public SortMethod getSortMethod()
    {
        return sortMethod;
    }

    public void setSortMethod(SortMethod sortMethod)
    {
        this.sortMethod = sortMethod;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteTask(Task task) {
        database.taskDao().delete(task);
    }

    /**
     * {@inheritDoc}
     * @param task
     */
    @Override
    public void createTask(Task task) {
        database.taskDao().insertAll(task);
    }

//    public Task getTask(int position)
//    {
//        return tasks.get(position);
//    }

    public List<Project> getProjects() {
        return database.projectDao().getAll();
    }


}
