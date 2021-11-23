package com.example.attendancev5.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RoomDAO {
    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insert(Room room);

    @Query("DELETE FROM room_table")
    void deleteAll();

    @Query("SELECT * from room_table ORDER BY room ASC")
    LiveData<List<Room>> getAllRooms();
}
