package com.example.clinica.Data.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.clinica.Data.Model.Personal;

public class SessionPreferences {

    private static final String PREFS_NAME = "SESSION";

    //Constantes sesión
    private static final String PREF_NOMBRE = "PREF_NAME";
    private static final String PREF_ID = "PREF_ID";
    private static final String PREF_USER_NAME = "PREF_USER_NAME";
    private static final String PREF_APELLIDO = "PREF_APELLIDO";
    private static final String PREF_TIPO = "PREF_TIPO";
    private static final String PREF_CI = "PREF_CI";
    private static final String PREF_SESSION = "PREF_SESSION";

    private static final String PREF_PERSONAL    = "PREF_PERSONAL";
    private static final String PREF_PACIENTE = "PREF_PACIENTE";
    private static final String PREF_ESPECIALIDAD = "PREF_ESPECIALIDAD";
    private static final String PREF_CUPO = "PREF_CUPO";
    private static final String PREF_HORARIO = "PREF_HORARIO";
    private static final String PREF_RESERVA = "PREF_RESERVA";
    private final SharedPreferences prefs;
    private  static SessionPreferences INSTANCE;

    public static SessionPreferences get(Context context){
        if (INSTANCE == null){
            INSTANCE = new SessionPreferences(context);
        }
        return INSTANCE;
    }

    public SessionPreferences(Context ctx) {
        prefs = ctx.getApplicationContext().getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
    }

    public boolean estaLogueado(){
        return prefs.getBoolean(PREF_SESSION,false);
    }

    public void guardarUsuario(Personal personal){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_NOMBRE, personal.getPer_nombre());
        editor.putString(PREF_APELLIDO, personal.getPer_apellido());
        editor.putString(PREF_USER_NAME, personal.getPer_username());
        editor.putString(PREF_TIPO, personal.getPer_tipo());
        editor.putInt(PREF_ID, personal.getPer_id());
        editor.putString(PREF_CI, personal.getPer_ci());
        editor.putBoolean(PREF_SESSION,true);

        editor.apply();
    }

    public void cerrarSesion(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_NOMBRE,null);
        editor.putInt(PREF_ID,0);
        editor.putString(PREF_CI,null);
        editor.putBoolean(PREF_SESSION,false);

        editor.apply();
    }

    public Personal getUsuario(){
        Personal _objPersonal = new Personal();
        _objPersonal.setPer_nombre(prefs.getString(PREF_NOMBRE,""));
        _objPersonal.setPer_apellido(prefs.getString(PREF_APELLIDO,""));
        _objPersonal.setPer_username(prefs.getString(PREF_USER_NAME,""));
        _objPersonal.setPer_tipo(prefs.getString(PREF_TIPO,""));
        _objPersonal.setPer_ci(prefs.getString(PREF_CI,""));
        _objPersonal.setPer_id(prefs.getInt(PREF_ID,0));

        return _objPersonal;
    }

    public int getPersonal(){
        return prefs.getInt(PREF_PERSONAL, 1);
    }
    public void setPersonal(int codigo){
        SharedPreferences.Editor objEdit = prefs.edit();
        objEdit.putInt(PREF_PERSONAL, codigo + 1);
        objEdit.apply();
    }


    public int getPaciente(){
        return prefs.getInt(PREF_PACIENTE, 1);
    }
    public void setPaciente(int codigo) {
        SharedPreferences.Editor objEdit = prefs.edit();
        objEdit.putInt(PREF_PACIENTE, codigo + 1);
        objEdit.apply();
    }

    public int getHorario(){
        return prefs.getInt(PREF_HORARIO, 1);
    }
    public void setHorario(int codigo) {
        SharedPreferences.Editor objEdit = prefs.edit();
        objEdit.putInt(PREF_HORARIO, codigo + 1);
        objEdit.apply();
    }



    public int getCupo(){
        return prefs.getInt(PREF_CUPO, 1);
    }
    public void setCupo(int codigo) {
        SharedPreferences.Editor objEdit = prefs.edit();
        objEdit.putInt(PREF_CUPO, codigo + 1);
        objEdit.apply();
    }


    public int getEspecialidad(){
        return prefs.getInt(PREF_ESPECIALIDAD, 1);
    }
    public void setEspecialidad(int codigo){
        SharedPreferences.Editor objEdit = prefs.edit();
        objEdit.putInt(PREF_ESPECIALIDAD, codigo + 1);
        objEdit.apply();
    }

    public int getReserva(){
        return prefs.getInt(PREF_RESERVA, 1);
    }
    public void setReserva(int codigo){
        SharedPreferences.Editor objEdit = prefs.edit();
        objEdit.putInt(PREF_RESERVA, codigo + 1);
        objEdit.apply();
    }
}
