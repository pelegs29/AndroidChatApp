package com.example.chatapp.repositories;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.chatapp.entities.Contact;

@Database(entities = {Contact.class}, version = 1)
public  abstract class ContactDB extends RoomDatabase {
    public abstract ContactDao Dao();
}
