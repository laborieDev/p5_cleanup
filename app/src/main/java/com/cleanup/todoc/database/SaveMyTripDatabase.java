package com.cleanup.todoc.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.concurrent.Executors;

@Database(entities = {Task.class, Project.class}, version = 2, exportSchema = false)

public abstract class SaveMyTripDatabase extends RoomDatabase {


    // --- SINGLETON ---

    private static volatile SaveMyTripDatabase INSTANCE;


    // --- DAO ---

    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();


    // --- INSTANCE ---
    public static SaveMyTripDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SaveMyTripDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SaveMyTripDatabase.class, "MyDatabase.db")
                                .addCallback(prepopulateDatabase())
                                .allowMainThreadQueries()
                                .build();
                }
            }
        }

        return INSTANCE;
    }

    private static Callback prepopulateDatabase() {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Executors.newSingleThreadExecutor().execute(() -> INSTANCE.projectDao().createProject(new Project(
                        1L, "Projet Tartampion", 0xFFEADAD1
                )));
                Executors.newSingleThreadExecutor().execute(() -> INSTANCE.projectDao().createProject(new Project(
                        2L, "Projet Lucidia", 0xFFB4CDBA
                )));
                Executors.newSingleThreadExecutor().execute(() -> INSTANCE.projectDao().createProject(new Project(
                        3L, "Projet Circus", 0xFFA3CED2
                )));
            }
        };
    }

}