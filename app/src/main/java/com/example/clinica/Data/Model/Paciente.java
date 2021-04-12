package com.example.clinica.Data.Model;

import com.example.clinica.Data.util.Metodos;

import java.io.Serializable;

public class Paciente implements Serializable {

    private int paciente_id;
    private String paciente_nombre;
    private String paciente_apellido;
    private String paciente_fecha_nacimiento;
    private String paciente_ci;
    private String paciente_telefono;
    private String paciente_estado;

    public Paciente() {
    }

    public Paciente(int paciente_id, String paciente_nombre, String paciente_apellido, String paciente_fecha_nacimiento,String paciente_ci, String paciente_telefono, String paciente_estado ) {
        this.paciente_id=  paciente_id;
        this.paciente_nombre = paciente_nombre;
        this.paciente_apellido = paciente_apellido;
        this.paciente_fecha_nacimiento = paciente_fecha_nacimiento;
        this.paciente_ci = paciente_ci;
        this.paciente_telefono = paciente_telefono;
        this.paciente_estado = paciente_estado;
    }

    public int getPaciente_id() {
        return paciente_id;
    }

    public void setPaciente_id(int per_id) {
        this.paciente_id = paciente_id;
    }

    public String getPaciente_nombre() {
        return paciente_nombre;
    }

    public void setPaciente_nombre(String paciente_nombre) {
        this.paciente_nombre = paciente_nombre;
    }

    public String getPaciente_apellido() {
        return paciente_apellido;
    }

    public void setPaciente_apellido(String paciente_apellido) {
        this.paciente_apellido = paciente_apellido;
    }

    public String getPaciente_ci() {
        return paciente_ci;
    }

    public void setPaciente_ci(String paciente_ci) {
        this.paciente_ci = paciente_ci;
    }

    public String getPaciente_fechana() {
        return paciente_fecha_nacimiento;
    }

    public void setPaciente_fechana(String paciente_fecha_nacimiento) {
        this.paciente_fecha_nacimiento = paciente_fecha_nacimiento;
    }


    public String getPaciente_telefono() {
        return paciente_telefono;
    }

    public void setPaciente_telefono(String paciente_telefono) {
        this.paciente_telefono = paciente_telefono;
    }

    public String getPaciente_estado() {
        return paciente_estado;
    }

    public void setPaciente_estado(String paciente_estado) {
        this.paciente_estado = paciente_estado;
    }

    public String componer(String caracter){
        return Metodos.cadenaComponer(caracter,new Object[]{
                paciente_id,
                paciente_nombre,
                paciente_apellido,
                paciente_fecha_nacimiento,
                paciente_ci,
                paciente_telefono,
                paciente_estado
        });
    }

    public Paciente(String cadenaLeida, String caracter){
        this.paciente_id = Integer.parseInt(Metodos.cadenaDescomponer(cadenaLeida,1,caracter));
        this.paciente_nombre = Metodos.cadenaDescomponer(cadenaLeida,2,caracter);
        this.paciente_apellido = Metodos.cadenaDescomponer(cadenaLeida,3,caracter);
        this.paciente_ci= Metodos.cadenaDescomponer(cadenaLeida,4,caracter);
        this.paciente_telefono= Metodos.cadenaDescomponer(cadenaLeida,5,caracter);
        this.paciente_estado= Metodos.cadenaDescomponer(cadenaLeida,6,caracter);
        this.paciente_fecha_nacimiento = Metodos.cadenaDescomponer(cadenaLeida,7,caracter);
    }


}

