package com.example.clinica.Views.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.clinica.Data.Model.Personal;
import com.example.clinica.Data.Model.Profesor;
import com.example.clinica.R;

import java.util.List;

public class PersonalIndexAdapter extends RecyclerView.Adapter<PersonalIndexAdapter.ViewHolderPersonal> {
    List<Personal> listaPersonal;
    OnItemClickListener onItemClickListener;

    public PersonalIndexAdapter(List<Personal> listaPersonal, OnItemClickListener onItemClickListener) {
        this.listaPersonal = listaPersonal;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolderPersonal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_personal,parent, false);
        return new ViewHolderPersonal(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolderPersonal holder, int position) {
        holder.bind(listaPersonal.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return listaPersonal.size();
    }

    public interface OnItemClickListener{
        void onItemClick(Personal personal, int position);
    }

    class ViewHolderPersonal extends RecyclerView.ViewHolder{
        TextView nombres,apellidos,ci,password,username,spn,id;
        ///ImageView d, tel, mail;
        public ViewHolderPersonal(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            nombres = itemView.findViewById(R.id.rictvNombre);
            apellidos = itemView.findViewById(R.id.rictvApellido);
            ci = itemView.findViewById(R.id.rictvCi);
            username = itemView.findViewById(R.id.rictvTitulo);
            spn = itemView.findViewById(R.id.rictvTipo);
        }

        void bind(final Personal personal, final OnItemClickListener itemClickListener){
            Log.d("acaes",personal.getPer_id()+"");
            id.setId(    personal.getPer_id());
            nombres.setText(personal.getPer_nombre());
            apellidos.setText(personal.getPer_apellido());
            ci.setText(personal.getPer_ci());
           // password.setText(personal.getPer_password());
            username.setText(personal.getPer_username()) ;
            spn.setText(personal.getPer_tipo());

            //id.setVisibility(!personal.getPer_id().isEmpty  () ? View.VISIBLE : View.INVISIBLE);
            apellidos.setVisibility(personal.getPer_apellido().isEmpty() ? View.INVISIBLE : View.VISIBLE);
            ci.setVisibility(personal.getPer_ci().isEmpty() ? View.INVISIBLE : View.VISIBLE);
           // password.setVisibility(personal.getPer_password().isEmpty() ? View.INVISIBLE : View.VISIBLE);
            spn.setVisibility(personal.getPer_tipo().isEmpty() ? View.INVISIBLE : View.VISIBLE);
            username.setVisibility(personal.getPer_username().isEmpty() ? View.INVISIBLE : View.VISIBLE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(personal, getAdapterPosition());
                }
            });
        }
    }
}
