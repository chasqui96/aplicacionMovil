package com.example.clinica.Views.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.clinica.Views.Adapter.DetalleReserva;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistorialReservaActivity extends AppCompatActivity {
    @BindView(R.id.acRvReservaDetalle)
    RecyclerView recyclerView;


    ArrayList<Map<String,String>> listaMain;
    DetalleReserva adaptador;
    Boolean bNuevo = true , bModificado = false ;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_reserva);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("LISTA RESERVAS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listaMain = new ArrayList<Map<String,String>>();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        cargarLista();
    }

    private void cargarLista() {

        cargarRecycler(listaMain);
    }

    private void cargarRecycler(ArrayList<Map<String,String>> listaMain2) {

        adaptador = new DetalleReserva(listaMain2, new DetalleReserva.OnItemClickListener(){
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

    private void irActivity(boolean bNuevo, final HashMap<String,String> datos) {
        Log.d("QUEMEPASO",datos.toString());
        new AlertDialog.Builder(this)
                .setTitle("Inactivar")
                .setMessage("¿Desea Anular la Reserva?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                       cargarLista();
                        toast("RESERVA ANULADA");

                    }
                })
                .setNegativeButton(android.R.string.no, null).show();

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

    private void toast(String mensaje){
        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            cargarLista();
        }
    }

}