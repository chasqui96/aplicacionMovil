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
import com.example.clinica.Data.Model.Curso;
import com.example.clinica.R;
import com.example.clinica.Views.Adapter.CursoAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriaDetalleActivity extends AppCompatActivity implements CursoAdapter.CursoAdapterListener {

    private List<Curso> cursos;
    private RecyclerView cursoRecycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar pbCarga;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_detalle);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(getIntent().getStringExtra("nombre"));

        pbCarga = findViewById(R.id.pbCarga);

        cursoRecycler = findViewById(R.id.cursoRecycler);
        cursoRecycler.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this,2);
        cursoRecycler.setLayoutManager(layoutManager);

        getCursos(getIntent().getIntExtra("id",0));
    }

    private void getCursos(int id){
        Call<List<Curso>> callCursos = Api.getApi().getCategoriaDetalle(id);
        callCursos.enqueue(new Callback<List<Curso>>() {
            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {
                cursos = response.body();
                pbCarga.setVisibility(View.GONE);
                adapter = new CursoAdapter(cursos,CategoriaDetalleActivity.this);
                cursoRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Curso>> call, Throwable t) {

            }
        });
    }

    @Override
    public void OnItemClicked(int id) {
        Intent i = new Intent(CategoriaDetalleActivity.this,CursoDetalleActivity.class);
        i.putExtra("id",id);
        startActivity(i);
    }
}
