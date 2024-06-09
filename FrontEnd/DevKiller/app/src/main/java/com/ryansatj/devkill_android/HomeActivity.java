package com.ryansatj.devkill_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.ryansatj.devkill_android.model.Project;
import com.ryansatj.devkill_android.model.User;
import com.ryansatj.devkill_android.request.BaseApiService;
import com.ryansatj.devkill_android.request.CookiesInterceptor;
import com.ryansatj.devkill_android.request.UtilsApi;
import com.ryansatj.devkill_android.utility.CookiesExtractor;
import android.view.MenuInflater;
import android.widget.Toast;


import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    List<Project> projects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = this;
        mApiService = UtilsApi.getApiService(mContext);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                return true;
            } else if (itemId == R.id.addProject) {
                finish();
                moveActivity(mContext, AddProjectActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            } else if (itemId == R.id.user) {
                finish();
                moveActivity(mContext, UserActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            } else {
                return false;
            }
        });

        CookiesExtractor extractor = new CookiesExtractor();
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String nameS = extractor.extractNameFromCookies(sharedPreferences);
        String useridS = extractor.extract_idFromCookies(sharedPreferences);
        System.out.println(useridS);
        TextView textView = findViewById(R.id.title);
        Integer userid = Integer.valueOf(useridS);
        textView.setText("Hello World!, " + nameS);
        handleAllProject(userid);
    }
//    public boolean onCreateOptionsMenu(Menu menu){
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.bottom_navbar_menu, menu);
//        return true;
//    }
    public void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int itemId = item.getItemId();
//        if(itemId == R.id.home){
//            finish();
//            moveActivity(this, HomeActivity.class);
//            return true;
//        }
//        if(itemId == R.id.addProject){
//            moveActivity(this, AddProjectActivity.class);
//            return true;
//        }
//        if(itemId == R.id.user){
//            moveActivity(this, UserActivity.class);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    protected void handleAllProject(int userid) {

        mApiService.getAllProject(userid).enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if (response.isSuccessful()) {
                    List<Project> res = response.body();
                    //System.out.println(res.toString());
                    if (res != null) {
                        projects = res;
                        ProjectListAdapter projectAdapter = new ProjectListAdapter(mContext, projects);
                        ListView projectListView = findViewById(R.id.list_project);
                        projectListView.setAdapter(projectAdapter);
                    } else {
                        Project project = new Project(-1, "error", -1,"error", "error");
                        projects.add(project);
                        viewToast(mContext, "Email atau Password salah");
                    }
                } else {
                    Toast.makeText(mContext, "Internal Server error " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
                System.out.println(t.toString());
            }
        });
    }
    private void viewToast(Context ctx, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
