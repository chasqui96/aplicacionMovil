package com.example.clinica.Views.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clinica.Data.Api.Api;
import com.example.clinica.Data.Model.Paciente;

import com.example.clinica.Data.Model.Personal;
import com.example.clinica.Data.Preferences.SessionPreferences;
import com.example.clinica.Data.util.Mensaje;
import com.example.clinica.R;


import java.util.Calendar;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PacienteActivity extends AppCompatActivity {
    DatePickerDialog picker;
    private TextView tvLogin;


    @BindView(R.id.etNombre)
    EditText nombre;

    @BindView(R.id.etApellido)
    EditText apellido;

    @BindView(R.id.etCi)
    EditText ci;


    @BindView(R.id.etTelefono)
    EditText telefono;

    @BindView(R.id.etFechaNa)
    EditText fechana;

    @BindView(R.id.acdLLAgregar)
    LinearLayout agregar;

    @BindView(R.id.acdLLModificar)
    LinearLayout modificar;

    @BindView(R.id.btnPaciente)
    Button btnRegistrar;

    @BindView(R.id.btnEliminarPaciente)
    Button btnCambiarEstado;


    @BindView(R.id.btnEditarPaciente)
    Button btnEditarRegistrar;


    Boolean bNuevo = true , bModificado = false ;

    Paciente paciente;

    Mensaje mensaje;

    private ProgressDialog pdDialogo;
    private SessionPreferences prefs;
    private int mYearIni, mMonthIni, mDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    Calendar C = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);
        sMonthIni = C.get(Calendar.MONTH);
        sMonthIni = C.get(Calendar.MONTH);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);
        sYearIni = C.get(Calendar.YEAR);
        tvLogin = findViewById(R.id.tvIniciar);
        ButterKnife.bind(this);

        prefs = new SessionPreferences(getApplicationContext());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("FORMULARIO");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //pantalla completa
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //boton de retorno en el toolbar
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mensaje = new Mensaje(getApplicationContext());

        if (getIntent().hasExtra("bNuevo")) {
            bNuevo = getIntent().getBooleanExtra("bNuevo", true);
        }

        agregar.setVisibility(bNuevo ? View.VISIBLE : View.INVISIBLE);
        modificar.setVisibility(!bNuevo ? View.VISIBLE : View.INVISIBLE);

        if (!bNuevo) {
            paciente = (Paciente) getIntent().getSerializableExtra("itemPaciente");
            cargarVista(paciente);
            if (paciente.getPaciente_estado().equals("ACTIVO")) {
                btnCambiarEstado.setText("INACTIVAR");

            }
        }


        fechana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DATE_ID);
            }
        });
        nombre.requestFocus();


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdDialogo = ProgressDialog.show(PacienteActivity.this, "Creando paciente", "Comprobando datos...", true, false);
                if (validar()) {
                    Paciente _objPaciente = new Paciente();
                    _objPaciente.setPaciente_nombre(nombre.getText().toString());
                    _objPaciente.setPaciente_apellido(apellido.getText().toString());
                    _objPaciente.setPaciente_ci(ci.getText().toString());
                    _objPaciente.setPaciente_telefono(telefono.getText().toString());
                    _objPaciente.setPaciente_fechana(fechana.getText().toString());
                    registrar(_objPaciente);
                } else {
                    pdDialogo.dismiss();
                }
            }
        });

        btnEditarRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdDialogo = ProgressDialog.show(PacienteActivity.this, "Editando paciente", "Comprobando datos...", true, false);
                if (validar()) {
                    Paciente _objPaciente = new Paciente();
                    _objPaciente.setPaciente_id(paciente.getPaciente_id());
                    _objPaciente.setPaciente_nombre(nombre.getText().toString());
                    _objPaciente.setPaciente_apellido(apellido.getText().toString());
                    _objPaciente.setPaciente_ci(ci.getText().toString());
                    _objPaciente.setPaciente_telefono(telefono.getText().toString());
                    _objPaciente.setPaciente_fechana(fechana.getText().toString());
                    editarRegistro(_objPaciente);
                } else {
                    pdDialogo.dismiss();
                }
            }
        });

        btnCambiarEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(PacienteActivity.this)
                        .setTitle("Cambiar Estado")
                        .setMessage("¿Desea realizar la Operacion?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Paciente _objPaciente = new Paciente();
                                _objPaciente.setPaciente_id(paciente.getPaciente_id());
                                if(paciente.getPaciente_estado().equals("ACTIVO")){
                                    _objPaciente.setPaciente_estado("INACTIVO");

                                }else{
                                    _objPaciente.setPaciente_estado("ACTIVO");
                                }
                                cambiarEstado(_objPaciente);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
    }


    private void colocar_fecha() {
        fechana.setText( mDayIni+ "/" +(mMonthIni + 1)  + "/" + mYearIni+" ");
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
                return new DatePickerDialog(PacienteActivity.this, mDateSetListener, sYearIni, sMonthIni, sDayIni);


        }


        return null;
    }
    private void editarRegistro(Paciente paciente){
                Call<Paciente> callRegistro = Api.getApi().registrarPacientes(paciente);
                callRegistro.enqueue(new Callback<Paciente>() {
                    @Override
                    public void onResponse(Call<Paciente> call, Response<Paciente> response) {
                        pdDialogo.dismiss();
                        Log.d("este ess",response+"");
                        if(response.isSuccessful()){
                            toast("Registro guardado");

                            Intent intent = new Intent(PacienteActivity.this,IndexPacienteActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else{
                            toast("No se ha podido registrar tu cuenta");
                        }
                    }

                    @Override
                    public void onFailure(Call<Paciente> call, Throwable t) {
                        pdDialogo.dismiss();
                        toast("Error al comunicarse con el servidor");
                    }
                });
    }

    private void registrar(Paciente paciente){
        Call<Paciente> callRegistro = Api.getApi().registrarPacientes(paciente);
        callRegistro.enqueue(new Callback<Paciente>() {
            @Override
            public void onResponse(Call<Paciente> call, Response<Paciente> response) {
                pdDialogo.dismiss();
                Log.d("este ess",response+"");
                if(response.isSuccessful()){
                    toast("Registro guardado");

                    Intent intent = new Intent(PacienteActivity.this,IndexPacienteActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    toast("No se ha podido registrar tu cuenta");
                }
            }

            @Override
            public void onFailure(Call<Paciente> call, Throwable t) {
                pdDialogo.dismiss();
                toast("Error al comunicarse con el servidor");
            }
        });
    }

    private void cambiarEstado(Paciente paciente) {
        Call<Paciente> callPaciente= Api.getApi().cambiarEstadoPacientes(paciente);
        callPaciente.enqueue(new Callback<Paciente>() {
            @Override
            public void onResponse(Call<Paciente> call, Response<Paciente> response) {

                Log.d("este si", response + "");
                if (response.isSuccessful()) {
                    bModificado = true;
                    toast("Se realizo un Cambio de estado");
                    salirActivity();
                } else {
                    toast("No se ha podido cambiar de estado");
                }
            }

            @Override
            public void onFailure(Call<Paciente> call, Throwable t) {
                pdDialogo.dismiss();
                toast("Error al comunicarse con el servidor");
            }
        });
    }



    private boolean validar(){
        boolean valido = true;

        String nombres = nombre.getText().toString();
        String fechanas = fechana.getText().toString();
        //String nombres = nombre.getText().toString();
        String apellidos = apellido.getText().toString();
        String cis = ci.getText().toString();

        if(apellidos.isEmpty()){
            valido =  false;
            apellido.setError("El user es requerido");
        }else{
            apellido.setError(null);
        }

        if(fechanas.isEmpty()){
            valido =  false;
            fechana.setError("la Fecha es requerido");
        }else{
            fechana.setError(null);
        }

        if(nombres.isEmpty()){
            valido =  false;
            nombre.setError("El nombre es requerido");
        }else{
            nombre.setError(null);
        }

        return valido;
    }

    public boolean validateEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }


    private void cargarVista(Paciente paciente) {

        nombre.setText(paciente.getPaciente_nombre());
        apellido.setText(paciente.getPaciente_apellido());
        ci.setText(paciente.getPaciente_ci());
        fechana.setText(paciente.getPaciente_fechana());
        telefono.setText(paciente.getPaciente_telefono());
    }



            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case android.R.id.home:
                        salirActivity();
                        return true;
                    default:
                        return super.onOptionsItemSelected(item);
                }
            }

            @Override
            public void onBackPressed() {
                salirActivity();
            }

            void salirActivity(){
                if (bModificado){
                    setResult(Activity.RESULT_OK, new Intent());
                }
                finish();
            }

            private void toast(String mensaje){
                Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
            }


}