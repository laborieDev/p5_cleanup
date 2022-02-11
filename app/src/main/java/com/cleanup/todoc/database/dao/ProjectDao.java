package com.cleanup.todoc.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {
    @Query("SELECT * FROM project")
    List<Project> getAll();

    @Query("SELECT * FROM project WHERE id IN (:projectIds)")
    List<Project> loadAllByIds(int[] projectIds);

    @Query("SELECT * FROM project WHERE name LIKE :name LIKE :last LIMIT 1")
    Project findByName(String name, String last);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProject(Project project);

    @Insert
    void insertAll(Project... projects);
}
