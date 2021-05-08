package com.example.clinica.Views.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clinica.Data.Model.Especialidad;
import com.example.clinica.R;

import java.util.List;

public class EspecialidadIndexAdapter extends RecyclerView.Adapter<EspecialidadIndexAdapter.ViewHolderEspecialidad>{


    List<Especialidad> listaEspecialidad;
    EspecialidadIndexAdapter.OnItemClickListener onItemClickListener;

    public EspecialidadIndexAdapter(List<Especialidad> listaEspecialidad, EspecialidadIndexAdapter.OnItemClickListener onItemClickListener) {
        this.listaEspecialidad = listaEspecialidad;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public EspecialidadIndexAdapter.ViewHolderEspecialidad onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_especialidad,parent, false);
        return new EspecialidadIndexAdapter.ViewHolderEspecialidad(view);
    }



    @Override
    public void onBindViewHolder(@NonNull EspecialidadIndexAdapter.ViewHolderEspecialidad holder, int position) {
        holder.bind(listaEspecialidad.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return listaEspecialidad.size();
    }

    public interface OnItemClickListener{
        void onItemClick(Especialidad especialidad, int position);
    }

    class ViewHolderEspecialidad extends RecyclerView.ViewHolder{
        TextView descripcion;
        //ImageView activo,inactivo;

        public ViewHolderEspecialidad(View itemView) {
            super(itemView);

            descripcion= itemView.findViewById(R.id.rictvNombre);
            // activo = itemView.findViewById(R.id.rictvestado);
             //inactivo=  itemView.findViewById(R.id.rictvinactivo);

        }

        void bind(final Especialidad especialidad, final EspecialidadIndexAdapter.OnItemClickListener itemClickListener){

            descripcion.setText(especialidad.getEspecialidad_descripcion());


            descripcion.setVisibility(especialidad.getEspecialidad_descripcion().isEmpty() ? View.INVISIBLE : View.VISIBLE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(especialidad, getAdapterPosition());
                }
            });
        }
    }
}
