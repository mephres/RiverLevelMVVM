package me.kdv.riverlevel.room;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

public class App extends Application {

    private static App instance;
    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public void dropAllTables() {
        database.clearAllTables();
    }
}


