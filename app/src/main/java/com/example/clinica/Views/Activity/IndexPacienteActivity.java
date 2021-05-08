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
import com.example.clinica.Data.Model.Paciente;

import com.example.clinica.Data.Model.Personal;
import com.example.clinica.R;
import com.example.clinica.Views.Adapter.PacienteIndexAdapter;
import com.example.clinica.Views.Adapter.PersonalIndexAdapter;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndexPacienteActivity extends AppCompatActivity {

    @BindView(R.id.acRvPaciente)
    RecyclerView pacienteRecycler;

    @BindView(R.id.acEtBuscarPaciente)
    EditText buscador;

    RecyclerView.LayoutManager layoutManager;
    Boolean bNuevo = true , bModificado = false ;
    //objeto para la personalizacion del item del recycler
    PacienteIndexAdapter adapter;

    //lista de objetos
    private List<Paciente> listaPaciente;
    private ProgressBar pbCarga;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_paciente);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("LISTA PACIENTES");
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        pbCarga = findViewById(R.id.pbCarga);
        cargarRecycler();
    }
    @OnClick(R.id.acFabNuevoPaciente)
    public void clickNuevoPaciente(){
        irActivity(true, new Paciente());
    }




    private void cargarRecycler(){
        Call<List<Paciente>> callPacientes = Api.getApi().getPacientes();
        callPacientes.enqueue(new Callback<List<Paciente>>() {
            @Override
            public void onResponse(Call<List<Paciente>> call, Response<List<Paciente>> response) {
                Log.d("acaes",response.body()+"");
                listaPaciente = response.body();
                pbCarga.setVisibility(View.GONE);
                adapter = new PacienteIndexAdapter(listaPaciente, new PacienteIndexAdapter.OnItemClickListener(){
                    @Override
                    public void onItemClick(Paciente paciente, int position) {
                        irActivity(false, paciente);
                    }
                });
                pacienteRecycler.setHasFixedSize(true);
                pacienteRecycler.setItemAnimator(new DefaultItemAnimator());
                pacienteRecycler.setLayoutManager(layoutManager);
                pacienteRecycler.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Paciente>> call, Throwable t) {

            }
        });
    }

    private void irActivity(boolean bNuevo, Paciente paciente) {
        Intent intent = new Intent(getApplicationContext(), PacienteActivity.class);

        intent.putExtra("bNuevo", bNuevo);
        intent.putExtra("itemPaciente", paciente);

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
