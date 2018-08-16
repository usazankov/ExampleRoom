package com.example.dell.exampleormlite;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by dell on 15.08.2018.
 */

public class ExtendData extends SimpleData {
    @DatabaseField
    private int newField;

    public ExtendData(){

    }
    public ExtendData(long millies){
        super(millies);
        newField = 123;
    }
}
