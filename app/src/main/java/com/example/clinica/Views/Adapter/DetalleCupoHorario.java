package com.example.clinica.Views.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clinica.Data.Model.Especialidad;
import com.example.clinica.R;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetalleCupoHorario extends RecyclerView.Adapter<DetalleCupoHorario.ViewHolderCupo>{

    ArrayList<Map<String,String>> listaMain;

    private Context context;
    DetalleCupoHorario.OnItemClickListener onItemClickListener;
    public DetalleCupoHorario(ArrayList<Map<String,String>> listaMain ,DetalleCupoHorario.OnItemClickListener onItemClickListener) {

        this.context = context;
        this.listaMain = listaMain;
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public ViewHolderCupo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_detalle_cupo, parent, false);
        return new ViewHolderCupo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetalleCupoHorario.ViewHolderCupo holder, int position) {
        holder.bind(listaMain.get(position), onItemClickListener);
    }

        @Override
    public int getItemCount() {
        return listaMain.size();
    }

    public interface OnItemClickListener{
        void onItemClick(ArrayList<Map<String,String>> listaMain, int position);
    }

    class ViewHolderCupo extends RecyclerView.ViewHolder{

        //EditText ;
        TextView tituloEspecialidad,doctor,dias,cantidad, reservado,hora_desde,hora_hasta,idespe;
        ImageView lleno;
        public ViewHolderCupo(View itemView) {
            super(itemView);
            doctor = itemView.findViewById(R.id.idDoctor);
            dias = itemView.findViewById(R.id.idDia);
            cantidad = itemView.findViewById(R.id.cupoDisponible);
            reservado = itemView.findViewById(R.id.cupoReservado);
            tituloEspecialidad = itemView.findViewById(R.id.idEspecialidad);
            hora_desde = itemView.findViewById(R.id.desde);
            hora_hasta = itemView.findViewById(R.id.hasta);
            idespe = itemView.findViewById(R.id.idEsoer);
            lleno = itemView.findViewById(R.id.rictvinactivo);

        }


         void bind(final  Map<String, String>  listaMain2, final OnItemClickListener onItemClickListener){

           doctor.setText(listaMain2.get("per_nombre")+" "+listaMain2.get("per_apellido"));
           dias.setText(listaMain2.get("dias")+"-");
           cantidad.setText(listaMain2.get("cantidad_cupo"));
           reservado.setText(listaMain2.get("cupo_reservado"));
           tituloEspecialidad.setText(listaMain2.get("especialidad_descripcion"));
           hora_desde.setText(listaMain2.get("horario_desde"));
           hora_hasta.setText(listaMain2.get("horario_hasta"));
           idespe.setText(listaMain2.get("idespecialidad"));

             if(listaMain2.get("cantidad_cupo").equals(listaMain2.get("cupo_reservado"))){
                 Log.d("anteSSSSs","ACTIVO");
                 lleno.setVisibility(View.VISIBLE);

             }else{
                 Log.d("despues","INACTIVO");
                 lleno.setVisibility(View.INVISIBLE);
             }

           // nombre.setVisibility(especialidad.getEspecialidad_descripcion().isEmpty() ? View.INVISIBLE : View.VISIBLE);
            //estado.setVisibility(especialidad.getEspecialidad_estado().isEmpty() ? View.INVISIBLE : View.VISIBLE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemClickListener.onItemClick(listaMain, getAdapterPosition());

                }
            });
        }



    }


}
