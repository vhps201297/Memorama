package com.example.mario.energru.view.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.example.mario.energru.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    SharedPreferences userDataPrefs;
    String getName;
    String getId;

    TextView Textname;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        userDataPrefs = getContext().getSharedPreferences("userData", Context.MODE_PRIVATE);
        getName = userDataPrefs.getString("nombreUser", "");
        Textname = (TextView) view.findViewById(R.id.user_name_dataBase);
        Textname.setText("Buen día "+ getName);

        return view;
    }

}
