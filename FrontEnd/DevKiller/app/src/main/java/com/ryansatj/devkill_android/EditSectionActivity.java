package com.ryansatj.devkill_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ryansatj.devkill_android.R;
import com.ryansatj.devkill_android.model.Section;
import com.ryansatj.devkill_android.request.BaseApiService;
import com.ryansatj.devkill_android.request.UtilsApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSectionActivity extends AppCompatActivity {

    EditText description, deadline, resources;
    Spinner priority;
    private Context mContext;
    private BaseApiService mApiService;
    String resAlerts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_section);
        mContext = this;
        mApiService = UtilsApi.getApiService(mContext);

        description = findViewById(R.id.edit_sec_desc);
        deadline = findViewById(R.id.edit_sec_deadline);
        resources = findViewById(R.id.edit_sec_resources);
        priority = findViewById(R.id.edit_prior);
        priority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                resAlerts = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("low");
        arrayList.add("medium");
        arrayList.add("high");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        priority.setAdapter(adapter);

        Button button = findViewById(R.id.edit_sec_button);
        button.setOnClickListener(v -> {
            String descriptionS, deadlineS, resourcesS;
            descriptionS = description.getText().toString();
            deadlineS = deadline.getText().toString();
            resourcesS = resources.getText().toString();
            if(descriptionS.isEmpty()){
                descriptionS = SectionListAdapter.selectedSection.description;
            }
            if(deadlineS.isEmpty()){
                deadlineS = SectionListAdapter.selectedSection.deadline;
            }
            if(resourcesS.isEmpty()){
                resourcesS = SectionListAdapter.selectedSection.resources;
            }
            handleEditSection(ProjectListAdapter.selectedProject.repository, SectionListAdapter.selectedSection.title, descriptionS, deadlineS, resourcesS, resAlerts);
        });
    }
    protected void handleEditSection(String projectrepo, String title, String description, String deadline, String resources, String alerts) {
        if (title.isEmpty()) {
            Toast.makeText(mContext, "Title IS EMPTY",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (description.isEmpty()) {
            Toast.makeText(mContext, "Description IS EMPTY",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        mApiService.editSection(projectrepo, title, description, deadline, resources, alerts).enqueue(new Callback<Section>(){
            @Override
            public void onResponse(Call<Section> call, Response<Section> response) {
                if (response.isSuccessful()) {
                    Section res = response.body();
                    if(res != null){
                        finish();
                        moveActivity(mContext, HomeActivity.class);
                    }
                }
            }
            @Override
            public void onFailure(Call<Section> call, Throwable t) {
                Toast.makeText(mContext, t.toString(),
                        Toast.LENGTH_SHORT).show();
                System.out.println(t.toString());
            }
        });
    }
    private void viewToast(Context ctx, String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }
}