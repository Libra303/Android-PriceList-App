package com.libra.greenagro;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void insertProdct(Product pd);

    @Update
    void updateProduct(Product pd);

    @Delete
    void deleteProduct(Product pd);

    @Query("SELECT * FROM product ORDER BY name, price")
    List<Product> gettAll();

    @Query("DELETE FROM product")
    void deleteAll();

    @Query("SELECT * FROM product WHERE name LIKE :search_name ORDER BY name, price")
    List<Product> getByName(String search_name);

    @Query("DELETE FROM product WHERE id == :id")
    void deleteById(int id);

    @Query("UPDATE product SET name = :name, size = :size, price = :price WHERE id = :id")
    void updateById(int id, String name,String size, String price);


}
