package com.ryansatj.devkill_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.ryansatj.devkill_android.model.Section;
import com.ryansatj.devkill_android.model.User;
import com.ryansatj.devkill_android.request.BaseApiService;
import com.ryansatj.devkill_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersOnProjectActivity extends AppCompatActivity {

    private Context mContext;
    private BaseApiService mApiService;

    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_on_project);
        mContext = this;
        mApiService = UtilsApi.getApiService(mContext);
        System.out.println(ProjectListAdapter.selectedProject.toString());
        handleUsers(ProjectListAdapter.selectedProject.repository);
    }
    protected void handleUsers(String repository) {

        mApiService.getAllUserOnProject(repository).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> res = response.body();
                    if (res != null) {
                        users = res;
                        UserListAdapter userListAdapter = new UserListAdapter(mContext, users);
                        ListView userListView = findViewById(R.id.users_view);
                        userListView.setAdapter(userListAdapter);
                    } else {
                        viewToast(mContext, "Error");
                    }
                } else {
                    Toast.makeText(mContext, "Internal Server error " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
                System.out.println(t);
            }
        });
    }
    private void viewToast(Context ctx, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}