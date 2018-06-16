package com.example.mario.energru.view;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mario.energru.CreateUserModel;
import com.example.mario.energru.LoginActivity;
import com.example.mario.energru.R;

import com.example.mario.energru.UsersService;
import com.example.mario.energru.Usuario;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CreateAccountActivity extends AppCompatActivity {

    TextView textView;
    private Retrofit retrofit;
    String baseUrl = "https://memorama-fi-unam.herokuapp.com/";


    EditText editTextname;
    EditText editTextusername;
    EditText editTextemail;

    Button btnRegister;

    EditText editTextpassword;
    EditText editTextrepeat_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        editTextname = findViewById(R.id.first_name);
        editTextusername = findViewById(R.id.nickname);
        editTextemail = findViewById(R.id.email);

        editTextpassword = findViewById(R.id.password);
        editTextrepeat_password = findViewById(R.id.repeat_password);
        btnRegister = findViewById(R.id.login);


        showToolbar(getResources().getString(R.string.toolbar_create_Account),true);
        validations();

    }

    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar = (Toolbar)  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    public void validations(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_bottom = getString(R.string.text_register);

                String name = editTextname.getText().toString();
                String nickname = editTextusername.getText().toString();
                String email = editTextemail.getText().toString();
                String password = editTextpassword.getText().toString();
                String password2 = editTextrepeat_password.getText().toString();

                if(btnRegister.getText().toString().equals(name_bottom)){

                    if(editTextname.getText().toString().equals(""))
                        Snackbar.make(view, "El nombre es necesario para continuar", Snackbar.LENGTH_LONG)
                                .show();
                    else if (editTextusername.getText().toString().equals(""))
                        Snackbar.make(view, "El nombre de usuario es necesario para continuar", Snackbar.LENGTH_LONG)
                                .show();
                    else if (editTextemail.getText().toString().equals(""))
                        Snackbar.make(view, "El Correo es necesario para continuar", Snackbar.LENGTH_LONG)
                                .show();
                    else if(editTextrepeat_password.getText().toString().equals(""))
                        Snackbar.make(view, "Agregue la contraseña para continuar", Snackbar.LENGTH_LONG)
                                .show();

                    else if(editTextrepeat_password.getText().toString().equals(""))
                        Snackbar.make(view, "Agregue la contraseña para continuar", Snackbar.LENGTH_LONG)
                                .show();
                    else if(!password.equals(password2))
                        Snackbar.make(view, "Las contraeñas no son iguales", Snackbar.LENGTH_LONG)
                                .show();
                    else {
                        peticion(name,nickname,email,password,password2,view);
                    }

                }
            }
        });

    }

    public void peticion(String name, String nickname, String email, String password, String password2, final View view){

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        CreateUserModel user = new CreateUserModel(name,nickname,email,password,password2);

        UsersService usersService =retrofit.create(UsersService.class);
        Call<Usuario> call = usersService.getResponse(user);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()){
                    Snackbar.make(view, "Ahora inicia sesion :D" , Snackbar.LENGTH_LONG)
                            .show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Email y Sobre nombre de usuario deben ser unicos",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("onFailure",t.toString());
            }
        });
    }

}
