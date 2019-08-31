package com.example.myapplication;

import android.widget.TextView;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    public int uid;


    @ColumnInfo(name = "cafe_name")
    public String cafeName;

    @ColumnInfo(name = "barcode_value")
    public String barcodeValue;


    public User(int uid, String cafeName, String barcodeValue) {
        this.uid = uid;
        this.cafeName = cafeName;
        this.barcodeValue = barcodeValue;
    }

}