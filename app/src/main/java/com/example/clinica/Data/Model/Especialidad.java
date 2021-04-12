package com.example.clinica.Data.Model;

import com.example.clinica.Data.util.Metodos;

import java.io.Serializable;

public class Especialidad implements Serializable {
    private int especialidad_id;
    private String especialidad_descripcion;
    private String especialidad_estado;
    private Boolean especialidad_seleccionado;
    public Especialidad() {
    }
    public Especialidad(int especialidad_id, String especialidad_descripcion, String especialidad_estado ,boolean especialidad_seleccionado) {
        this.especialidad_id =  especialidad_id;
        this.especialidad_descripcion = especialidad_descripcion;
        this.especialidad_estado = especialidad_estado;
        this.especialidad_seleccionado = especialidad_seleccionado;
    }

    public int getEspecialidad_id() {
        return especialidad_id;
    }

    public void setEspecialidad_id(int especialidad_id) {
        this.especialidad_id = especialidad_id;
    }

    public String getEspecialidad_descripcion() {
        return especialidad_descripcion;
    }

    public void setEspecialidad_descripcion(String especialidad_descripcion){
        this.especialidad_descripcion = especialidad_descripcion;
    }

    public String getEspecialidad_estado() {
        return especialidad_estado;
    }

    public void setEspecialidad_estado (String especialidad_estado) {
        this.especialidad_estado = especialidad_estado;
    }

    public Boolean getEspecialidad_seleccionado() {
        return especialidad_seleccionado;
    }

    public void setEspecialidad_seleccionado(Boolean especialidad_seleccionado) {
        this.especialidad_seleccionado = especialidad_seleccionado;
    }

    public String componer(String caracter){
        return Metodos.cadenaComponer(caracter,new Object[]{
               especialidad_id,
                especialidad_descripcion,
                especialidad_estado,
        });
    }

    public Especialidad (String cadenaLeida, String caracter){
        this.especialidad_id = Integer.parseInt(Metodos.cadenaDescomponer(cadenaLeida,1,caracter));
        this.especialidad_descripcion = Metodos.cadenaDescomponer(cadenaLeida,2,caracter);
        this.especialidad_estado = Metodos.cadenaDescomponer(cadenaLeida,3,caracter);

    }


}
