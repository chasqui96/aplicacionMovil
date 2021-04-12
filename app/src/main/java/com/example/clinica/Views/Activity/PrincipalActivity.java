package com.example.clinica.Views.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,CursoAdapter.CursoAdapterListener {

    private SessionPreferences prefs;
    private ProgressDialog pdDialogo;
    private List<Curso> cursos;

    private RecyclerView cursoRecycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar pbCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        prefs = new SessionPreferences(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Clinica");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        TextView tvNombre = header.findViewById(R.id.tvNombre);
        TextView tvEmail = header.findViewById(R.id.tvEmail);

        tvNombre.setText(prefs.getUsuario().getPer_nombre());
        tvEmail.setText(prefs.getUsuario().getPer_nombre());
        //pbCarga = findViewById(R.id.pbCarga);

        //getCursos();

        //cursoRecycler = findViewById(R.id.cursoRecycler);
        //cursoRecycler.setHasFixedSize(true);

        //layoutManager = new GridLayoutManager(this,2);
        //cursoRecycler.setLayoutManager(layoutManager);

    }

    private void getCursos(){
        Call<List<Curso>> callCursos = Api.getApi().getCursos();
        callCursos.enqueue(new Callback<List<Curso>>() {
            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {
                try {
                    cursos = response.body();
                    pbCarga.setVisibility(View.GONE);
                    adapter = new CursoAdapter(cursos,PrincipalActivity.this);
                    cursoRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }catch (Exception e){
                    Log.d("error_mamon",e.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Curso>> call, Throwable t) {

            }
        });
    }

    @Override
    public void OnItemClicked(int id) {
        Intent i = new Intent(PrincipalActivity.this,CursoDetalleActivity.class);
        i.putExtra("id",id);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_paciente) {
            Intent i = new Intent(PrincipalActivity.this,IndexPacienteActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_usuarios) {
           Intent i = new Intent(PrincipalActivity.this,IndexPersonalActivity.class);
           startActivity(i);
        }else if (id == R.id.nav_cupos) {
                Intent i = new Intent(PrincipalActivity.this,CupoActivity.class);
                startActivity(i);
        }else if (id == R.id.nav_especialidad) {
            Intent i = new Intent(PrincipalActivity.this,IndexEspecialidadActivity.class);
            startActivity(i);}
        else if (id == R.id.nav_historial) {
                Intent i = new Intent(PrincipalActivity.this,HistorialCupoActivity.class);
                startActivity(i);
        }else if (id == R.id.nav_usuario) {
            Intent i = new Intent(PrincipalActivity.this,PerfilActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_historial_reserva) {
            Intent i = new Intent(PrincipalActivity.this,HistorialReservaActivity.class);
            startActivity(i);
        } else if (id == R.id.cerrar_sesion) {
            pdDialogo = ProgressDialog.show(PrincipalActivity.this,"Cerrando sesión","Borrando datos...",true,false);
            prefs.cerrarSesion();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pdDialogo.dismiss();
                    Intent intent = new Intent(PrincipalActivity.this,LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            },3000);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
