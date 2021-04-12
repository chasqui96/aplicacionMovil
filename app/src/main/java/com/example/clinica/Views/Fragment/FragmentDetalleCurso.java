package com.example.clinica.Views.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clinica.Data.Api.Api;
import com.example.clinica.Data.Model.Curso;
import com.example.clinica.Data.Model.ServerResponse;
import com.example.clinica.Data.Preferences.SessionPreferences;
import com.example.clinica.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentDetalleCurso extends Fragment {

    ImageView imgPortada;
    TextView tvTitulo,tvDescripcion;
    private SessionPreferences prefs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = new SessionPreferences(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalle_curso,container,false);
        imgPortada = view.findViewById(R.id.imgPortada);
        tvTitulo = view.findViewById(R.id.tvTitulo);
        tvDescripcion = view.findViewById(R.id.tvDescripcion);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarCurso(view);
            }
        });

        cargarCurso();

        return view;
    }

    public void cargarCurso(){
        Call<Curso> callCurso = Api.getApi().getCurso(getActivity().getIntent().getIntExtra("id",0));
        callCurso.enqueue(new Callback<Curso>() {
            @Override
            public void onResponse(Call<Curso> call, Response<Curso> response) {
                Log.d("TEST", "onResponse: "+ response.body().getNombre());

                Picasso.get().load(response.body().getPortada()).into(imgPortada);
                tvTitulo.setText(response.body().getNombre());
                tvDescripcion.setText(response.body().getDescripcion());
            }

            @Override
            public void onFailure(Call<Curso> call, Throwable t) {

            }
        });
    }

    private void agregarCurso(final View v){
        Call<ServerResponse> callUserCurso = Api.getApi().agregarCurso(prefs.getUsuario().getPer_id(),getActivity().getIntent().getIntExtra("id",0));
        callUserCurso.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Snackbar.make(v, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Snackbar.make(v, "Error al conectarse al servidor", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
