package com.example.clinica.Views.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.clinica.Data.Api.Api;
import com.example.clinica.Data.Model.Curso;
import com.example.clinica.Data.Preferences.SessionPreferences;
import com.example.clinica.R;
import com.example.clinica.Views.Adapter.CursoAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity implements CursoAdapter.CursoAdapterListener {

    private SessionPreferences prefs;
    private TextView tvNombre,tvEmail;
    private ImageView imgEditar;

    private List<Curso> cursos;
    private RecyclerView cursoRecycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar pbCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        prefs = new SessionPreferences(getApplicationContext());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Perfil");

        tvNombre = findViewById(R.id.tvNombre);
        tvEmail = findViewById(R.id.tvEmail);
        imgEditar= findViewById(R.id.imgEditar);

        imgEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PerfilActivity.this,EditarPerfilActivity.class);
                startActivity(i);
            }
        });

        tvNombre.setText(prefs.getUsuario().getPer_nombre());
        tvEmail.setText(prefs.getUsuario().getPer_username());


        //pbCarga = findViewById(R.id.pbCarga);

        cursoRecycler = findViewById(R.id.cursoRecycler);

        layoutManager = new GridLayoutManager(this,2);
        cursoRecycler.setLayoutManager(layoutManager);

       // getCursos(prefs.getUsuario().getPer_id());
    }

    //private void getCursos(int id){
       // Call<List<Curso>> callCursos = Api.getApi().getCursosUser(id);
       // callCursos.enqueue(new Callback<List<Curso>>() {
           // @Override
           // public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {
              //  cursos = response.body();
             //   pbCarga.setVisibility(View.GONE);
             //   adapter = new CursoAdapter(cursos,PerfilActivity.this);
            //    cursoRecycler.setAdapter(adapter);
            //    adapter.notifyDataSetChanged();
          //  }

            //@Override
           // public void onFailure(Call<List<Curso>> call, Throwable t) {

           // }
        //});
    //}

    @Override
    public void OnItemClicked(int id) {
        Intent i = new Intent(PerfilActivity.this,CursoDetalleActivity.class);
        i.putExtra("id",id);
        startActivity(i);
    }
}
