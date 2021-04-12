package com.example.clinica.Views.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clinica.Data.Model.Cupo;
import com.example.clinica.Data.Model.Horario;
import com.example.clinica.Data.Model.Paciente;
import com.example.clinica.Data.Model.Personal;
import com.example.clinica.Data.Model.Reserva;
import com.example.clinica.Data.Preferences.SessionPreferences;
import com.example.clinica.R;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReservaTurnoActivity extends AppCompatActivity {

    @BindView(R.id.acEtFecha)
    EditText fecha;


    @BindView(R.id.acEtPaciente)
    Spinner spn;


    @BindView(R.id.idEspecialidad)
    TextView especialidad;

    @BindView(R.id.idDia)
    TextView dias;
    @BindView(R.id.idDoctor)
    TextView doctor;

    private ProgressDialog pdDialogo;
    private SessionPreferences prefs;


    ArrayList<String> listaPaciente;
    List<Paciente> tempListaPaciente = new ArrayList<>();

    Boolean bNuevo = true , bModificado = false;

    private int mYearIni, mMonthIni, mDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    Calendar C = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_turno);
        sMonthIni = C.get(Calendar.MONTH);
        sMonthIni = C.get(Calendar.MONTH);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);
        sYearIni = C.get(Calendar.YEAR);
        ButterKnife.bind(this);
        prefs = new SessionPreferences(getApplicationContext());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("RESERVA TURNO");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        obtenerLista();
        ArrayAdapter<CharSequence> adaptador=new ArrayAdapter
                (this,android.R.layout.simple_spinner_item,listaPaciente);

        spn.setAdapter(adaptador);

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idl) {
                //IdPersonal = tempListaPersonal.get(position-1).getPer_id();
                //Log.d("ahora siii",IdPersonal.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DATE_ID);
            }
        });


        especialidad.setText(getIntent().getStringExtra("especialidad"));
        doctor.setText(getIntent().getStringExtra("Doctor")+" "+getIntent().getStringExtra("Apellido"));
        dias.setText(getIntent().getStringExtra("Dias"));
        Log.d("ya es hora",getIntent().getStringExtra("especialidades"));
        Log.d("ya esd hora",getIntent().getStringExtra("personal"));
    }
    private void colocar_fecha() {
        fecha.setText( mDayIni+ "/" +(mMonthIni + 1)  + "/" + mYearIni+" ");
    }



    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYearIni = year;
                    mMonthIni = monthOfYear;
                    mDayIni = dayOfMonth;
                    colocar_fecha();
                }

    };


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, mDateSetListener, sYearIni, sMonthIni, sDayIni);


        }


        return null;
    }


    private void obtenerLista() {

        listaPaciente=new ArrayList<String>();
        listaPaciente.add("Seleccione");
        Log.d("entro?","aca entra");
        for(int i=0;i<tempListaPaciente.size();i++){
            Log.d("aquis",tempListaPaciente.toString());
            listaPaciente.add(tempListaPaciente.get(i).getPaciente_id()+"-"+tempListaPaciente.get(i).getPaciente_nombre()+" "+tempListaPaciente.get(i).getPaciente_apellido()+"-"+tempListaPaciente.get(i).getPaciente_ci());
            Log.d("aqui","holas");
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void toast(String mensaje){
        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
    }
    public void onBackPressed() {
        salirActivity();
    }

    void salirActivity(){
        if (bModificado){
            setResult(Activity.RESULT_OK, new Intent());
        }
        finish();
    }
}


