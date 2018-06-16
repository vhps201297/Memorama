package com.example.mario.energru.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mario.energru.R;
import com.example.mario.energru.User;
import com.example.mario.energru.UsersAdapter;
import com.example.mario.energru.UsersService;
import com.example.mario.energru.Usuario;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressFragment extends Fragment {

    String baseUrl = "https://memorama-fi-unam.herokuapp.com/";

    private RecyclerView rvUsers;
    private UsersAdapter adapter;
    private Retrofit retrofit;
    private ArrayList<Usuario> list;

    public ProgressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        initRecyclerView(view);
        initService();
        return view;
    }

    public void initRecyclerView(View view){

        rvUsers = (RecyclerView) view.findViewById(R.id.rvUsers);
        adapter = new UsersAdapter();
        rvUsers.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        rvUsers.setLayoutManager(llm);


        rvUsers.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(rvUsers.getContext(),llm.getOrientation());
        rvUsers.addItemDecoration(itemDecoration);
    }

    public void initService(){
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        UsersService usersService =retrofit.create(UsersService.class);
        Call<User> userLit = usersService.getUsers();

        userLit.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    User responde = response.body();
                    List<Usuario> lista_usr = responde.getUsuarios();
                    adapter.addUsersRV(lista_usr);

                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("error", t.toString());

            }
        });
    }



}
