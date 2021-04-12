package com.example.clinica.Views.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.clinica.Data.Api.Api;
import com.example.clinica.Data.Model.Profesor;
import com.example.clinica.R;
import com.example.clinica.Views.Adapter.ProfesorAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfesorActivity extends AppCompatActivity implements ProfesorAdapter.ProfesorAdapterListener {

    private List<Profesor> profesores;
    private RecyclerView profesorRecycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar pbCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profesores");

        pbCarga = findViewById(R.id.pbCarga);
        profesorRecycler = findViewById(R.id.profesorRecycler);
        profesorRecycler.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this,2);
        profesorRecycler.setLayoutManager(layoutManager);

        getProfesores();
    }

    private void getProfesores(){
        Call<List<Profesor>> callProfesores = Api.getApi().getProfesores();
        callProfesores.enqueue(new Callback<List<Profesor>>() {
            @Override
            public void onResponse(Call<List<Profesor>> call, Response<List<Profesor>> response) {
                profesores = response.body();
                pbCarga.setVisibility(View.GONE);
                adapter = new ProfesorAdapter(profesores,ProfesorActivity.this);
                profesorRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Profesor>> call, Throwable t) {

            }
        });
    }

    @Override
    public void OnItemClickListener(int position) {
        Intent i = new Intent(ProfesorActivity.this,ProfesorDetalleActivity.class);
        i.putExtra("id",profesores.get(position).getId());
        i.putExtra("nombre",profesores.get(position).getNombre());
        startActivity(i);
    }
}
