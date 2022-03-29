package com.forthcode.customerapp.utils;

import java.util.List;

public interface LoadTasksCallback<T> {

    void onTasksLoaded(List<T> tasks);

    void onDataNotAvailable();
}
