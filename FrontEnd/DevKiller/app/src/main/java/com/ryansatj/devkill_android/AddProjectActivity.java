package com.ryansatj.devkill_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ryansatj.devkill_android.model.Project;
import com.ryansatj.devkill_android.model.User;
import com.ryansatj.devkill_android.request.BaseApiService;
import com.ryansatj.devkill_android.request.UtilsApi;
import com.ryansatj.devkill_android.utility.CookiesExtractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProjectActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private Context mContext;
    private SharedPreferences sharedPreferences;

    private EditText title, description, repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.addProject);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                moveActivity(mContext, HomeActivity.class);
                return true;
            } else if (itemId == R.id.addProject) {
                return true;
            } else if (itemId == R.id.user) {
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                moveActivity(mContext, UserActivity.class);
                return true;
            } else {
                return false;
            }
        });

        repository = findViewById(R.id.addproject_repo);
        title = findViewById(R.id.addproject_title);
        description = findViewById(R.id.add_description);
        sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        mContext = this;
        mApiService = UtilsApi.getApiService(mContext);
        CookiesExtractor extractor = new CookiesExtractor();
        String useridS = extractor.extract_idFromCookies(sharedPreferences);
        Integer userid = Integer.valueOf(useridS);

        Button createProjectButton = findViewById(R.id.create_project_button);
        createProjectButton.setOnClickListener(v->{
            String resTitle, resDesc, resRepo;
            resRepo = repository.getText().toString();
            resTitle = title.getText().toString();
            resDesc = description.getText().toString();
            handleAddProject(userid, resTitle, resDesc, resRepo);
        });
        Button backButton = findViewById(R.id.back_fromAddProject);
        backButton.setOnClickListener(v -> {
            finish();
            moveActivity(mContext, HomeActivity.class);
        });
    }
    protected void handleAddProject(int userid, String title, String description, String repository) {
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
        if (description.length() > 150) {
            Toast.makeText(mContext, "Description too many",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        mApiService.createProject(userid, title, description, repository).enqueue(new Callback<Project>(){
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                if (response.isSuccessful()) {
                    Project res = response.body();
                    System.out.println(res.toString());
                    if(res != null && res.id > 0){
                        finish();
                        moveActivity(mContext, HomeActivity.class);
                    } else if(res.id == -1) {
                        viewToast(mContext, "Repository Exist");
                    }
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
    private void viewToast(Context ctx, String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

}