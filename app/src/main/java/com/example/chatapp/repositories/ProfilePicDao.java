package com.example.chatapp.repositories;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.chatapp.entities.ProfilePic;

import java.util.List;

@Dao
public interface ProfilePicDao {
    @Query("SELECT * FROM profilepic")
    List<ProfilePic> index();

    @Query("SELECT * FROM profilepic WHERE id = :id")
    ProfilePic get(String id);

    @Insert
    void insert(ProfilePic... profilePics);

}
