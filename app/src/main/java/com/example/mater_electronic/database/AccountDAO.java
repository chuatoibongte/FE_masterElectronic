package com.example.mater_electronic.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mater_electronic.models.account.Account;

@Dao
public interface AccountDAO {

//    Lấy data account khi đăng nhập
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAccount(Account account);

    @Query("SELECT * FROM account LIMIT 1")
    Account getAccount();

}
