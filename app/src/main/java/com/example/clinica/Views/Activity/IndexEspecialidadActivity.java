package com.example.clinica.Views.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.example.clinica.Data.Model.Especialidad;
import com.example.clinica.Data.Model.Paciente;
import com.example.clinica.R;
import com.example.clinica.Views.Adapter.EspecialidadIndexAdapter;
import com.example.clinica.Views.Adapter.PacienteIndexAdapter;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndexEspecialidadActivity extends AppCompatActivity {
    @BindView(R.id.acRvEspecialidad)
    RecyclerView recyclerView;

    @BindView(R.id.acEtBuscarEspecialidad)
    EditText buscador;

    RecyclerView.LayoutManager layoutManager;

    //objeto para la personalizacion del item del recycler
    EspecialidadIndexAdapter adaptador;
    Boolean bNuevo = true , bModificado = false ;
    //lista de objetos
    List<Especialidad>  listaEspecialidad = new ArrayList<>();
    private ProgressBar pbCarga;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_especialidad);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("LISTA ESPECIALIDAD");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        pbCarga = findViewById(R.id.pbCarga);
        cargarRecycler();

    }
    @OnClick(R.id.acFabNuevoEspecialidad)
    public void clickNuevoEspecialidad(){
        irActivity(true,
                new Especialidad());
    }




    private void cargarRecycler() {
        Call<List<Especialidad>> callEspecialidad= Api.getApi().getEspecialidad();
        callEspecialidad.enqueue(new Callback<List<Especialidad>>() {
            @Override
            public void onResponse(Call<List<Especialidad>> call, Response<List<Especialidad>> response) {
                Log.d("acaes",response.body()+"");
                listaEspecialidad = response.body();
                pbCarga.setVisibility(View.GONE);
                adaptador = new EspecialidadIndexAdapter(listaEspecialidad, new EspecialidadIndexAdapter.OnItemClickListener(){
                    @Override
                    public void onItemClick(Especialidad especialidad, int position) {
                        irActivity(false, especialidad);
                    }
                });
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adaptador);

                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Especialidad>> call, Throwable t) {

            }
        });

    }

    private void irActivity(boolean bNuevo, Especialidad especialidad) {
        Intent intent = new Intent(getApplicationContext(), EspecialidadActivity.class);

        intent.putExtra("bNuevo", bNuevo);
        intent.putExtra("itemEspecialidad", especialidad);


        startActivityForResult(intent, 1);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            cargarRecycler();
        }
    }


}
