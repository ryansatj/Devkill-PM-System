package com.ryansatj.devkill_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ryansatj.devkill_android.model.Project;
import com.ryansatj.devkill_android.model.Section;
import com.ryansatj.devkill_android.request.BaseApiService;
import com.ryansatj.devkill_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectDetailsActivity extends AppCompatActivity {

    private Context mContext;
    private BaseApiService mApiService;
    private SharedPreferences sharedPreferences;

    private List<Section> sections = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
        mContext = this;
        mApiService = UtilsApi.getApiService(mContext);
        TextView title, descriptions;
        title = findViewById(R.id.project_title);
        descriptions = findViewById(R.id.project_descriptions);
        title.setText(ProjectListAdapter.selectedProject.title);
        descriptions.setText(ProjectListAdapter.selectedProject.descriptions);
        handleAllSections(ProjectListAdapter.selectedProject.repository);
    }

    protected void handleAllSections(String projectRepo) {

        mApiService.getAllSection(projectRepo).enqueue(new Callback<List<Section>>() {
            @Override
            public void onResponse(Call<List<Section>> call, Response<List<Section>> response) {
                if (response.isSuccessful()) {
                    List<Section> res = response.body();
                    if (res != null) {
                        sections = res;
                        SectionListAdapter sectionListAdapter = new SectionListAdapter(mContext, sections);
                        ListView sectionsListView = findViewById(R.id.sections_view);
                        sectionsListView.setAdapter(sectionListAdapter);
                    } else {
                        viewToast(mContext, "Error");
                    }
                } else {
                    Toast.makeText(mContext, "Internal Server error " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Section>> call, Throwable t) {
                Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
                System.out.println(t.toString());
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.project_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.user_add){
            finish();
            moveActivity(this, AddUserToProjectActivity.class);
            return true;
        }
        if(itemId == R.id.new_section){
            moveActivity(this, AddSectionActivity.class);
            finish();
            return true;
        }
        if(itemId == R.id.users){
            finish();
            moveActivity(this, UsersOnProjectActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }
    private void viewToast(Context ctx, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}