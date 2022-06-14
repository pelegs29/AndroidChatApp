package com.example.chatapp.repositories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.chatapp.entities.Content;
import com.example.chatapp.entities.Conversation;

import java.util.List;



@Dao
public interface ContentDao {
    @Query("SELECT * FROM content")
    List<Content> index();


    @Query("SELECT * FROM content WHERE id = :id")
    Content get(int id);

    @Query("SELECT * FROM content WHERE `to` LIKE :to AND `from` LIKE :from")
    List<Content> getContents(String from, String to);

//    (onConflict = OnConflictStrategy.REPLACE)
    @Insert
    void insert(Content... contents);



    @Update
    void update(Content... contents);

    @Delete
    void delete(Content... Content);

    @Query("DELETE FROM content")
    void deleteAll();
}