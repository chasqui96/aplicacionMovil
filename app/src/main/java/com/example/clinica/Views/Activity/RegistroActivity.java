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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clinica.Data.Api.Api;
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

public class RegistroActivity extends AppCompatActivity   {
  ;
        private TextView tvLogin;

    private ProgressDialog pdDialogo;
    private SessionPreferences prefs;

    @BindView(R.id.etNombre)
    EditText nombre;

    @BindView(R.id.etApellido)
    EditText apellido;

    @BindView(R.id.etCi)
    EditText ci;

    @BindView(R.id.etTelefono)
    EditText tel;
    @BindView(R.id.etUsername)
    EditText username;

    @BindView(R.id.etPassword)
    EditText password;

    @BindView(R.id.ettipoPersona)
    Spinner spn;


    @BindView(R.id.acdLLAgregar)
    LinearLayout agregar;

    @BindView(R.id.btnRegistrarse)
    Button btnRegistrar;

    @BindView(R.id.btnEditarRegistrarse)
    Button btnEditarRegistrar;

    @BindView(R.id.acdLLModificar)
    LinearLayout modificar;

    Boolean bNuevo = true , bModificado = false ;

    Personal personal;

    Mensaje mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        tvLogin = findViewById(R.id.tvIniciar);
        ButterKnife.bind(this);

        prefs = new SessionPreferences(getApplicationContext());
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle("FORMULARIO");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //pantalla completa
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN );


        mensaje = new Mensaje(getApplicationContext());

        if (getIntent().hasExtra("bNuevo")){
            bNuevo = getIntent().getBooleanExtra("bNuevo", true);
        }

        agregar.setVisibility(bNuevo ? View.VISIBLE : View.INVISIBLE);
        modificar.setVisibility(!bNuevo ? View.VISIBLE : View.INVISIBLE);

        if (!bNuevo){
            personal = (Personal)getIntent().getSerializableExtra("itemPersonal");
            cargarVista(personal);
        }
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistroActivity.this,LoginActivity.class));
            }
        });
        nombre.requestFocus();



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipo_persona, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdDialogo = ProgressDialog.show(RegistroActivity.this,"Creando cuenta","Comprobando datos...",true,false);
                if(validar()){
                    Personal _objPersonal= new Personal();
                    _objPersonal.setPer_nombre(nombre.getText().toString());
                    _objPersonal.setPer_apellido(apellido.getText().toString());
                    _objPersonal.setPer_ci(ci.getText().toString());
                    _objPersonal.setPer_telefono(tel.getText().toString());
                    _objPersonal.setPer_username(username.getText().toString());
                    _objPersonal.setPer_password(password.getText().toString());
                    _objPersonal.setPer_tipo(spn.getSelectedItem().toString());
                    registro(_objPersonal);
                }else{
                    pdDialogo.dismiss();
                }
            }
        });

        btnEditarRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdDialogo = ProgressDialog.show(RegistroActivity.this,"Editando cuenta","Comprobando datos...",true,false);
                if(validar()){
                    Log.d("editar",personal.getPer_ci()+"");
                    Personal _objPersonal= new Personal();
                    _objPersonal.setPer_id(personal.getPer_id());
                    _objPersonal.setPer_nombre(nombre.getText().toString());
                    _objPersonal.setPer_apellido(apellido.getText().toString());
                    _objPersonal.setPer_ci(ci.getText().toString());
                    _objPersonal.setPer_telefono(tel.getText().toString());
                    _objPersonal.setPer_username(username.getText().toString());
                    _objPersonal.setPer_password(password.getText().toString());
                    _objPersonal.setPer_tipo(spn.getSelectedItem().toString());
                    editarRegistro(_objPersonal);
                }else{
                    pdDialogo.dismiss();
                }
            }
        });

    }

    private void registro(Personal personal){
        Call<Personal> callRegistro = Api.getApi().registrar(personal);
        callRegistro.enqueue(new Callback<Personal>() {
            @Override
            public void onResponse(Call<Personal> call, Response<Personal> response) {
                pdDialogo.dismiss();
                Log.d("este ess",response+"");
                if(response.isSuccessful()){
                    toast("Usuario registrado");
                    prefs.guardarUsuario(response.body());

                    Intent intent = new Intent(RegistroActivity.this,PrincipalActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    toast("No se ha podido registrar tu cuenta");
                }
            }

            @Override
            public void onFailure(Call<Personal> call, Throwable t) {
                pdDialogo.dismiss();
                toast("Error al comunicarse con el servidor");
            }
        });
    }


    private void editarRegistro(Personal personal){
        Call<Personal> callRegistro = Api.getApi().editarPersonal(personal);
        callRegistro.enqueue(new Callback<Personal>() {
            @Override
            public void onResponse(Call<Personal> call, Response<Personal> response) {
                pdDialogo.dismiss();
                Log.d("este ess",response+"");
                if(response.isSuccessful()){
                    bModificado = true;
                    toast("Usuario Editado");
                    salirActivity();
                }else{
                    toast("No se ha podido registrar tu cuenta");
                }
            }

            @Override
            public void onFailure(Call<Personal> call, Throwable t) {
                pdDialogo.dismiss();
                toast("Error al comunicarse con el servidor");
            }
        });
    }



    private boolean validar(){
        boolean valido = true;

        String usernames = username.getText().toString();
        String passwords = password.getText().toString();
        String nombres = nombre.getText().toString();
        String apellidos = apellido.getText().toString();
        String cis = ci.getText().toString();

        if(usernames.isEmpty()){
            valido =  false;
            username.setError("El user es requerido");
        }else{
            username.setError(null);
        }

        if(passwords.isEmpty()){
            valido =  false;
         password.setError("El password es requerido");
        }else{
            password.setError(null);
        }

        if(nombres.isEmpty()){
            valido =  false;
            password.setError("El nombre es requerido");
        }else{
            password.setError(null);
        }

        return valido;
    }

    public boolean validateEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void cargarVista(Personal personal) {

        nombre.setText(personal.getPer_nombre());
        apellido.setText(personal.getPer_apellido());
        ci.setText(personal.getPer_ci());
        username.setText(personal.getPer_username());
        tel.setText(personal.getPer_telefono());
        password.setText(personal.getPer_password());

        for (int i=0; i < spn.getAdapter().getCount(); i++){
            if(personal.getPer_tipo().equals(spn.getAdapter().getItem(i).toString())){
                spn.setSelection(0);
                Log.d("DDDD",spn.getAdapter().getItem(i).toString());
            }
        }

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
