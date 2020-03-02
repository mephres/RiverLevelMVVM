package me.kdv.riverlevel.room.dao;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import me.kdv.riverlevel.model.River;

@Dao
public interface RiverDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRiverList(List<River> riverList);

    @Query("SELECT * FROM River")
    List<River> getRiverList();

}
