package me.kdv.riverlevel.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import me.kdv.riverlevel.model.River;
import me.kdv.riverlevel.room.dao.RiverDao;

@Database(entities = {River.class}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {
    public abstract RiverDao riverDao();
}
