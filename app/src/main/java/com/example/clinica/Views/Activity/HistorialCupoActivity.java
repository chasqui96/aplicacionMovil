package com.example.clinica.Views.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.clinica.R;
import com.example.clinica.Views.Adapter.DetalleCupoHorario;



import java.util.ArrayList;

import java.util.HashMap;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistorialCupoActivity extends AppCompatActivity {
    @BindView(R.id.acRvCupoDetalle)
    RecyclerView recyclerView;


    ArrayList<Map<String,String>> listaMain;
    DetalleCupoHorario adaptador;
    Boolean bNuevo = true , bModificado = false ;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_cupo);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("LISTA CUPOS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listaMain = new ArrayList<Map<String,String>>();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        cargarLista();
    }

    private void cargarLista() {

        cargarRecycler(listaMain);
    }

    private void cargarRecycler(ArrayList<Map<String,String>> listaMain2) {

        adaptador = new DetalleCupoHorario(listaMain2, new DetalleCupoHorario.OnItemClickListener(){
            @Override
            public void onItemClick(ArrayList<Map<String, String>> listaMain3, int position) {
                irActivity(false, (HashMap<String, String>) listaMain3.get(position));
            }
        });

        recyclerView.setHasFixedSize(true);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adaptador);

    }

    private void irActivity(boolean bNuevo, HashMap<String,String> datos) {
        Intent intent = new Intent(getApplicationContext(), ReservaTurnoActivity.class);
        Log.d("que traes",datos.toString());
        intent.putExtra("bNuevo", bNuevo);
        intent.putExtra("especialidad", datos.get("especialidad_descripcion"));
        intent.putExtra("Doctor", datos.get("per_nombre"));
        intent.putExtra("Apellido", datos.get("per_apellido"));
        intent.putExtra("Dias", datos.get("dias"));
        intent.putExtra("especialidades", datos.get("idespecialidad"));
        intent.putExtra("Cupo", datos.get("idcupo"));
        intent.putExtra("personal", datos.get("idpersonal"));

        startActivityForResult(intent, 1);
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

    private void toast(String mensaje){
        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            cargarLista();
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

}