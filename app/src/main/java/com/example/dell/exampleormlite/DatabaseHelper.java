package com.example.dell.exampleormlite;

/**
 * Created by dell on 15.08.2018.
 */

        import java.sql.SQLException;

        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.util.Log;

        import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
        import com.j256.ormlite.dao.Dao;
        import com.j256.ormlite.dao.RuntimeExceptionDao;
        import com.j256.ormlite.support.ConnectionSource;
        import com.j256.ormlite.table.TableUtils;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "/storage/emulated/0/db4/helloAndroid.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper db;
    // the DAO object we use to access the SimpleData table
    private Dao<ExtendData, Integer> simpleDao = null;
    private RuntimeExceptionDao<ExtendData, Integer> simpleRuntimeDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, ExtendData.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

        // here we try inserting data in the on-create as a test
        RuntimeExceptionDao<ExtendData, Integer> dao = getSimpleDataDao();
        long millis = System.currentTimeMillis();
        // create some entries in the onCreate
        ExtendData simple = new ExtendData(millis);
        dao.create(simple);
        simple = new ExtendData(millis + 1);
        dao.create(simple);
        Log.i(DatabaseHelper.class.getName(), "created new entries in onCreate: " + millis);
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, ExtendData.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
     * value.
     */
    public Dao<ExtendData, Integer> getDao() throws SQLException {
        if (simpleDao == null) {
            simpleDao = getDao(ExtendData.class);
        }
        return simpleDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<ExtendData, Integer> getSimpleDataDao() {
        if (simpleRuntimeDao == null) {
            simpleRuntimeDao = getRuntimeExceptionDao(ExtendData.class);
        }
        return simpleRuntimeDao;
    }
    public static DatabaseHelper getInstance(){
        if(db == null){
            db = new DatabaseHelper(MyApplication.getApp());
        }
        return db;
    }
    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        simpleDao = null;
        simpleRuntimeDao = null;
    }
}

