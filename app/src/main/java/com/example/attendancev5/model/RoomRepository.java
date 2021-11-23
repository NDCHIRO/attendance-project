package com.example.attendancev5.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;

import java.util.List;

public class RoomRepository {
    private RoomDAO mRoomDAO;
    private LiveData<List<Room>> mAllRooms;

    RoomRepository(Application application) {
        RoomRoomDatabase db = RoomRoomDatabase.getDatabase(application);
        mRoomDAO = db.roomDao();
        mAllRooms = mRoomDAO.getAllRooms();
    }

    LiveData<List<Room>> getAllRooms() {
        return mAllRooms;
    }

    public void insert (Room room) {
        new insertAsyncTask(mRoomDAO).execute(room);
    }

    private static class insertAsyncTask extends AsyncTask<Room, Void, Void> {

        private RoomDAO mAsyncTaskDao;

        insertAsyncTask(RoomDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Room... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
