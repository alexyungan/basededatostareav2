package com.example.aes.basededatostareav2;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SimpleCursorAdapter adapter;
    ArrayList<Tarea> list_tarea_recuperado;
    AdapterTarea adapterTarea;
    ListView listView_tareas;
     DB_Managament_tarea db_managament_tarea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        listView_tareas=(ListView)findViewById(R.id.listView_tareas);
         db_managament_tarea =new DB_Managament_tarea(this);
        //Tarea tarea=new Tarea(1,"ir a clases","12:30",1,1);
        //db_managament_tarea.Insetar(tarea);
        View header=getLayoutInflater().inflate(R.layout.row_header,null);
        View footer=getLayoutInflater().inflate(R.layout.row_footer,null);
        listView_tareas.addHeaderView(header);
        listView_tareas.addFooterView(footer);

          //carga los datos de la base de datos
           Recargar_datos();


        /// insertar
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                final AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);

                LayoutInflater inflater =getLayoutInflater();
                View Dialog_crear =inflater.inflate(R.layout.crear_tarea,null);


                final EditText editText_descripcion_tarea =(EditText)Dialog_crear.findViewById(R.id.editText_descripcion_tarea);
                final EditText editText_hora_tarea =(EditText)Dialog_crear.findViewById(R.id.editText_hora_tarea);
                final FloatingActionButton floatingActionButton_crear_tarea =(FloatingActionButton)Dialog_crear.findViewById(R.id.floating_button_crear_tarea);
                final Switch s =(Switch)Dialog_crear.findViewById(R.id.switch_estado_tarea);
                final int[] estado_tarea = {0};
                s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            Toast.makeText(getApplicationContext(),"prioridad Alta",Toast.LENGTH_LONG).show();
                            estado_tarea[0] =1;
                        }         else{
                            Toast.makeText(getApplicationContext(),"prioridad Baja",Toast.LENGTH_LONG).show();
                            estado_tarea[0] =0;
                        }

                    }
                });
                floatingActionButton_crear_tarea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Descripcion=editText_descripcion_tarea.getText().toString();
                        String Hora=editText_hora_tarea.getText().toString();
                        Tarea tarea=new Tarea(1,Descripcion,Hora,estado_tarea[0],0);
                        db_managament_tarea.Insetar(tarea);
                        Recargar_datos();

                        Toast.makeText(getApplicationContext(),"ingreso correcto",Toast.LENGTH_LONG).show();


                    }
                });

                builder.setView(Dialog_crear);
                builder.show();





            }
        });



        //eliminar
        listView_tareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String descripcion =((TextView)v.findViewById(R.id.textView_descripcion_tarea)).getText().toString();

                Toast.makeText(getApplicationContext(),""+descripcion,Toast.LENGTH_LONG).show();
            }
        });

        //actualizar
        SwipeListViewTouchListener touchListener =new SwipeListViewTouchListener(listView_tareas,new SwipeListViewTouchListener.OnSwipeCallback() {
            @Override
            public void onSwipeLeft(ListView listView, int [] reverseSortedPositions) {
              //modificar
                     Modificar_tareas(reverseSortedPositions[0]-1);
            }

            @Override
            public void onSwipeRight(ListView listView, int [] reverseSortedPositions) {

                int position =reverseSortedPositions[0]-1;//la posicion a eliminar
                Log.d("eliminar posicion",""+position);
                Tarea tarea =list_tarea_recuperado.get(position); //recoje datos en esa posicion de la lista
                list_tarea_recuperado.remove(position);//elimina en la lista
                db_managament_tarea.elimnar(tarea.get_id());//elimina en la base
                Recargar_datos();
                Toast.makeText(getApplicationContext(),"Eliminado Correctamente",Toast.LENGTH_LONG).show();
                Log.d("ver............. ",""+reverseSortedPositions[0]);
            }
        },true, false);

        //Escuchadores del listView pra que limine o actulize al deslizar
        listView_tareas.setOnTouchListener(touchListener);
        listView_tareas.setOnScrollListener(touchListener.makeScrollListener());




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //recargar datos este se easta usando

        public  void Recargar_datos(){

            adapterTarea=null;

           list_tarea_recuperado=db_managament_tarea.listar_tareas_en_una_lista();
            adapterTarea=new AdapterTarea(MainActivity.this,list_tarea_recuperado);
            listView_tareas.setAdapter(adapterTarea);
    }
    //recargar datos por cursor
    public void Recargar_datos_cursor(){
         adapter=null;
        Cursor cursor =db_managament_tarea.listart_tarea_cursor();
        String[] from =new String[]{DB_Managament_tarea.COLUMNA_ID_TAREA,DB_Managament_tarea.COLUMNA_DESCRIPCION,DB_Managament_tarea.COLUMNA_HORA};
         int[] to =new int[]{R.id.textView_id_tarea,R.id.textView_descripcion_tarea,R.id.textView_hora_tarea};
         adapter=new SimpleCursorAdapter(getApplicationContext(), R.layout.row_tarea,cursor, from, to, 0) ;

        listView_tareas.setAdapter(adapter);
    }



    //modificar
    public void Modificar_tareas(int id_modificar){
         AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
        View Dialog_view=getLayoutInflater().inflate(R.layout.crear_tarea,null);

        final Tarea tarea1 =list_tarea_recuperado.get(id_modificar);

        final EditText editText_descripcion_tarea =(EditText)Dialog_view.findViewById(R.id.editText_descripcion_tarea);
        final EditText editText_hora_tarea =(EditText)Dialog_view.findViewById(R.id.editText_hora_tarea);
        final FloatingActionButton floatingActionButton_modificar_tarea =(FloatingActionButton)Dialog_view.findViewById(R.id.floating_button_crear_tarea);
        final Switch s =(Switch)Dialog_view.findViewById(R.id.switch_estado_tarea);
        //recuperadatos
        editText_descripcion_tarea.setText(tarea1.getDescripcion());
        editText_hora_tarea.setText(tarea1.getHora());
        if(tarea1.getEstado()==0){
            s.setChecked(false);
        }else{
            s.setChecked(true);
        }
        //ve si cambian el estado
        final int[] estado_tarea = {tarea1.getEstado()};
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getApplicationContext(),"prioridad Alta",Toast.LENGTH_LONG).show();
                    estado_tarea[0] =1;
                }         else{
                    Toast.makeText(getApplicationContext(),"prioridad Baja",Toast.LENGTH_LONG).show();
                    estado_tarea[0] =0;
                }

            }
        });
        //el boton de modificar
        floatingActionButton_modificar_tarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 //recuperamos datos
                String Descripcion=editText_descripcion_tarea.getText().toString();
                String Hora=editText_hora_tarea.getText().toString();
                Tarea modi=new Tarea(tarea1.get_id(),Descripcion,Hora,estado_tarea[0],0);
                db_managament_tarea.actualizar(modi);
                Recargar_datos();

                Toast.makeText(getApplicationContext(),"Modificarion  correcto",Toast.LENGTH_LONG).show();
               // ponemos en la base de datos

            }
        });



        builder.setView(Dialog_view);
        builder.show();





    }

}
