package com.example.clinica.Views.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.clinica.Data.Api.Api;
import com.example.clinica.Data.Model.Paciente;
import com.example.clinica.Data.Model.Personal;
import com.example.clinica.Data.Model.Profesor;
import com.example.clinica.R;

import com.example.clinica.Views.Adapter.PacienteIndexAdapter;
import com.example.clinica.Views.Adapter.PersonalIndexAdapter;
import com.example.clinica.Views.Adapter.ProfesorAdapter;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  IndexPersonalActivity extends AppCompatActivity {




    @BindView(R.id.acRvPersonal)
    RecyclerView personalRecycler;

    @BindView(R.id.acEtBuscarPersonal)
    EditText buscador;

    RecyclerView.LayoutManager layoutManager;

    //objeto para la personalizacion del item del recycler
    PersonalIndexAdapter adapter;
    Boolean bNuevo = true , bModificado = false ;
    //lista de objetos
    private List<Personal> listaPersonal;
    private ProgressBar pbCarga;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_personal);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("LISTA USUARIOS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pbCarga = findViewById(R.id.pbCarga);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
      // personalRecycler = findViewById(R.id.acRvPersonal);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        cargarRecycler();

    }
    @OnClick(R.id.acFabNuevoCliente)
    public void clickNuevoPersonal(){
        irActivity(true, new Personal());
    }


    private void cargarRecycler(){
        Call<List<Personal>> callPersonales = Api.getApi().getPersonal();
        callPersonales.enqueue(new Callback<List<Personal>>() {
            @Override
            public void onResponse(Call<List<Personal>> call, Response<List<Personal>> response) {
                Log.d("acaes",response.body()+"");
                listaPersonal = response.body();
                pbCarga.setVisibility(View.GONE);
                adapter = new PersonalIndexAdapter(listaPersonal, new PersonalIndexAdapter.OnItemClickListener(){
                    @Override
                    public void onItemClick(Personal personal, int position) {
                        irActivity(false, personal);
                    }
                });
                personalRecycler.setHasFixedSize(true);
                personalRecycler.setItemAnimator(new DefaultItemAnimator());
                personalRecycler.setLayoutManager(layoutManager);
                personalRecycler.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Personal>> call, Throwable t) {

            }
        });
    }

    private void irActivity(boolean bNuevo, Personal personal) {
        Intent intent = new Intent(getApplicationContext(), RegistroActivity.class);

       intent.putExtra("bNuevo", bNuevo);
       intent.putExtra("itemPersonal", personal);

        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            cargarRecycler();
        }
    }
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
}