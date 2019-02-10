package com.example.aliyyyyreza.retrofit.model.room;


import androidx.room.RoomDatabase;

import com.example.aliyyyyreza.retrofit.room.DAO;

@androidx.room.Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract DAO access();
}
