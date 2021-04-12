package com.example.clinica.Views.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.clinica.Data.Model.Cupo;
import com.example.clinica.Data.Model.Especialidad;
import com.example.clinica.Data.Model.Horario;
import com.example.clinica.Data.Model.Paciente;
import com.example.clinica.Data.Model.Personal;
import com.example.clinica.Data.Preferences.SessionPreferences;
import com.example.clinica.Data.util.Metodos;
import com.example.clinica.R;
import com.example.clinica.Views.Adapter.DetalleCupoHorario;
import com.example.clinica.Views.Adapter.PersonalIndexAdapter;


import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CupoHorarioActivity extends AppCompatActivity {
    TextView time;
    TimePicker simpleTimePicker;

    @BindView(R.id.acEtFecha)
    EditText fechacupo;

    @BindView(R.id.etCantidad)
    EditText cantidad;


    @BindView(R.id.IdEspecialidad)
    EditText idEspecialidad;


    @BindView(R.id.acEtReDoctor)
    EditText redoctor;

    @BindView(R.id.acEtDesde)
    EditText desde;
    @BindView(R.id.acEthasta)
    EditText hasta;

    @BindView(R.id.etEspecialidad)
    TextView espe;
   // @BindView(R.id.IdPersonal)
    Integer IdPersonal;

    private ProgressDialog pdDialogo;
    private SessionPreferences prefs;

    List<Especialidad> listaEspecialidad = new ArrayList<>();
    ArrayList<String> listaPersonas;
    List<Personal> tempListaPersonal = new ArrayList<>();
    @BindView(R.id.acEtDoctor)
    Spinner spn;

    @BindView(R.id.acEtDia)
    Spinner dias;

    Boolean bNuevo = true , bModificado = false;

    private int mYearIni, mMonthIni, mDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    Calendar C = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cupo_horario);
        sMonthIni = C.get(Calendar.MONTH);
        sMonthIni = C.get(Calendar.MONTH);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);
        sYearIni = C.get(Calendar.YEAR);
        ButterKnife.bind(this);
        prefs = new SessionPreferences(getApplicationContext());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("GUARDAR CUPOS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        obtenerLista();
        ArrayAdapter<CharSequence> adaptador=new ArrayAdapter
                (this,android.R.layout.simple_spinner_item,listaPersonas);

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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dias_semanas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dias.setAdapter(adapter);

        this.showTimePickerDialogdesde();
        this.showTimePickerDialoghasta();
        //cargarRecycler();

        espe.setText(getIntent().getStringExtra("itemEspecialidad"));
        //idEspecialidad.setText(String.valueOf());
        Log.d("prof",   String.valueOf(getIntent().getIntExtra("idEspecialidad",0)));
        fechacupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DATE_ID);
            }
        });
    }
    private void colocar_fecha() {
        fechacupo.setText( mDayIni+ "/" +(mMonthIni + 1)  + "/" + mYearIni+" ");
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
    private void showTimePickerDialogdesde()
    {
        // Get open TimePickerDialog button.

        desde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new OnTimeSetListener instance. This listener will be invoked when user click ok button in TimePickerDialog.
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        StringBuffer strBuf = new StringBuffer();
                        strBuf.append(hour);
                        strBuf.append(":");
                        if (minute < 10) {
                            strBuf.append("0" + minute);
                        } else {
                            strBuf.append(minute);
                        }
                        strBuf.append(" hrs.");
                        TextView timePickerValueTextView = (TextView)findViewById(R.id.acEtDesde);
                        timePickerValueTextView.setText(strBuf.toString());
                    }
                };

                Calendar now = Calendar.getInstance();
                int hour = now.get(java.util.Calendar.HOUR_OF_DAY);
                int minute = now.get(java.util.Calendar.MINUTE);

                // Whether show time in 24 hour format or not.
                boolean is24Hour = true;

                TimePickerDialog timePickerDialog = new TimePickerDialog(CupoHorarioActivity.this, onTimeSetListener, hour, minute, is24Hour);

                //timePickerDialog.setIcon(R.drawable.if_snowman);
                timePickerDialog.setTitle("Please select time.");

                timePickerDialog.show();

            }
        });
    }

    private void showTimePickerDialoghasta()
    {
        // Get open TimePickerDialog button.

        hasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new OnTimeSetListener instance. This listener will be invoked when user click ok button in TimePickerDialog.
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        StringBuffer strBuf = new StringBuffer();
                        strBuf.append(hour);
                        strBuf.append(":");
                        if (minute < 10) {
                            strBuf.append("0" + minute);
                        } else {
                            strBuf.append(minute);
                        }

                        strBuf.append(" hrs.");
                        TextView timePickerValueTextView = (TextView)findViewById(R.id.acEthasta);
                        timePickerValueTextView.setText(strBuf.toString());
                    }
                };

                Calendar now = Calendar.getInstance();
                int hour = now.get(java.util.Calendar.HOUR_OF_DAY);
                int minute = now.get(java.util.Calendar.MINUTE);

                // Whether show time in 24 hour format or not.
                boolean is24Hour = true;

                TimePickerDialog timePickerDialog = new TimePickerDialog(CupoHorarioActivity.this, onTimeSetListener, hour, minute, is24Hour);

                //timePickerDialog.setIcon(R.drawable.if_snowman);
                timePickerDialog.setTitle("Please select time.");

                timePickerDialog.show();
            }
        });
    }

    //private void cargarRecycler() {
        //adaptador = new DetalleCupoHorario(listaEspecialidad);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setAdapter(adaptador);
   // }

    private void irActivity(boolean bNuevo, Personal personal) {
        Intent intent = new Intent(getApplicationContext(), RegistroActivity.class);

       // intent.putExtra("bNuevo", bNuevo);
       // intent.putExtra("itemPersonal", personal);

        startActivityForResult(intent, 1);
    }




    private void obtenerLista() {

       // Select.seleccionarDoctor(getApplicationContext(), tempListaPersonal);
        listaPersonas=new ArrayList<String>();
        listaPersonas.add("Seleccione");
        Log.d("entro?","aca entra");
        for(int i=0;i<tempListaPersonal.size();i++){
            Log.d("aquis",tempListaPersonal.toString());
            listaPersonas.add(tempListaPersonal.get(i).getPer_id()+"-"+tempListaPersonal.get(i).getPer_nombre()+" "+tempListaPersonal.get(i).getPer_apellido()+"-"+tempListaPersonal.get(i).getPer_ci());
            Log.d("aqui","holas");
        }

    }

    @OnClick(R.id.acFabCupo)
    void clickAgregar(){
        int codigo = SessionPreferences.get(getApplicationContext()).getHorario();
        int codigoCupo = SessionPreferences.get(getApplicationContext()).getCupo();
 //       int ides = Integer.parseInt(idEspecialidad.getText().toString());
        //Obtiene posicion en Spinner.
        int posicionSpinner = (spn.getSelectedItemPosition()+1);

            toast("CUPO REGISTRADO");
        salirActivity();
            //Intent intent = new Intent(CupoHorarioActivity.this,HistorialCupoActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            //startActivity(intent);


    }

    private void toast(String mensaje){
        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
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