package com.example.aes.basededatostareav2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aes on 29/5/2016.
 */
public class DB_Managament_tarea {
    public  static  String TABLA_NOMBRE="tarea";
    public  static  String COLUMNA_ID_TAREA ="_id";
    public  static  String COLUMNA_DESCRIPCION="descripcion";
    public  static String COLUMNA_HORA="hora";
    public  static  String COLUMNA_ESTADO="estado";
    public  static  String COLUMNA_ESTADO_SINCRONIZACION="estado_sincronizacion";

    public  static  String CREAR_TABLA="create table  "+TABLA_NOMBRE+" ( "
            +COLUMNA_ID_TAREA+" integer primary key autoincrement , "+
            COLUMNA_DESCRIPCION+" text,"+
            COLUMNA_HORA+" text ,"+
            COLUMNA_ESTADO+" integer ,"+
            COLUMNA_ESTADO_SINCRONIZACION+" integer);";

    private  DBHELPER_TAREA dbhelper_tarea;
    private SQLiteDatabase db;
    //constructor


    public DB_Managament_tarea(Context context) {
        dbhelper_tarea =new DBHELPER_TAREA(context);//instancianos db helper para que cree la scheme
        db=dbhelper_tarea.getWritableDatabase();//ponemos la db a la scheme que tenemos

    }

    //convertir a context values
    public ContentValues convertir_context_values(Tarea tarea){
        ContentValues contentValues =new ContentValues();
       // contentValues.put(COLUMNA_ID_TAREA,tarea.get_id());
        contentValues.put(COLUMNA_DESCRIPCION,tarea.getDescripcion());
        contentValues.put(COLUMNA_HORA,tarea.getHora());
        contentValues.put(COLUMNA_ESTADO,tarea.getEstado());
        contentValues.put(COLUMNA_ESTADO_SINCRONIZACION,tarea.getEstado_sincronizacion());
        return contentValues;
    }

    //insertar
    public void  Insetar(Tarea tarea){

        //tabla,NullcolumHack,context values
        //NullcolumHack es un añadido para que se compatible con otras base de datos
        Log.d("getDescripcion",tarea.getDescripcion());
        Log.d("getEstado",Integer.toString(tarea.getEstado()));
        Log.d("getHora",tarea.getHora());

        db.insert(TABLA_NOMBRE,null,convertir_context_values(tarea));


    }
    //actualizar
    public  void  actualizar(Tarea tarea){
        //db.update(table,content_values, clausula_where,argumento_where);
        String [] clausula =new String[]{Integer.toString(tarea.get_id())};
        db.update(TABLA_NOMBRE,convertir_context_values(tarea),COLUMNA_ID_TAREA +"=?",clausula);

    }
    //eliminar
    public  void  elimnar(int id_a_eliminar){

        //db.delete(Tabla,clausula_where,Argumento_where);
        String[] clausula= new String[]{Integer.toString(id_a_eliminar)};
        db.delete(TABLA_NOMBRE,COLUMNA_ID_TAREA+"=?",clausula);

    }
    //listar
    public Cursor listart_tarea_cursor(){
        // db.query(nombre_tabla, String[] columnas , String Selection,String[] SelecttionArg,String groupby,string having,String OrederBy)
        //  String[] columnas =new String[]{COLUMNA_ID,COLUMNA_DESCRIPCION,COLUMNA_HORA,COLUMNA_ESTADO};
        String[] columnas=new String[]{COLUMNA_ID_TAREA,COLUMNA_DESCRIPCION,COLUMNA_HORA,COLUMNA_ESTADO,COLUMNA_ESTADO_SINCRONIZACION};
           return  db.query(TABLA_NOMBRE,columnas,null,null,null,null,null);
    }
    public ArrayList<Tarea> listar_tareas_en_una_lista(){
        ArrayList<Tarea> list_tarea=new ArrayList<Tarea>();
        String[] columnas=new String[]{COLUMNA_ID_TAREA,COLUMNA_DESCRIPCION,COLUMNA_HORA,COLUMNA_ESTADO,COLUMNA_ESTADO_SINCRONIZACION};
        Cursor cursor = db.query(TABLA_NOMBRE,columnas,null,null,null,null,null);
        //si existen datos en las tablas
        if (cursor.moveToFirst()){//ve si eiste elemtos en el primer row
           do {
                String item_id=cursor.getString(0);
                String item_descripcion=cursor.getString(1);
                String item_hora=cursor.getString(2);
                String item_estado=cursor.getString(3);
                String item_estado_sincronizacion=cursor.getString(4);

                Tarea item=new Tarea(Integer.parseInt(item_id),item_descripcion,item_hora,Integer.parseInt(item_estado),Integer.parseInt(item_estado_sincronizacion));

                list_tarea.add(item);
            }while (cursor.moveToNext());
        }
        return list_tarea;

    }


}
