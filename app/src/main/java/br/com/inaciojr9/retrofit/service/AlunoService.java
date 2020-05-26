package br.com.inaciojr9.retrofit.service;

import java.util.List;

import br.com.inaciojr9.retrofit.model.Aluno;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AlunoService {

    //@Headers("X-Mashape-Key: AuuyclCPjcmshv2iOPq190OpzLrMp1FJWwejsnJrdfwOUr4h44")

    //@FormUrlEncoded
     /*@POST("convert")
    Call<RespostaServidor> converterUnidade(@Field("from-type") String from_type,
                                            @Field("from-value") String from_value,
                                            @Field("to-type") String to_type);*/
    /*
    @GET("users")
    Call<User> getUserById(@Query("id") Integer id);
     */

    @Headers("Content-Type: application/json")

    @POST("alunos")
    Call<Aluno> salvar(@Body Aluno aluno);

    @GET("alunos")
    Call<List<Aluno>> listarTodos();

    @GET("alunos/{id}")
    Call<Aluno> obterPorId(@Path("id") Long id);

    @PUT("alunos/{id}")
    Call<Void> atualizar(@Path("id") Long id, @Body Aluno aluno);

    @DELETE("alunos/{id}")
    Call<Void> excluir(@Path("id") Long id);

    /*
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    */

}