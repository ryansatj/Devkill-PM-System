package com.ryansatj.devkill_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ryansatj.devkill_android.model.Project;
import com.ryansatj.devkill_android.model.Section;
import com.ryansatj.devkill_android.request.BaseApiService;
import com.ryansatj.devkill_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSectionActivity extends AppCompatActivity {

    private Context mContext;
    private BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_section);

        EditText title, description, deadline, resources, alerts;

        title = findViewById(R.id.addSection_title);
        description = findViewById(R.id.addSection_description);
        deadline = findViewById(R.id.addSection_deadline);
        resources = findViewById(R.id.addSection_resources);
        alerts = findViewById(R.id.addSection_alerts);
        mContext = this;
        mApiService = UtilsApi.getApiService(mContext);

        Button addSection = findViewById(R.id.addSection_button);
        addSection.setOnClickListener(v -> {
            String resTitle, resDesc, resDeadline, resResources, resAlerts;
            resTitle = title.getText().toString();
            resDesc = description.getText().toString();
            resDeadline = deadline.getText().toString();
            resResources = resources.getText().toString();
            resAlerts = alerts.getText().toString();
            handleAddSection(ProjectListAdapter.selectedProject.repository, resTitle, resDesc, resDeadline, resResources, resAlerts);
        });
    }

    protected void handleAddSection(String projectrepo, String title, String description, String deadline, String resources, String alerts) {
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
        mApiService.createSection(projectrepo, title, description, deadline, resources, alerts).enqueue(new Callback<Section>(){
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