package com.example.mario.energru;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mario.energru.view.ContainerActivity;
import com.example.mario.energru.view.CreateAccountActivity;
import com.example.mario.energru.view.fragments.PlayFragment;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.prefs.PreferenceChangeEvent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private Retrofit retrofit;
    String baseUrl = "https://memorama-fi-unam.herokuapp.com/";

    Button btnLogin;
    EditText editTextemail;
    EditText editTextpassword;

    SharedPreferences userDataPrefs;
    SharedPreferences.Editor editorUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.login);
        editTextemail = findViewById(R.id.email);
        editTextpassword = findViewById(R.id.password);

        userDataPrefs = getSharedPreferences("userData", Context.MODE_PRIVATE);

        cargarDatos();
        validations();
    }


    public void goCreateAccount(View view) {
        Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
        startActivity(intent);
    }

    public void validations() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_bottom = getString(R.string.text_nutton_login);

                String email = editTextemail.getText().toString();
                String password = editTextpassword.getText().toString();

                if (btnLogin.getText().toString().equals(name_bottom)) {
                    if (editTextemail.getText().toString().equals(""))
                        Snackbar.make(view, "Ingresa tu correo", Snackbar.LENGTH_LONG)
                                .show();
                    else if (editTextpassword.getText().toString().equals(""))
                        Snackbar.make(view, "Ingresa la contraseña para continuar", Snackbar.LENGTH_LONG)
                                .show();
                    else {
                        peticion(email, password, view);
                    }
                }

                saveData(editTextemail.getText().toString(), editTextpassword.getText().toString());


            }
        });
    }

    public void peticion(String email, String password, final View view) {

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LoginModel login = new LoginModel(email, password);

        UsersService usersService = retrofit.create(UsersService.class);
        Call<ResponseBodyServise> call = usersService.getValidation(login);

        call.enqueue(new Callback<ResponseBodyServise>() {
            @Override
            public void onResponse(Call<ResponseBodyServise> call, Response<ResponseBodyServise> response) {
                if (response.isSuccessful()) {
                    response.body();
                    String name = response.body().getName();
                    String idtocompere = response.body().get_id();

                    Log.e("", idtocompere);

                    /*PlayFragment playFragment = new PlayFragment();

                    Bundle bundle = new Bundle();

                    bundle.putString("useridfrom", idtocompere);

                    playFragment.setArguments(bundle); */

                    editorUserData = userDataPrefs.edit();
                    editorUserData.putString("nombreUser", name);
                    editorUserData.putString("idtoCompare", idtocompere);
                    editorUserData.apply();

                    Snackbar.make(view, "Bienvenido", Snackbar.LENGTH_LONG)
                            .show();
                    Intent intent = new Intent(getApplicationContext(), ContainerActivity.class);
                    startActivity(intent);


                } else {

                    Snackbar.make(view, "Usuario o contraseña incorrecta", Snackbar.LENGTH_LONG)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBodyServise> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });
    }

    public void saveData(String name, String id) {

        SharedPreferences sharedP = getSharedPreferences("Credentiales", Context.MODE_PRIVATE);

        String user_emai_ = name;
        String user_password = id;

        SharedPreferences.Editor editor = sharedP.edit();
        editor.putString("user_email", user_emai_);
        editor.putString("user_password", user_password);
        editTextemail.setText(user_emai_);
        editTextpassword.setText(user_password);


        editor.commit();
    }

    public void cargarDatos() {

        SharedPreferences getSha = getSharedPreferences("Credentiales", Context.MODE_PRIVATE);

        String email = getSha.getString("user_email", "");
        String pass = getSha.getString("user_password", "");

        editTextemail.setText(email);
        editTextpassword.setText(pass);


    }
}
