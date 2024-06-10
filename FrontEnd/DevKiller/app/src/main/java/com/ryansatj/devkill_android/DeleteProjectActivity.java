package com.ryansatj.devkill_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ryansatj.devkill_android.R;
import com.ryansatj.devkill_android.model.Project;
import com.ryansatj.devkill_android.request.BaseApiService;
import com.ryansatj.devkill_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteProjectActivity extends AppCompatActivity {

    private Context mContext;
    private BaseApiService mApiService;
    EditText verification;
    TextView text, errVerif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_project);
        mContext = this;
        mApiService = UtilsApi.getApiService(mContext);
        text = findViewById(R.id.sure);
        errVerif = findViewById(R.id.error_projectverif);
        verification = findViewById(R.id.delete_project_verif);
        text.setText("Type " + ProjectListAdapter.selectedProject.title + " to Continue");
        Button button = findViewById(R.id.delete_project_button);
        button.setOnClickListener(v -> {
            String verifS = verification.getText().toString();
            if(verifS.equals(ProjectListAdapter.selectedProject.title)){
                handleDeleteProject(ProjectListAdapter.selectedProject.repository);
            }
            else{
                errVerif.setText("Samakan dengan project title");
            }
        });
    }
    protected void handleDeleteProject(String repository) {
        mApiService.deleteProject(repository).enqueue(new Callback<Project>(){
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                if (response.isSuccessful()) {
                    moveActivity(mContext, HomeActivity.class);
                }
            }
            @Override
            public void onFailure(Call<Project> call, Throwable t) {
                Toast.makeText(mContext, t.toString(),
                        Toast.LENGTH_SHORT).show();
                System.out.println(t.toString());
                moveActivity(mContext, HomeActivity.class);
            }
        });
    }
    public void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }
}