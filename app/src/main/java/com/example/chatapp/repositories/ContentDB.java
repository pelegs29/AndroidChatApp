package com.example.chatapp.repositories;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.chatapp.entities.Content;


@Database(entities = {Content.class}, version = 1)
public  abstract class ContentDB extends RoomDatabase {
    public abstract ContentDao Dao();
}
