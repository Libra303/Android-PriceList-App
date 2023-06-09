package com.libra.greenagro;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class},version = 1)
public abstract class ProductDB extends RoomDatabase {
    public abstract ProductDao productDao();
}
