package com.example.clinica.Data.Model;

import com.example.clinica.Data.util.Metodos;

import java.io.Serializable;

public class Paciente implements Serializable {

    private int id;
    private String paciente_nombre;
    private String paciente_apellido;
    private String paciente_fecha_nac;
    private String paciente_cedula;
    private String paciente_telefono;
    private String paciente_estado;

    public Paciente() {
    }


    public int getPaciente_id() {
        return id;
    }

    public void setPaciente_id(int id) {
        this.id = id;
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
        return paciente_cedula;
    }

    public void setPaciente_ci(String paciente_ci) {
        this.paciente_cedula = paciente_ci;
    }

    public String getPaciente_fechana() {
        return paciente_fecha_nac;
    }

    public void setPaciente_fechana(String paciente_fecha_nac) {
        this.paciente_fecha_nac = paciente_fecha_nac;
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



}

