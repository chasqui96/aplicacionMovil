package com.example.clinica.Views.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.clinica.Data.Api.Api;
import com.example.clinica.Data.Model.Categoria;
import com.example.clinica.R;
import com.example.clinica.Views.Adapter.CategoriaAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriasActivity extends AppCompatActivity implements CategoriaAdapter.CategoriaAdapterListener{

    private RecyclerView categoriaRecycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lmManager;
    private ProgressBar pbCarga;
    private List<Categoria> categorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Citas");

        pbCarga = findViewById(R.id.pbCarga);
        categoriaRecycler = findViewById(R.id.recycler);
        categoriaRecycler.setHasFixedSize(true);

        lmManager = new LinearLayoutManager(this);
        categoriaRecycler.setLayoutManager(lmManager);

        DividerItemDecoration divider = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.divider));
        categoriaRecycler.addItemDecoration(divider);

        getCategorias();
    }

    public void getCategorias(){
        Call<List<Categoria>> callCategorias = Api.getApi().getCategorias();
        callCategorias.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                categorias = response.body();
                adapter = new CategoriaAdapter(categorias,CategoriasActivity.this);
                categoriaRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                pbCarga.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                pbCarga.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void OnItemClickListener(int position) {
        Intent i = new Intent(CategoriasActivity.this,CategoriaDetalleActivity.class);
        i.putExtra("id",categorias.get(position).getId());
        i.putExtra("nombre",categorias.get(position).getNombre());
        startActivity(i);
    }
}
