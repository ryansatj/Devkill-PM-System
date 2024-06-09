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
import android.widget.Button;
import android.widget.TextView;

import com.ryansatj.devkill_android.model.Project;

import java.util.List;

public class ProjectListAdapter extends ArrayAdapter<Project> {
    private Context mContext;
    public static Project selectedProject;

    public ProjectListAdapter(@NonNull Context context, List<Project> list) {
        super(context, 0, list);
        mContext = context;
    }   @NonNull

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_project_list_adapter, parent, false);
        }
        Project project = getItem(position);
        if (project != null) {
            TextView textView1 = currentItemView.findViewById(R.id.text_view);
            textView1.setText(project.title);
            textView1.setOnClickListener(v -> {
                selectedProject = getItem(position);
                Intent intent = new Intent(mContext, ProjectDetailsActivity.class);
                mContext.startActivity(intent);
            });
        }
        return currentItemView;
    }
}