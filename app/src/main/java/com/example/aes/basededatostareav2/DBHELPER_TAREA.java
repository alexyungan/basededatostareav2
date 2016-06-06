package com.example.aes.basededatostareav2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by Aes on 29/5/2016.
 */
public class DBHELPER_TAREA  extends SQLiteOpenHelper{

    private static final int VERSION=2;
    private static final String DATA_BASE_NAME="administracion_tareas";
    //constructor
    public DBHELPER_TAREA(Context context) {
        //  super(context, DATA_BASE_NAME, factory, VERSION);
        super(context, DATA_BASE_NAME, null, VERSION);
    }
    //crear base de datos

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_Managament_tarea.CREAR_TABLA);

    }
    //actualizar la scheme

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       String borrrar="drop table "+DB_Managament_tarea.TABLA_NOMBRE+";";
       db.execSQL(borrrar);
        db.execSQL(DB_Managament_tarea.CREAR_TABLA);


    }
}
