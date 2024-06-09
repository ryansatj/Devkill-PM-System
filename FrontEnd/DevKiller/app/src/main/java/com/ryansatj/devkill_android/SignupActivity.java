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

public class SignupActivity extends AppCompatActivity {
    private Context mContext;
    private BaseApiService mApiService;
    private EditText name, email, password, username;
    private TextView errName,errUsername, errEmail, errPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            getSupportActionBar().hide();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.signup_name);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        username = findViewById(R.id.signup_username);
        errEmail = findViewById(R.id.error_email);
        errPass = findViewById(R.id.error_pass);
        errName = findViewById(R.id.error_name);
        errUsername = findViewById(R.id.error_username);

        mContext = this;
        mApiService = UtilsApi.getApiService(mContext);

        Button loginButton = findViewById(R.id.login_button);
        Button signupButton = findViewById(R.id.signup_button);

        loginButton.setOnClickListener(v->{
            finish();
            moveActivity(mContext, LoginActivity.class);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        signupButton.setOnClickListener(v->{
            String resEmail, resName, resPassword, resUsername;
            resName = name.getText().toString();
            resUsername = username.getText().toString();
            resEmail = email.getText().toString();
            resPassword = password.getText().toString();
            handleSignup(resName, resEmail, resPassword, resUsername);
        });

    }
    private void viewToast(Context ctx, String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    protected void handleSignup(String name, String email, String password, String username) {
        if (name.isEmpty()) {
            errEmail.setText("");
            errPass.setText("");
            errUsername.setText("");
            errName.setText("This Field Cannot be empty");
            return;
        } else if(username.isEmpty()){
            errEmail.setText("");
            errPass.setText("");
            errName.setText("");
            errUsername.setText("This Field Cannot be empty");
            return;
        } else if (email.isEmpty()) {
            errUsername.setText("");
            errPass.setText("");
            errName.setText("");
            errEmail.setText("This Field Cannot be empty");
            return;
        } else if (password.isEmpty()){
            errUsername.setText("");
            errEmail.setText("");
            errName.setText("");
            errPass.setText("This Field Cannot be empty");
            return;
        }
        mApiService.createUser(name, email, password, username).enqueue(new Callback<User>(){
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User res = response.body();
                    if (res != null && res.id > 0) {
                        Toast.makeText(mContext, "Akun telah dibuat", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if(res.id == -1){
                        errEmail.setText("");
                        errPass.setText("");
                        errName.setText("");
                        errEmail.setText("Email telah digunakan");
                    } else if (res.id == -2) {
                        errEmail.setText("");
                        errPass.setText("");
                        errName.setText("");
                        errUsername.setText("Username telah digunakan");
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
}