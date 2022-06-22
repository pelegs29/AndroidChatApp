package com.example.chatapp.entities;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.example.chatapp.repositories.Converters;

@Entity
public class ProfilePic {


    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String id;
    private Bitmap imageBitMap;

    public ProfilePic(String id, Bitmap imageBitMap) {
        this.id = id;
        this.imageBitMap = imageBitMap;
    }


    public String getUserId() {
        return id;
    }

    public void setUserId(String userId) {
        this.id = userId;
    }

    public Bitmap getImageBitMap() {
        return imageBitMap;
    }

    public void setImageBitMap(Bitmap imageBitMap) {
        this.imageBitMap = imageBitMap;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
