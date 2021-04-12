package com.example.clinica.Data.Model;

import com.example.clinica.Data.util.Metodos;

import java.io.Serializable;

public class Cupo  implements Serializable {
    private int cupo_id;
    private int horario_id;
    private int especialidad_id;
    private int per_id;
    private String fecha_cupo;
    private String cupo_cantidad;
    private String cupo_reservado;

    public Cupo() {
    }

    public Cupo(int cupo_id, int horario_id , int especialidad_id, int per_id, String fecha_cupo, String cupo_cantidad,String cupo_reservado ) {
        this.cupo_id = cupo_id;
        this.horario_id = horario_id;
        this.especialidad_id =  especialidad_id;
        this.per_id = per_id;
        this.fecha_cupo = fecha_cupo;
        this.cupo_cantidad = cupo_cantidad;
        this.cupo_reservado = cupo_reservado;
    }
    public int getCupo_id() {
        return cupo_id;
    }

    public void setCupo_id(int cupo_id) {
        this.cupo_id = cupo_id;
    }

    public int getHorario_id() {
        return horario_id;
    }

    public void setHorairo_id(int horaario_id) {
        this.horario_id = horario_id;
    }

    public int getEspecialidad_id() {
        return especialidad_id;
    }

    public void setEspecialidad_id(int especialidad_id) {
        this.especialidad_id = especialidad_id;
    }

    public int getPer_id() {
        return per_id;
    }

    public void setPer_id(int per_id) {
        this.per_id = per_id;
    }


    public String getCupo_fechaCupo() {
        return fecha_cupo;
    }

    public void setCupo_fechaCupo(String fechaCupo) {
        this.fecha_cupo = fecha_cupo;
    }

    public String  getCupo_cantidad() {
        return cupo_cantidad;
    }

    public void setCupo_cantidad(String  cupo_cantidad) {
        this.cupo_cantidad = cupo_cantidad;
    }

    public String  getCupo_reservado() {
        return cupo_reservado;
    }

    public void setCupo_reservado(String  cupo_reservado) {
        this.cupo_reservado = cupo_reservado;
    }



    public String componer(String caracter){
        return Metodos.cadenaComponer(caracter,new Object[]{
                cupo_id,
                horario_id,
                especialidad_id,
                per_id,
                fecha_cupo,
                cupo_cantidad,
                cupo_reservado,

        });
    }

    public Cupo(String cadenaLeida, String caracter){
        this.cupo_id = Integer.parseInt(Metodos.cadenaDescomponer(cadenaLeida,1,caracter));
        this.horario_id = Integer.parseInt(Metodos.cadenaDescomponer(cadenaLeida,2,caracter));
        this.especialidad_id = Integer.parseInt(Metodos.cadenaDescomponer(cadenaLeida,3,caracter));
        this.per_id = Integer.parseInt(Metodos.cadenaDescomponer(cadenaLeida,4,caracter));
        this.fecha_cupo= Metodos.cadenaDescomponer(cadenaLeida,5,caracter);
        this.cupo_cantidad =  Metodos.cadenaDescomponer(cadenaLeida,6,caracter);
        this.cupo_reservado = Metodos.cadenaDescomponer(cadenaLeida,7,caracter);
    }




















}
