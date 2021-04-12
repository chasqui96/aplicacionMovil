package com.example.clinica.Data.Model;

import com.example.clinica.Data.util.Metodos;

import java.io.Serializable;

public class Horario implements Serializable {
          private int horario_id;
        private int especialidad_id;
        private int per_id;
        private String doctor_registro ;
        private String especialidad_estado ;
        private String dias ;
        private String horario_desde ;
        private String horario_hasta ;

    public Horario() {
    }
    public Horario(int horario_id , int especialidad_id,int per_id, String doctor_registro,String dias, String horario_desde, String horario_hasta ) {
        this.horario_id =  horario_id;
        this.especialidad_id =  especialidad_id;
        this.per_id = per_id;
        this.doctor_registro = doctor_registro;
        this.dias = dias;
        this.horario_desde = horario_desde;
        this.horario_hasta =  horario_hasta;
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

        public String getHorario_doctorRegistro() {
            return doctor_registro;
        }

        public void setHorario_doctorRegistro (String doctor_registro) {
            this.doctor_registro = doctor_registro;
        }
        public String getHorario_desde() {
            return horario_desde;
        }

        public void setHorario_desde(String horario_desde) {
            this.horario_desde = horario_desde;
        }
        public String getHorario_hasta() {
            return horario_hasta;
        }

        public void setHorario_hasta(String horario_hasta) {
            this.horario_hasta = horario_hasta;
        }
        public String getHorario_dias() {
            return dias;
        }

        public void setHorario_dias(String dias) {
            this.dias = dias;
        }

    public String getEspecialidad_estado() {
        return especialidad_estado;
    }

    public void setEspecialidad_estado (String especialidad_estado) {
        this.especialidad_estado = especialidad_estado;
    }
    public String componer(String caracter){
        return Metodos.cadenaComponer(caracter,new Object[]{
                horario_id,
                especialidad_id,
                per_id,
                doctor_registro,
                dias,
                horario_desde,
                horario_hasta
        });
    }

    public Horario (String cadenaLeida, String caracter){
        this.horario_id = Integer.parseInt(Metodos.cadenaDescomponer(cadenaLeida,1,caracter));
        this.especialidad_id = Integer.parseInt(Metodos.cadenaDescomponer(cadenaLeida,2,caracter));
        this.per_id = Integer.parseInt(Metodos.cadenaDescomponer(cadenaLeida,3,caracter));
        this.doctor_registro= Metodos.cadenaDescomponer(cadenaLeida,4,caracter);
        this.dias = Metodos.cadenaDescomponer(cadenaLeida,5,caracter);
        this.horario_desde = Metodos.cadenaDescomponer(cadenaLeida,6,caracter);
        this.horario_hasta = Metodos.cadenaDescomponer(cadenaLeida,7,caracter);
    }


}
