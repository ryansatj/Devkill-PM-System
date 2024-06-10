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
import com.ryansatj.devkill_android.model.Section;

import java.util.List;

public class SectionListAdapter extends ArrayAdapter<Section> {
    private Context mContext;
    public static Section selectedSection;

    public SectionListAdapter(@NonNull Context context, List<Section> list) {
        super(context, 0, list);
        mContext = context;
    }   @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_section_list_adapter, parent, false);
        }
        Section section = getItem(position);
        if (section != null) {
            TextView textView1 = currentItemView.findViewById(R.id.text_view);
            TextView textView2 = currentItemView.findViewById(R.id.text_view2);
            textView1.setText(section.title);
            textView2.setText("Deadline: " + section.deadline.substring(0,10));
            textView1.setOnClickListener(v -> {
                selectedSection = getItem(position);
                Intent intent = new Intent(mContext, SectionDetailsActivity.class);
                mContext.startActivity(intent);
            });
        }
        return currentItemView;
    }
}