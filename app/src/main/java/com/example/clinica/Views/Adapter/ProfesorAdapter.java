package com.example.clinica.Views.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clinica.Data.Model.Profesor;
import com.example.clinica.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfesorAdapter extends RecyclerView.Adapter<ProfesorAdapter.MyViewHolder> {

    private List<Profesor> profesores;
    private ProfesorAdapterListener listener;

    public ProfesorAdapter(List<Profesor> profesores,ProfesorAdapterListener listener){
        this.profesores = profesores;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_profesor,parent,false);
        return new MyViewHolder(item_view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Profesor _objProfesor = profesores.get(position);

        Picasso.get().load(_objProfesor.getFoto()).into(holder.imgProfesor);
        holder.tvNombre.setText(_objProfesor.getNombre());
        holder.tvEmail.setText(_objProfesor.getEmail());
    }

    @Override
    public int getItemCount() {
        int i = 0;
        if(profesores.size() > 0){
            i = profesores.size();
        }

        return i;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imgProfesor;
        public TextView tvNombre,tvEmail;

        public MyViewHolder(View view){
            super(view);

            imgProfesor = view.findViewById(R.id.imgProfesor);
            tvNombre = view.findViewById(R.id.tvNombre);
            tvEmail = view.findViewById(R.id.tvEmail);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.OnItemClickListener(getAdapterPosition());
        }
    }

    public interface ProfesorAdapterListener{
        void OnItemClickListener(int position);
    }

}
