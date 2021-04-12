package com.example.clinica.Views.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.clinica.R;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetalleReserva extends RecyclerView.Adapter<DetalleReserva.ViewHolderReserva>{

    ArrayList<Map<String,String>> listaMain;

    private Context context;
    DetalleReserva.OnItemClickListener onItemClickListener;
    public DetalleReserva(ArrayList<Map<String,String>> listaMain ,DetalleReserva.OnItemClickListener onItemClickListener) {

        this.context = context;
        this.listaMain = listaMain;
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public ViewHolderReserva onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_detalle_reserva, parent, false);
        return new ViewHolderReserva(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetalleReserva.ViewHolderReserva holder, int position) {
        holder.bind(listaMain.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return listaMain.size();
    }

    public interface OnItemClickListener{
        void onItemClick(ArrayList<Map<String,String>> listaMain, int position);
    }

    class ViewHolderReserva extends RecyclerView.ViewHolder{

        //EditText ;
        TextView doctor,paciente,especialidad,fecha,estado;

        public ViewHolderReserva(View itemView) {
            super(itemView);
            doctor = itemView.findViewById(R.id.doctor);
            paciente = itemView.findViewById(R.id.idPaciente);
            especialidad = itemView.findViewById(R.id.espedialidad_desc);
            estado = itemView.findViewById(R.id.estado);
            fecha = itemView.findViewById(R.id.fecha);

        }


        void bind(final  Map<String, String>  listaMain2, final OnItemClickListener onItemClickListener){

            doctor.setText(listaMain2.get("doctor_nombre")+" "+listaMain2.get("doctor_apellido"));
            paciente.setText(listaMain2.get("paciente_nombre")+" "+listaMain2.get("paciente_apellido"));
            especialidad.setText(listaMain2.get("especialidad"));
            fecha.setText(listaMain2.get("turno_fecha"));
            estado.setText(listaMain2.get("turno_estado"));



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
