package com.ryansatj.devkill_android;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ryansatj.devkill_android.model.User;
import com.ryansatj.devkill_android.request.BaseApiService;
import com.ryansatj.devkill_android.request.UtilsApi;
import com.ryansatj.devkill_android.utility.CookiesExtractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private Context mContext;
    private EditText email, password;
    private TextView errEmail, errPass;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.pass_login);
        errEmail = findViewById(R.id.error_email);
        errPass = findViewById(R.id.error_pass);
        mContext = this;
        mApiService = UtilsApi.getApiService(mContext);
        Gson gson = new Gson();

        CookiesExtractor extractor = new CookiesExtractor();

        try{
            if (extractor.cookiesExist(sharedPreferences)) {
                moveActivity(mContext, HomeActivity.class);
                return;
            }
        }catch (Error error){
            System.out.println(error);
        }

        Button loginButton = findViewById(R.id.login_button);
        Button signupButton = findViewById(R.id.signup_button);

        loginButton.setOnClickListener(v -> {
            String resEmail = email.getText().toString();
            String resPassword = password.getText().toString();

            handleLogin(resEmail, resPassword);
        });

        signupButton.setOnClickListener(v -> {
            finish();
            moveActivity(this, SignupActivity.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    private void viewToast(Context ctx, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    protected void handleLogin(String email, String password) {
        if (email.isEmpty()) {
            errPass.setText("");
            errEmail.setText("This field can't be empty");
            return;
        }
        else if (password.isEmpty()) {
            errEmail.setText("");
            errPass.setText("This field can't be empty");
            return;
        }
        mApiService.getUser(email, password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User res = response.body();
                    System.out.println(res);
                    if (res != null && res.id > 0) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.apply();
                        Toast.makeText(mContext, "Selamat", Toast.LENGTH_SHORT).show();
                        finish();
                        moveActivity(mContext, HomeActivity.class);
                    } else {
                        errPass.setText("");
                        errEmail.setText("Email atau Password salah");
                        viewToast(mContext, "Email atau Password salah");
                    }
                } else {
                    Toast.makeText(mContext, "Internal Server error " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
                System.out.println(t.toString());
            }
        });
    }
}
