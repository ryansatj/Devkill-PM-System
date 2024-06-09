package com.ryansatj.devkill_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ryansatj.devkill_android.utility.CookiesExtractor;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        CookiesExtractor extractor = new CookiesExtractor();
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        Context mContext = this;

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.user);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                finish();
                moveActivity(mContext, HomeActivity.class);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;
            } else if (itemId == R.id.addProject) {
                finish();
                moveActivity(mContext, AddProjectActivity.class);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;
            } else if (itemId == R.id.user) {
                return true;
            } else {
                return false;
            }
        });

        TextView nameTextview = findViewById(R.id.details_name);
        TextView emailTextview = findViewById(R.id.details_email);
        TextView usernameTextview = findViewById(R.id.details_username);
        TextView initialTextview = findViewById(R.id.initial);
        Button signoutButton = findViewById(R.id.signout_button);

        String name = extractor.extractNameFromCookies(sharedPreferences);
        String email = extractor.extractEmailFromCookies(sharedPreferences);
        String username = extractor.extractUsernameFromCookies(sharedPreferences);
        String initial = name.charAt(0) + "";

        nameTextview.setText(name);
        emailTextview.setText(email);
        usernameTextview.setText(username);
        initialTextview.setText(initial);

        signoutButton.setOnClickListener(v->{
            extractor.deleteCookies(sharedPreferences);
            moveActivity(mContext, LoginActivity.class);
        });

        Button edit = findViewById(R.id.editUser_button);
        edit.setOnClickListener(v -> {
            moveActivity(this, EditUserActivity.class);
        });
    }

    public void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

}