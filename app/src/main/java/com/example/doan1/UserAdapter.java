package com.example.doan1;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private ArrayList<User_Page> userList;

    public UserAdapter(ArrayList<User_Page> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_page, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User_Page user = userList.get(position);
        holder.userNameTextView.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userProfileImageView;
        TextView userNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userProfileImageView = itemView.findViewById(R.id.userview);
            userNameTextView = itemView.findViewById(R.id.username);
        }
    }
}