package com.cleanup.todoc.di;

import com.cleanup.todoc.database.SaveMyTripDatabase;
import com.cleanup.todoc.service.DummyTaskApiService;
import com.cleanup.todoc.service.TaskApiService;

public class DI {

    private static TaskApiService service;

    /**
     * Get an instance on @{@link TaskApiService}
     * @return
     */
    public static TaskApiService getTaskApiService() {
        return service;
    }

    /**
     * Get always a new instance on @{@link TaskApiService}. Useful for tests, so we ensure the context is clean.
     * @return
     */
    public static TaskApiService getNewInstanceApiService(SaveMyTripDatabase databse) {
        return new DummyTaskApiService(databse);
    }

}
