package com.example.mario.energru;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private ArrayList<Usuario> list;

    private Context context;


    public UsersAdapter() {
        this.list = new ArrayList<>();
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row,parent,false);
        return new UsersViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UsersViewHolder holder, int position) {
        final Usuario user = list.get(position);
        holder.tvName.setText(user.getNickname());
        holder.tvScore.setText(user.getScore());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addUsersRV(List<Usuario> lista_usr) {
        list.addAll(lista_usr);
        notifyDataSetChanged();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {


        TextView tvName;
        TextView tvScore;

        public UsersViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.user_name_);
            tvScore = (TextView) itemView.findViewById(R.id.user_score_);
        }

    }

}
