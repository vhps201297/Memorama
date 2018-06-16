package com.example.mario.energru.view.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mario.energru.LoginModel;
import com.example.mario.energru.R;
import com.example.mario.energru.ResponseBodyServise;
import com.example.mario.energru.UserScore;
import com.example.mario.energru.UsersService;
import com.example.mario.energru.view.ContainerActivity;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayFragment extends Fragment implements View.OnClickListener{
    private Retrofit retrofit;
    String baseUrl = "https://memorama-fi-unam.herokuapp.com/";

    ImageButton imbCarta1, imbCarta2, imbCarta3, imbCarta4, imbCarta5, imbCarta6, imbCarta7, imbCarta8;
    int[] imagenes = {R.drawable.ic_image1,R.drawable.ic_image2,R.drawable.ic_image3,R.drawable.ic_image4};

    int[] juego = new int[8];
    int[] cartas_selecionadas = new int[2];
    int[] imagenes_selecionadas = new int[2];
    int[] juego_terminado = new int[8];
    int turno=0, ganador=0;

    SharedPreferences userDataPrefs;

    String getName;
    String getId;

    public PlayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_play, container, false);

        userDataPrefs = getContext().getSharedPreferences("userData", Context.MODE_PRIVATE);

        getName = userDataPrefs.getString("nombreUser", "");
        getId = userDataPrefs.getString("idtoCompare", "");

        imbCarta1 = (ImageButton) view.findViewById(R.id.imbCarta1);
        imbCarta1.setOnClickListener(this);
        imbCarta2 = (ImageButton)  view.findViewById(R.id.imbCarta2);
        imbCarta2.setOnClickListener(this);
        imbCarta3 = (ImageButton) view.findViewById(R.id.imbCarta3);
        imbCarta3.setOnClickListener(this);
        imbCarta4 = (ImageButton) view.findViewById(R.id.imbCarta4);
        imbCarta4.setOnClickListener(this);
        imbCarta5 = (ImageButton) view.findViewById(R.id.imbCarta5);
        imbCarta5.setOnClickListener(this);
        imbCarta6 = (ImageButton) view.findViewById(R.id.imbCarta6);
        imbCarta6.setOnClickListener(this);
        imbCarta7 = (ImageButton) view.findViewById(R.id.imbCarta7);
        imbCarta7.setOnClickListener(this);
        imbCarta8 = (ImageButton) view.findViewById(R.id.imbCarta8);
        imbCarta8.setOnClickListener(this);

        Toast.makeText(getContext(),getId,Toast.LENGTH_SHORT).show();
        if (savedInstanceState == null)
        {
            asignarImagenes();
            for (int i=0;i<8;i++)
                juego_terminado[i]=0;

            cartas_selecionadas[0]=8;
        }

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imbCarta1:
                imbCarta1.setImageResource(juego[0]);
                imagenes_selecionadas[turno]=juego[0];
                cartas_selecionadas[turno]=0;
                imbCarta1.setEnabled(false);
                break;
            case R.id.imbCarta2:
                imbCarta2.setImageResource(juego[1]);
                imagenes_selecionadas[turno]=juego[1];
                cartas_selecionadas[turno]=1;
                imbCarta2.setEnabled(false);
                break;
            case R.id.imbCarta3:
                imbCarta3.setImageResource(juego[2]);
                imagenes_selecionadas[turno]=juego[2];
                cartas_selecionadas[turno]=2;
                imbCarta3.setEnabled(false);
                break;
            case R.id.imbCarta4:
                imbCarta4.setImageResource(juego[3]);
                imagenes_selecionadas[turno]=juego[3];
                cartas_selecionadas[turno]=3;
                imbCarta4.setEnabled(false);
                break;
            case R.id.imbCarta5:
                imbCarta5.setImageResource(juego[4]);
                imagenes_selecionadas[turno]=juego[4];
                cartas_selecionadas[turno]=4;
                imbCarta5.setEnabled(false);
                break;
            case R.id.imbCarta6:
                imbCarta6.setImageResource(juego[5]);
                imagenes_selecionadas[turno]=juego[5];
                cartas_selecionadas[turno]=5;
                imbCarta6.setEnabled(false);
                break;
            case R.id.imbCarta7:
                imbCarta7.setImageResource(juego[6]);
                imagenes_selecionadas[turno]=juego[6];
                cartas_selecionadas[turno]=6;
                imbCarta7.setEnabled(false);
                break;
            case R.id.imbCarta8:
                imbCarta8.setImageResource(juego[7]);
                imagenes_selecionadas[turno]=juego[7];
                cartas_selecionadas[turno]=7;
                imbCarta8.setEnabled(false);
        }
        if(turno==0) {
            turno = 1;
        }else{
            new Hilo().execute();
            turno=0;
        }

    }

    private void asignarImagenes() {
        int posicion, contador = 0;

        for (int i=0;i<4;)
        {
            posicion = (int)(Math.random()*8);

            if(juego[posicion]==0) {
                juego[posicion] = imagenes[i];
                contador++;

                if(contador == 2)
                {
                    i++;
                    contador=0;
                }

            }
        }
    }
    class Hilo extends AsyncTask<Void,Integer,Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imbCarta1.setEnabled(false);
            imbCarta2.setEnabled(false);
            imbCarta3.setEnabled(false);
            imbCarta4.setEnabled(false);
            imbCarta5.setEnabled(false);
            imbCarta6.setEnabled(false);
            imbCarta7.setEnabled(false);
            imbCarta8.setEnabled(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(300);

            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(imagenes_selecionadas[0]==imagenes_selecionadas[1])
            {
                juego_terminado[cartas_selecionadas[0]]=1;
                juego_terminado[cartas_selecionadas[1]]=1;

                ganador++;

                if(ganador==4){
                    getScore();
                    Toast.makeText(getContext(),"Eres el mejor, pronto tendremos mas niveles",Toast.LENGTH_SHORT).show();
                }

            }

            if (juego_terminado[0] == 0)
            {
                imbCarta1.setImageResource(R.drawable.ic_cards);
                imbCarta1.setEnabled(true);
            }

            if (juego_terminado[1] == 0)
            {
                imbCarta2.setImageResource(R.drawable.ic_cards);
                imbCarta2.setEnabled(true);
            }

            if (juego_terminado[2] == 0)
            {
                imbCarta3.setImageResource(R.drawable.ic_cards);
                imbCarta3.setEnabled(true);
            }

            if (juego_terminado[3] == 0)
            {
                imbCarta4.setImageResource(R.drawable.ic_cards);
                imbCarta4.setEnabled(true);
            }

            if (juego_terminado[4] == 0)
            {
                imbCarta5.setImageResource(R.drawable.ic_cards);
                imbCarta5.setEnabled(true);
            }

            if (juego_terminado[5] == 0)
            {
                imbCarta6.setImageResource(R.drawable.ic_cards);
                imbCarta6.setEnabled(true);
            }

            if (juego_terminado[6] == 0)
            {
                imbCarta7.setImageResource(R.drawable.ic_cards);
                imbCarta7.setEnabled(true);
            }

            if (juego_terminado[7] == 0)
            {
                imbCarta8.setImageResource(R.drawable.ic_cards);
                imbCarta8.setEnabled(true);
            }

            cartas_selecionadas[0]=8;

        }
    }


    public void peticion(int score){

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        UserScore score_ = new UserScore(score);

        UsersService usersService =retrofit.create(UsersService.class);
        Call<ResponseBodyServise> call = usersService.updateScore(getId,score_);

        call.enqueue(new Callback<ResponseBodyServise>() {
            @Override
            public void onResponse(Call<ResponseBodyServise> call, Response<ResponseBodyServise> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getContext(),"Tus datos se actualizaron\"",Toast.LENGTH_SHORT).show();
                }else{
                    String mensaje =response.body().getMensaje();
                    Toast.makeText(getContext(),mensaje,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBodyServise> call, Throwable t) {
                Log.e("onFailure",t.toString());
            }
        });
    }

    public void getScore(){

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        UsersService usersService =retrofit.create(UsersService.class);

        Call<ResponseBodyServise> call = usersService.getScore(getId);

        call.enqueue(new Callback<ResponseBodyServise>() {
            @Override
            public void onResponse(Call<ResponseBodyServise> call, Response<ResponseBodyServise> response) {
                if (response.isSuccessful()){
                    response.body();
                    int score =response.body().getScore();
                    peticion(score+20);

                }else{
                    String mensaje =response.body().getMensaje();
                    Toast.makeText(getContext(),mensaje,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBodyServise> call, Throwable t) {
                Log.e("onFailure",t.toString());
            }
        });
    }


}
