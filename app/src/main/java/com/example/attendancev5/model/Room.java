package com.example.attendancev5.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "room_table")
public class Room {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "room")
    private String mRoom;

    public Room(@NonNull String room) {this.mRoom = room;}

    public String getRoom(){return this.mRoom;}
}
