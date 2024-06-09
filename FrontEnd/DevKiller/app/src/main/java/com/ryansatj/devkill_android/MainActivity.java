package com.ryansatj.devkill_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.ryansatj.devkill_android.utility.CookiesExtractor;

public class MainActivity extends AppCompatActivity {

    private Button signup, login;
    private SharedPreferences sharedPreferences;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        signup = findViewById(R.id.join_us);
        login = findViewById(R.id.login_now);

        signup.setOnClickListener(v -> {
            moveActivity(this, SignupActivity.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        login.setOnClickListener(v -> {
            moveActivity(this, LoginActivity.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        });
        mContext = this;
        CookiesExtractor extractor = new CookiesExtractor();

        try{
            if (extractor.cookiesExist(sharedPreferences)) {
                moveActivity(mContext, HomeActivity.class);
                return;
            }
        }catch (Error error){
            System.out.println(error);
        }

    }
    public void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }
}