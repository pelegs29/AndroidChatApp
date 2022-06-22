package com.example.chatapp.repositories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.chatapp.entities.Contact;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact")
    List<Contact> index();

    @Query("SELECT * FROM contact WHERE id = :id")
    Contact get(int id);

    @Query("SELECT * FROM contact WHERE contactOf = :contactof")
    List<Contact> getUserContacts(String contactof);

    //    @Query("SELECT * FROM contact WHERE 'id' = id")
//    Contact getById(String idFriend);
//
    @Insert
    void insert(Contact... contacts);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Contact... contacts);

    @Delete
    void delete(Contact... Content);

    @Query("DELETE FROM contact")
    void deleteAll();
}