package com.example.clinica.Data.Model;

import android.widget.SpinnerAdapter;

import com.example.clinica.Data.util.Metodos;

import java.io.Serializable;

public class Personal implements Serializable  {

    private int id;
    private String per_nombre;
    private String per_apellido;
    private String per_cedula;
    private String per_telefono;
    private String user;
    private String password;
    private String tipo_persona;
    private String per_estado;



    public int getPer_id() {
        return id;
    }

    public void setPer_id(int id) {
        this.id = id;
    }

    public String getPer_nombre() {
        return per_nombre;
    }

    public void setPer_nombre(String per_nombre) {
        this.per_nombre = per_nombre;
    }

    public String getPer_apellido() {
        return per_apellido;
    }

    public void setPer_apellido(String per_apellido) {
        this.per_apellido = per_apellido;
    }

    public String getPer_ci() {
        return per_cedula;
    }

    public void setPer_ci(String per_cedula) {
        this. per_cedula =  per_cedula;
    }

    public String getPer_username() {
        return user;
    }

    public void setPer_username(String user) {
        this.user = user;
    }

    public String getPer_password() {
        return password;
    }

    public void setPer_password(String password) {
        this.password= password;;
    }

    public String getPer_tipo() {
        return tipo_persona;
    }

    public void setPer_tipo(String tipo_persona) {
        this.tipo_persona = tipo_persona;
    }

    public String getPer_telefono() {
        return per_telefono;
    }

    public void setPer_telefono(String  per_telefono) {
        this. per_telefono =  per_telefono;
    }

    public String getPer_estado() {
        return per_estado;
    }

    public void setPer_estado(String  per_estado) {
        this. per_estado =  per_estado;
    }

}

