package com.example.clinica.Views.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.clinica.Data.Api.Api;
import com.example.clinica.Data.Model.Personal;
import com.example.clinica.Data.Preferences.SessionPreferences;
import com.example.clinica.Data.util.Mensaje;
import com.example.clinica.R;


import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarPerfilActivity extends AppCompatActivity {

    private EditText etNombre,etUsername,etPassword,etCi,etApellido;
    private Button btnEditar;
    Spinner spn;
    private SessionPreferences prefs;
    private Personal personal;
    private ProgressDialog pdDialogo;
    Boolean bNuevo = true , bModificado = false ;


    Mensaje mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        prefs = new SessionPreferences(getApplicationContext());

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        spn= findViewById(R.id.ettipoPersona);
        etCi= findViewById(R.id.etCi);
        btnEditar = findViewById(R.id.btnEditar);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdDialogo = ProgressDialog.show(EditarPerfilActivity.this,"Actualizando","Modificando información...",true,false);
                if(validar()){
                    int codigo = SessionPreferences.get(getApplicationContext()).getPersonal();

                    bModificado = true;
                    mensaje.mensajeToasGuardar();
                }else{
                    pdDialogo.dismiss();
                }
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Editar datos");

    }

    private void cargarVista(Personal personal) {

        etNombre.setText(personal.getPer_nombre());
        etApellido.setText(personal.getPer_apellido());
        etCi.setText(personal.getPer_ci());
        etUsername.setText(personal.getPer_username());
        etPassword.setText(personal.getPer_password());
    }

    private boolean validar(){
        boolean valido = true;

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String nombre = etNombre.getText().toString();
        String apellido = etApellido.getText().toString();
        String ci = etCi.getText().toString();

        if(username.isEmpty()){
            valido =  false;
            etUsername.setError("El user es requerido");
        }else{
            etUsername.setError(null);
        }

        if(password.isEmpty()){
            valido =  false;
            etPassword.setError("El password es requerido");
        }else{
            etPassword.setError(null);
        }

        if(nombre.isEmpty()){
            valido =  false;
            etNombre.setError("El nombre es requerido");
        }else{
            etNombre.setError(null);
        }

        return valido;
    }

    public boolean validateEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void editarInformacion(Personal personal){
        Call<Personal> callEditar = Api.getApi().editarPersonal(personal);
        callEditar.enqueue(new Callback<Personal>() {
            @Override
            public void onResponse(Call<Personal> call, Response<Personal> response) {
                pdDialogo.dismiss();
                if(response.isSuccessful()){
                    toast("Datos modificados correctamente");
                    prefs.guardarUsuario(response.body());
                }else{
                    toast("No se ha podido modificar tus datos");
                }
            }

            @Override
            public void onFailure(Call<Personal> call, Throwable t) {
                pdDialogo.dismiss();
                toast("Error al comunicarse con el servidor");
            }
        });
    }

    private void toast(String mensaje){
        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
    }
}
