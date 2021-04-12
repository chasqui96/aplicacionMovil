package com.example.clinica.Views.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.clinica.R;
import com.example.clinica.Views.Adapter.CursoDetalleViewPagerAdapter;
import com.example.clinica.Views.Fragment.FragmentDetalleCurso;
import com.example.clinica.Views.Fragment.FragmentVideosCurso;

public class CursoDetalleActivity extends AppCompatActivity {

    private TabLayout tabCurso;
    private ViewPager pagerCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso_detalle);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setElevation(0);
        actionBar.setTitle("Curso");

        pagerCurso = findViewById(R.id.pagerCurso);
        setupViewPager(pagerCurso);

        tabCurso = findViewById(R.id.tabCurso);
        tabCurso.setupWithViewPager(pagerCurso);
    }

    private void setupViewPager(ViewPager vp){
        CursoDetalleViewPagerAdapter adapter = new CursoDetalleViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentDetalleCurso(),"DETALLE");
        adapter.addFragment(new FragmentVideosCurso(),"VIDEOS");
        vp.setAdapter(adapter);
    }
}
