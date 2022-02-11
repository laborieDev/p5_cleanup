package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskViewModel extends ViewModel {
    public final LiveData<List<Task>> tasksList;

    public TaskViewModel(TaskDao taskDao) {
        tasksList = taskDao.getAll();
    }
}
