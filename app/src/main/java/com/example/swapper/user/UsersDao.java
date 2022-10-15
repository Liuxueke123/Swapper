package com.example.swapper.user;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UsersDao {
    @Insert
    void insert(UsersEntity... userEntities);

    @Update
    void update(UsersEntity... userEntities);

    @Delete
    void delete(UsersEntity... userEntities);

    @Query("SELECT * FROM user")
    List<UsersEntity> getUsers();

}
