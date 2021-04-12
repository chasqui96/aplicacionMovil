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
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.clinica.Data.Model.Paciente;

import com.example.clinica.R;
import com.example.clinica.Views.Adapter.PacienteIndexAdapter;



import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndexPacienteActivity extends AppCompatActivity {

    @BindView(R.id.acRvPaciente)
    RecyclerView recyclerView;

    @BindView(R.id.acEtBuscarPaciente)
    EditText buscador;

    RecyclerView.LayoutManager layoutManager;
    Boolean bNuevo = true , bModificado = false ;
    //objeto para la personalizacion del item del recycler
    PacienteIndexAdapter adaptador;

    //lista de objetos
    List<Paciente> listaPaciente = new ArrayList<>();
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
    @OnClick(R.id.acFabNuevoPaciente)
    public void clickNuevoPaciente(){
        irActivity(true, new Paciente());
    }


    private void cargarLista() {
        //Select.seleccionarPaciente(getApplicationContext(), listaPaciente, buscador.getText().toString());
        cargarRecycler(listaPaciente);
    }

    private void cargarRecycler(List<Paciente> listaPaciente) {

        adaptador = new PacienteIndexAdapter(listaPaciente, new PacienteIndexAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(Paciente paciente, int position) {
                irActivity(false, paciente);
            }
        });

        recyclerView.setHasFixedSize(true);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adaptador);

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
            cargarLista();
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
