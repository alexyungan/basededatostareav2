package com.example.aes.basededatostareav2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Aes on 29/5/2016.
 */
public class AdapterTarea extends ArrayAdapter<Tarea> {
    ArrayList<Tarea> tareas;
    public AdapterTarea(Context context, ArrayList<Tarea> tareas) {
        super(context,R.layout.row_tarea , tareas);
        this.tareas=tareas;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =LayoutInflater.from(getContext());
        View item=inflater.inflate(R.layout.row_tarea,null);

        TextView textView_id_tarea =(TextView)item.findViewById(R.id.textView_id_tarea);
        TextView textView_descripcion_tarea =(TextView)item.findViewById(R.id.textView_descripcion_tarea);
        TextView textView_hora_tarea =(TextView)item.findViewById(R.id.textView_hora_tarea);
        View View_estado_tarea =(View)item.findViewById(R.id.View_estado_tarea);
        View View_estado_sincronizacion_tarea =(View)item.findViewById(R.id.View_estado_sincronizacion_tarea);


        textView_id_tarea.setText(Integer.toString(tareas.get(position).get_id()));
        textView_descripcion_tarea.setText(tareas.get(position).getDescripcion());
        textView_hora_tarea.setText(tareas.get(position).getHora());

        //estado tarea
        if(tareas.get(position).getEstado()==0){
            View_estado_tarea.setBackgroundResource(R.color.azul);

        }else{
            View_estado_tarea.setBackgroundResource(R.color.rojo);
        }
        //estado sincronizacion
        if(tareas.get(position).getEstado_sincronizacion()==0){
            View_estado_sincronizacion_tarea.setBackgroundResource(R.color.azul);

        }else{
            View_estado_sincronizacion_tarea.setBackgroundResource(R.color.verde);
        }


        return  (item);
    }
}
