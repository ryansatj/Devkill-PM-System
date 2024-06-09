package com.ryansatj.devkill_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ryansatj.devkill_android.R;
import com.ryansatj.devkill_android.model.Project;
import com.ryansatj.devkill_android.model.User;
import com.ryansatj.devkill_android.request.BaseApiService;
import com.ryansatj.devkill_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProjectActivity extends AppCompatActivity {

    private BaseApiService mApiService;
    private Context mContext;
    EditText title, descriptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);
        mContext = this;
        mApiService = UtilsApi.getApiService(mContext);
        title = findViewById(R.id.edit_project_title);
        descriptions = findViewById(R.id.edit_project_desc);
        Button button = findViewById(R.id.project_edit_button);

        button.setOnClickListener(v -> {
            String titleS = title.getText().toString();
            String descriptionsS = descriptions.getText().toString();
            if(titleS.isEmpty()){
                titleS = ProjectListAdapter.selectedProject.title;
            }
            if(descriptionsS.isEmpty()){
                descriptionsS = ProjectListAdapter.selectedProject.descriptions;
            }
            handleProjectEdit(ProjectListAdapter.selectedProject.repository, titleS, descriptionsS);
        });
    }
    protected void handleProjectEdit(String repository, String title, String descriptions) {

        mApiService.editProject(repository, title, descriptions).enqueue(new Callback<Project>(){
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                if (response.isSuccessful()) {
                    Project res = response.body();
                    Toast.makeText(mContext, "Project Berhasil di Edit", Toast.LENGTH_SHORT).show();
                    finish();
                    moveActivity(mContext, HomeActivity.class);
                }
            }
            @Override
            public void onFailure(Call<Project> call, Throwable t) {
                Toast.makeText(mContext, t.toString(),
                        Toast.LENGTH_SHORT).show();
                System.out.println(t.toString());
            }
        });
    }
    public void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }
}