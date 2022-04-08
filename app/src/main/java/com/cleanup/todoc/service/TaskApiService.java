package com.cleanup.todoc.service;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

public interface TaskApiService {

    /**
     * Get all my Tasks
     * @return {@link List}
     */
    List<Task> getTasks();

    /**
     * Delete a Task
     * @param task
     */
    void deleteTask(Task task);

    /**
     * Create a Task
     * @param task
     */
    void createTask(Task task);

    /**
     * Get all projects
     * @return projects
     */
    List<Project> getProjects();

    /**
     * Get all sort methods enum
     * @return sortMethod
     */
    public SortMethod getSortMethod();

    /**
     * Set actual used sort method
     * @param sortMethod
     */
    public void setSortMethod(SortMethod sortMethod);

    /**
     * List of all possible sort methods for task
     */
    public enum SortMethod {
        /**
         * Sort alphabetical by name
         */
        ALPHABETICAL,
        /**
         * Inverted sort alphabetical by name
         */
        ALPHABETICAL_INVERTED,
        /**
         * Lastly created first
         */
        RECENT_FIRST,
        /**
         * First created first
         */
        OLD_FIRST,
        /**
         * No sort
         */
        NONE
    }
}
