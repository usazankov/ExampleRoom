package com.example.dell.exampleormlite;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.j256.ormlite.dao.Dao;

import java.io.File;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File rootDir = Environment.getExternalStorageDirectory();
        File path = new File(rootDir,"db/");
        String p = path.getAbsolutePath();
        try {
            Dao<ExtendData, Integer> dao = DatabaseHelper.getInstance().getDao();
            ExtendData data = new ExtendData(1000);
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
