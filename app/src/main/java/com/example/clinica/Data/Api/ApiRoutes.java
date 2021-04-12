package com.example.clinica.Data.Api;

import com.example.clinica.Data.Model.Categoria;
import com.example.clinica.Data.Model.Curso;
import com.example.clinica.Data.Model.LoginBody;
import com.example.clinica.Data.Model.Paciente;
import com.example.clinica.Data.Model.Personal;
import com.example.clinica.Data.Model.Profesor;
import com.example.clinica.Data.Model.ServerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiRoutes {

  @POST("loguear")
  Call<Personal> login(@Body LoginBody loginBody);

  @POST("GuardarPersonales")
  Call<Personal> registrar(@Body Personal personal);

  @POST("EditarPersonales/editar")
  Call<Personal> editarPersonal(@Body Personal personal);


  @POST("guardarPacientes")
  Call<Paciente> registrar(@Body Paciente paciente);

  @GET("ListarPersonales")
  Call<List<Personal>> getPersonal();


  @GET("cursos")
  Call<List<Curso>> getCursos();

  @GET("curso/{id}")
  Call<Curso> getCurso(@Path("id") int id);

  @GET("categorias")
  Call<List<Categoria>> getCategorias();

  @GET("categoria/cursos/{id}")
  Call<List<Curso>> getCategoriaDetalle(@Path("id") int id);

  @GET("profesores")
  Call<List<Profesor>> getProfesores();

  @GET("profesor/cursos/{id}")
  Call<List<Curso>> getProfesorDetalle(@Path("id") int id);

  @GET("user/{id}")
  Call<Personal> getUser(@Path("id") int id);



  @POST("user/curso/{user_id}/{curso_id}")
  Call<ServerResponse> agregarCurso(@Path("user_id") int user_id, @Path("curso_id") int curso_id);

  @GET("curso/user/{id}")
  Call<List<Curso>> getCursosUser(@Path("id") int id);
}
