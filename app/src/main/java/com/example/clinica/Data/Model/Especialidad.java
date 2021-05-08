package com.example.clinica.Data.Model;

import com.example.clinica.Data.util.Metodos;

import java.io.Serializable;

public class Especialidad implements Serializable {
    private int id;
    private String espe_descrip;

    public Especialidad() {
    }


    public int getEspecialidad_id() {
        return id;
    }

    public void setEspecialidad_id(int id) {
        this.id = id;
    }

    public String getEspecialidad_descripcion() {
        return espe_descrip;
    }

    public void setEspecialidad_descripcion(String espe_descrip){
        this.espe_descrip = espe_descrip;
    }





}
