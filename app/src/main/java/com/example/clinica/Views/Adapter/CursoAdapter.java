package com.example.clinica.Views.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clinica.Data.Model.Curso;
import com.example.clinica.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.MyViewHolder> {

    private List<Curso> cursos;
    private CursoAdapterListener listener;

    public CursoAdapter(List<Curso> cursos,CursoAdapterListener listener) {
        this.cursos = cursos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_curso,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Curso _objCurso = cursos.get(position);

        holder.tvTitulo.setText(_objCurso.getNombre());
        holder.tvNombreProfesor.setText(_objCurso.getProfesor().getNombre());
        holder.tvDescripcion.setText(_objCurso.getDescripcion());

        Picasso.get().load(_objCurso.getIcono()).into(holder.imgIcono);
        Picasso.get().load(_objCurso.getProfesor().getFoto()).into(holder.imgProfesor);

    }

    @Override
    public int getItemCount() {
        int i = 0;

        if(cursos.size() > 0){
            i = cursos.size();
        }

        return i;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imgIcono,imgProfesor;
        public TextView tvTitulo,tvDescripcion,tvNombreProfesor;

        public  MyViewHolder(View view){
            super(view);
            imgIcono = view.findViewById(R.id.imgIcono);
            imgProfesor = view.findViewById(R.id.imgProfesor);
            tvTitulo = view.findViewById(R.id.tvTitulo);
            tvDescripcion = view.findViewById(R.id.tvDescripcion);
            tvNombreProfesor = view.findViewById(R.id.tvNombreProfesor);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.OnItemClicked(cursos.get(getAdapterPosition()).getId());
        }
    }

    public interface CursoAdapterListener{
        void OnItemClicked(int id);
    }
}
