package com.example.chatapp.repositories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.chatapp.entities.Contact;
import com.example.chatapp.entities.Content;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact")
    List<Contact> index();

    @Query("SELECT * FROM contact WHERE id = :id")
    Contact get(int id);

    @Query("SELECT * FROM contact WHERE 'id' = id")
    Contact getById(String idFriend);
//    @Query("SELECT * FROM contact WHERE `to` LIKE :to AND `from` LIKE :from")
//    List<Content> getContents(String from, String to);

    @Insert
    void insert(Contact... contacts);

    @Update
    void update(Contact... contacts);

    @Delete
    void delete(Contact... Content);

    @Query("DELETE FROM contact")
    void deleteAll();
}