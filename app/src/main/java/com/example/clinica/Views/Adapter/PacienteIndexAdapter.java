package com.example.clinica.Views.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        ImageView activo,inactivo;
        public ViewHolderPaciente(View itemView) {
            super(itemView);

            nombres = itemView.findViewById(R.id.rictvNombre);
            apellidos = itemView.findViewById(R.id.rictvApellido);
            ci = itemView.findViewById(R.id.rictvCi);
            fechana = itemView.findViewById(R.id.rictvFechana);
            activo = itemView.findViewById(R.id.rictvestado);
            inactivo=  itemView.findViewById(R.id.rictvinactivo);
        }

        void bind(final Paciente paciente, final PacienteIndexAdapter.OnItemClickListener itemClickListener){
            Log.d("payaso",paciente.getPaciente_fechana()+"");
            nombres.setText(paciente.getPaciente_nombre());
            apellidos.setText(paciente.getPaciente_apellido());
            ci.setText(paciente.getPaciente_ci());
            if(paciente.getPaciente_estado().equals("ACTIVO")){
                Log.d("anteSSSSs","ACTIVO");
                activo.setVisibility(View.VISIBLE);
                inactivo.setVisibility(View.INVISIBLE);
            }else{
                activo.setVisibility(View.INVISIBLE);
                inactivo.setVisibility(View.VISIBLE);
            }

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
