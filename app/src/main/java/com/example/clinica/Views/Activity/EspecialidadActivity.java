package com.example.clinica.Views.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clinica.Data.Api.Api;
import com.example.clinica.Data.Model.Especialidad;
import com.example.clinica.Data.Model.Paciente;
import com.example.clinica.Data.Model.Personal;
import com.example.clinica.Data.Preferences.SessionPreferences;
import com.example.clinica.Data.util.Mensaje;
import com.example.clinica.R;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EspecialidadActivity extends AppCompatActivity {

    private TextView tvLogin;

    private ProgressDialog pdDialogo;
    private SessionPreferences prefs;

    @BindView(R.id.etEspecialidad)
    EditText especialidads;

    @BindView(R.id.acdLLAgregar)
    LinearLayout agregar;

    @BindView(R.id.acdLLModificar)
    LinearLayout modificar;

    Boolean bNuevo = true , bModificado = false ;

    Especialidad especialidad;

    Mensaje mensaje;

    @BindView(R.id.btnEspecialidad)
    Button btnRegistrar;

    @BindView(R.id.btnEditarEspecialidad)
    Button btnEditar;


    @BindView(R.id.btnEliminarEspecialidad)
    Button btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especialidad);
        tvLogin = findViewById(R.id.tvIniciar);
        ButterKnife.bind(this);

        prefs = new SessionPreferences(getApplicationContext());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("FORMULARIO");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //pantalla completa
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN );

        //boton de retorno en el toolbar
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mensaje = new Mensaje(getApplicationContext());

        if (getIntent().hasExtra("bNuevo")){
            bNuevo = getIntent().getBooleanExtra("bNuevo", true);
        }

        agregar.setVisibility(bNuevo ? View.VISIBLE : View.INVISIBLE);
        modificar.setVisibility(!bNuevo ? View.VISIBLE : View.INVISIBLE);

        if (!bNuevo){
            especialidad = (Especialidad)getIntent().getSerializableExtra("itemEspecialidad");
            cargarVista(especialidad);
        }

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdDialogo = ProgressDialog.show(EspecialidadActivity.this,"Creando cuenta","Comprobando datos...",true,false);
                if(validar()){
                   Especialidad _objEspecialidad= new Especialidad();
                    _objEspecialidad.setEspecialidad_descripcion(especialidads.getText().toString());
                    registro(_objEspecialidad);
                }else{
                    pdDialogo.dismiss();
                }
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdDialogo = ProgressDialog.show(EspecialidadActivity.this,"Editando cuenta","Comprobando datos...",true,false);
                if(validar()){
                    Especialidad _objEspecialidad= new Especialidad();
                    _objEspecialidad.setEspecialidad_id(especialidad.getEspecialidad_id());
                    _objEspecialidad.setEspecialidad_descripcion(especialidads.getText().toString());
                    editarRegistro(_objEspecialidad);
                }else{
                    pdDialogo.dismiss();
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EspecialidadActivity.this)
                        .setTitle("Eliminar")
                        .setMessage("¿Desea realizar la Operacion?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Especialidad _objEspecialidad = new Especialidad();
                                _objEspecialidad.setEspecialidad_id(especialidad.getEspecialidad_id());
                                Eliminar(_objEspecialidad);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        especialidads.requestFocus();
    }


    private boolean validar(){
        boolean valido = true;

        String espe = especialidads.getText().toString();


        if(espe.isEmpty()){
            valido =  false;
            especialidads.setError("El user es requerido");
        }else{
            especialidads.setError(null);
        }

        return valido;
    }

    public boolean validateEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void registro(Especialidad especialidad){
        Call<Especialidad> callEspecialidad = Api.getApi().registrarEspecialidad(especialidad);
        callEspecialidad.enqueue(new Callback<Especialidad>() {
            @Override
            public void onResponse(Call<Especialidad> call, Response<Especialidad> response) {
                pdDialogo.dismiss();
                if(response.isSuccessful()){
                    Intent intent = new Intent(EspecialidadActivity.this,IndexEspecialidadActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    toast("No se ha podido registrar la especialidad");
                }
            }

            @Override
            public void onFailure(Call<Especialidad> call, Throwable t) {
                pdDialogo.dismiss();
                toast("Error al comunicarse con el servidor");
            }
        });
    }


    private void editarRegistro(Especialidad especialidad){
        Call<Especialidad> callEspecialidad = Api.getApi().editarEspecialidad(especialidad);
        callEspecialidad.enqueue(new Callback<Especialidad>() {
            @Override
            public void onResponse(Call<Especialidad> call, Response<Especialidad> response) {
                pdDialogo.dismiss();
                if(response.isSuccessful()){
                    Intent intent = new Intent(EspecialidadActivity.this,IndexEspecialidadActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    toast("No se ha podido editar");
                }
            }

            @Override
            public void onFailure(Call<Especialidad> call, Throwable t) {
                pdDialogo.dismiss();
                toast("Error al comunicarse con el servidor");
            }
        });
    }

    private void Eliminar(Especialidad especialidad) {
        Call<Especialidad> callRegistro = Api.getApi().eliminarEspecialidad(especialidad);
        callRegistro.enqueue(new Callback<Especialidad>() {
            @Override
            public void onResponse(Call<Especialidad> call, Response<Especialidad> response) {
                if (response.isSuccessful()) {
                    bModificado = true;
                    toast("Se realizo un Cambio de estado");
                    salirActivity();
                } else {
                    toast("No se ha podido cambiar de estado");
                }
            }

            @Override
            public void onFailure(Call<Especialidad> call, Throwable t) {
                pdDialogo.dismiss();
                toast("Error al comunicarse con el servidor");
            }
        });
    }



    private void cargarVista(Especialidad especialidad) {

        especialidads.setText(especialidad.getEspecialidad_descripcion());

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