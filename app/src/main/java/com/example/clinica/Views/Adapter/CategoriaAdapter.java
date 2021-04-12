package com.example.clinica.Views.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clinica.Data.Model.Categoria;
import com.example.clinica.R;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.MyViewHolder> {

    private List<Categoria> categorias;
    private CategoriaAdapterListener listener;

    public CategoriaAdapter(List<Categoria> categorias, CategoriaAdapterListener listener) {
        this.categorias = categorias;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_categoria,parent,false);
        return new MyViewHolder(item_view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Categoria _objCategoria = categorias.get(position);

        holder.tvNombreCategoria.setText(_objCategoria.getNombre());
    }

    @Override
    public int getItemCount() {
        int i = 0;
        if(categorias.size() > 0){
            i = categorias.size();
        }
        return i;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvNombreCategoria;

        public MyViewHolder(View view){
            super(view);

            tvNombreCategoria = view.findViewById(R.id.tvNombreCategoria);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.OnItemClickListener(getAdapterPosition());
        }
    }

    public interface CategoriaAdapterListener{
        void OnItemClickListener(int position);
    }
}
