package com.example.mario.energru.view;


import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.mario.energru.R;
import com.example.mario.energru.view.fragments.PlayFragment;
import com.example.mario.energru.view.fragments.ProfileFragment;
import com.example.mario.energru.view.fragments.ProgressFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class ContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        BottomBar bottomBar = findViewById(R.id.bottombar);
        bottomBar.setDefaultTab(R.id.play);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {

                switch (tabId) {
                    case R.id.play:
                        showToolbar(getResources().getString(R.string.toolbar_inicio),false);
                        PlayFragment playFragment = new PlayFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_aplication,playFragment)
                        .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK).addToBackStack(null).commit();
                        break;
                    case R.id.profile:
                        showToolbar(getResources().getString(R.string.toolbar_editar),true);
                        ProfileFragment profileFragment = new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_aplication,profileFragment)
                                .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK).addToBackStack(null).commit();
                        break;
                    case R.id.progress:
                        showToolbar(getResources().getString(R.string.toolbar_estadisticas),false);
                        ProgressFragment progressFragment = new ProgressFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_aplication,progressFragment)
                                .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK).addToBackStack(null).commit();
                        break;

                }
            }
        });
    }

    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar = (Toolbar)  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
}
