package com.example.clinica.Views.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clinica.Data.Model.Paciente;

import com.example.clinica.R;

import java.util.List;

public class PacienteIndexAdapter extends RecyclerView.Adapter<PacienteIndexAdapter.ViewHolderPaciente>{
    List<Paciente> listaPaciente;
    PacienteIndexAdapter.OnItemClickListener onItemClickListener;

    public PacienteIndexAdapter(List<Paciente> listaPaciente, PacienteIndexAdapter.OnItemClickListener onItemClickListener) {
        this.listaPaciente = listaPaciente;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PacienteIndexAdapter.ViewHolderPaciente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_paciente,parent, false);
        return new PacienteIndexAdapter.ViewHolderPaciente(view);
    }



    @Override
    public void onBindViewHolder(@NonNull PacienteIndexAdapter.ViewHolderPaciente holder, int position) {
        holder.bind(listaPaciente.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return listaPaciente.size();
    }

    public interface OnItemClickListener{
        void onItemClick(Paciente paciente, int position);
    }

    class ViewHolderPaciente extends RecyclerView.ViewHolder{
        TextView nombres,apellidos,ci,fechana;
        ///ImageView d, tel, mail;
        public ViewHolderPaciente(View itemView) {
            super(itemView);

            nombres = itemView.findViewById(R.id.rictvNombre);
            apellidos = itemView.findViewById(R.id.rictvApellido);
            ci = itemView.findViewById(R.id.rictvCi);
            //username = itemView.findViewById(R.id.rictvTitulo);
            fechana = itemView.findViewById(R.id.rictvFechana);
            //password = itemView.findViewById(R.id.rictvCi);
        }

        void bind(final Paciente paciente, final PacienteIndexAdapter.OnItemClickListener itemClickListener){

            nombres.setText(paciente.getPaciente_nombre());
            apellidos.setText(paciente.getPaciente_apellido());
            ci.setText(paciente.getPaciente_ci());
            //password.setText(personal.getPer_password());
            fechana.setText(paciente.getPaciente_fechana()) ;


            nombres.setVisibility(paciente.getPaciente_nombre().isEmpty() ? View.INVISIBLE : View.VISIBLE);
            apellidos.setVisibility(paciente.getPaciente_apellido().isEmpty() ? View.INVISIBLE : View.VISIBLE);
            ci.setVisibility(paciente.getPaciente_ci().isEmpty() ? View.INVISIBLE : View.VISIBLE);
            //password.setVisibility(personal.getPer_password().isEmpty() ? View.INVISIBLE : View.VISIBLE);
            fechana.setVisibility(paciente.getPaciente_fechana().isEmpty() ? View.INVISIBLE : View.VISIBLE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(paciente, getAdapterPosition());
                }
            });
        }
    }
}
