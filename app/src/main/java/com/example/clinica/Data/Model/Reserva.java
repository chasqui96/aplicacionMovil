package com.example.clinica.Data.Model;

import com.example.clinica.Data.util.Metodos;

import java.io.Serializable;

public class Reserva implements Serializable {
    private int reserva_id;
    private int paciente_id;
    private int especialidad_id;
    private int per_id;
    private String turno_fecha ;
    private String turno_estado;

    public Reserva() {
    }
    public Reserva(int reserva_id ,int paciente_id , int especialidad_id,int per_id, String turno_fecha,String turno_estado) {
        this.reserva_id =  reserva_id;
        this.paciente_id = paciente_id;
        this.especialidad_id =  especialidad_id;
        this.per_id = per_id;
        this.turno_fecha = turno_fecha;
        this.turno_estado = turno_estado;
    }
    public int getReserva_id() {
        return reserva_id;
    }
    public void setReserva_id(int reserva_id) {
        this.reserva_id = reserva_id;
    }

    public int getPaciente_id() {
        return paciente_id;
    }

    public void setPaciente_id(int per_id) {
        this.paciente_id = paciente_id;
    }


    public int getEspecialidad_id() {
        return especialidad_id;
    }

    public void setEspecialidad_id(int especialidad_id) {
        this.especialidad_id = especialidad_id;}

    public int getPer_id() {
        return per_id;
    }

    public void setPer_id(int per_id) {
        this.per_id = per_id;
    }

    public String getReserva_fecha() {
        return turno_fecha ;
    }

    public void setReserva_fecha(String turno_fecha) {
        this.turno_fecha  = turno_fecha ;
    }
    public String getTurno_estado() {
        return turno_estado;
    }

    public void setTurno_fecha(String turno_estado) {
        this.turno_estado = turno_estado;
    }
    public String componer(String caracter){
        return Metodos.cadenaComponer(caracter,new Object[]{
                reserva_id,
                 paciente_id,
                especialidad_id,
                per_id,
                turno_fecha,
                turno_estado,

        });
    }

    public Reserva (String cadenaLeida, String caracter){
        this.reserva_id = Integer.parseInt(Metodos.cadenaDescomponer(cadenaLeida,1,caracter));
        this.especialidad_id = Integer.parseInt(Metodos.cadenaDescomponer(cadenaLeida,2,caracter));
        this.per_id = Integer.parseInt(Metodos.cadenaDescomponer(cadenaLeida,3,caracter));
        this.paciente_id = Integer.parseInt(Metodos.cadenaDescomponer(cadenaLeida,3,caracter));
        this.turno_fecha= Metodos.cadenaDescomponer(cadenaLeida,4,caracter);
        this.turno_estado = Metodos.cadenaDescomponer(cadenaLeida,5,caracter);

    }
}
