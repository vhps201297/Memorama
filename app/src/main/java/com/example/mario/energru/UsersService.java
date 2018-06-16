package com.example.mario.energru;



import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsersService {
    @GET("usuario/")
    Call<User> getUsers();


    @POST("usuario/")
    Call<Usuario> getResponse(@Body CreateUserModel user);

    @PUT("usuario/score/{id}")
    Call<ResponseBodyServise> updateScore(@Path("id") String id, @Body UserScore score);

    @GET("usuario/score/{id}")
    Call<ResponseBodyServise> getScore(@Path("id") String id);

    @POST("login/")
    Call<ResponseBodyServise> getValidation( @Body LoginModel login);

//    @Field("name") String name, @Field("nickname") String nickname ,@Field("email") String email,@Field("password") String password,@Field("repeatPassword") String repeatPassword
}
