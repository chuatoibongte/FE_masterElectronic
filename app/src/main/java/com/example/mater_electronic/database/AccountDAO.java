package com.example.mater_electronic.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mater_electronic.models.account.Account;

@Dao
public interface AccountDAO {

//    Lấy data account khi đăng nhập
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAccount(Account account);

    @Query("SELECT * FROM account WHERE _id = :id LIMIT 1")
    Account getAccountById(String id);
//Update account
    @Update
    void updateAccount(Account account);
}
