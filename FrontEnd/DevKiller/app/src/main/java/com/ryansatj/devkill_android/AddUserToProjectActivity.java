package com.ryansatj.devkill_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ryansatj.devkill_android.model.User;
import com.ryansatj.devkill_android.request.BaseApiService;
import com.ryansatj.devkill_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserToProjectActivity extends AppCompatActivity {

    private Context mContext;
    private BaseApiService mApiService;

    private TextView errMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_to_project);
        mContext = this;
        mApiService = UtilsApi.getApiService(mContext);
        EditText username = findViewById(R.id.add_username);
        Button addUser = findViewById(R.id.addUser_button);
        errMsg = findViewById(R.id.error_addUser);

        addUser.setOnClickListener(v -> {
            String resUser;
            resUser = username.getText().toString();
            handleAddUser(ProjectListAdapter.selectedProject.repository, resUser);
        });
    }
    protected void handleAddUser(String repository, String username) {
        if (username.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        mApiService.addToProject(repository, username).enqueue(new Callback<User>(){
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    User res = response.body();
                    System.out.println(res.name);
                    if (res != null){
                        if(res.id > 0){
                            finish();
                            moveActivity(mContext, HomeActivity.class);
                        }
                        else if(res.id == -1){
                            errMsg.setText("Username Already Exist");
                            viewToast(mContext, "Username Already Exist");
                        }
                        else if(res.id == -2){
                            errMsg.setText("Username Not Found");
                            viewToast(mContext, "Username Not Found");
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(mContext, t.toString(),
                        Toast.LENGTH_SHORT).show();
                System.out.println(t.toString());
            }
        });
    }
    private void viewToast(Context ctx, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

}