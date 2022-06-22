package com.example.chatapp.repositories;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.chatapp.entities.ProfilePic;

@Database(entities = {ProfilePic.class}, version = 1)
@TypeConverters({Converters.class})
public  abstract class ProfilePicDB extends RoomDatabase {
    public abstract ProfilePicDao Dao();
}

