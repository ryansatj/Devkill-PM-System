package com.ryansatj.devkill_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ryansatj.devkill_android.model.Project;
import com.ryansatj.devkill_android.model.Section;
import com.ryansatj.devkill_android.model.User;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<User> {
    private Context mContext;
    public UserListAdapter(@NonNull Context context, List<User> list) {
        super(context, 0, list);
        mContext = context;
    }   @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_user_list_adapter, parent, false);
        }
        User user = getItem(position);
        if (user != null) {
            TextView textView1 = currentItemView.findViewById(R.id.text_view);
            textView1.setText(user.name);
        }
        return currentItemView;
    }
}