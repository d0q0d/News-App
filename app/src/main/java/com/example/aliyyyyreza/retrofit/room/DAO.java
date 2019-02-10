package com.example.aliyyyyreza.retrofit.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.aliyyyyreza.retrofit.model.room.Article;

import java.util.List;

@Dao
public interface DAO {
    @Query("select * from article")
    List<Article> getUsers();

    @Insert
    void addArticles(List<Article> articles);
}
