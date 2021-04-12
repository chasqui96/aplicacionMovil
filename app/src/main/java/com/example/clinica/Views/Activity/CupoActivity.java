package com.example.clinica.Views.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.clinica.Data.Model.Especialidad;

import com.example.clinica.R;
import com.example.clinica.Views.Adapter.CupoAdapter;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CupoActivity extends AppCompatActivity {
    RecyclerView.LayoutManager layoutManager;
   CupoAdapter adaptador;
    @Nullable
    @BindView(R.id.etEspecialidad)
    EditText especialidads;

    @BindView(R.id.acRvEspecialidad)
    RecyclerView recyclerView;

    @BindView(R.id.acEtBuscarEspecialidad)
    EditText buscador;

    List<Especialidad> listaEspecialidad = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cupo);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("SELECCIONAR");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);;

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        cargarLista();
        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cargarLista();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void cargarLista() {
        cargarRecycler(listaEspecialidad);
    }


    private void cargarRecycler(final List<Especialidad> listaEspecialidad) {

        adaptador = new CupoAdapter(listaEspecialidad, new CupoAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(Especialidad especialidad, int position) {
                irActivity(false, especialidad);
            }
        });

        recyclerView.setHasFixedSize(true);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adaptador);
    }

    private void irActivity(boolean bNuevo, Especialidad especialidad) {
        Intent intent = new Intent(getApplicationContext(), CupoHorarioActivity.class);


        intent.putExtra("bNuevo", bNuevo);
        intent.putExtra("itemEspecialidad", especialidad.getEspecialidad_descripcion());
        intent.putExtra("idEspecialidad", especialidad.getEspecialidad_id());
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            cargarLista();
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
}