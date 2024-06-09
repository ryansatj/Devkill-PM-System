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

import com.ryansatj.devkill_android.R;
import com.ryansatj.devkill_android.model.User;
import com.ryansatj.devkill_android.request.BaseApiService;
import com.ryansatj.devkill_android.request.UtilsApi;
import com.ryansatj.devkill_android.utility.CookiesExtractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserActivity extends AppCompatActivity {

    private Context mContext;
    private BaseApiService mApiService;
    private CookiesExtractor extractor = new CookiesExtractor();
    private SharedPreferences sharedPreferences;
    private TextView errUser, errEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        mContext = this;
        mApiService = UtilsApi.getApiService(mContext);

        errUser = findViewById(R.id.error_user_edit);
        errEmail = findViewById(R.id.error_email_edit);
        EditText username, name, email;
        username = findViewById(R.id.edit_username);
        username.setHint(extractor.extractUsernameFromCookies(sharedPreferences));
        name = findViewById(R.id.edit_name);
        name.setHint(extractor.extractNameFromCookies(sharedPreferences));
        email = findViewById(R.id.edit_email);
        email.setHint(extractor.extractEmailFromCookies(sharedPreferences));

        Button editNow = findViewById(R.id.user_edit_button);

        editNow.setOnClickListener(v -> {
            String usernameS, nameS, emailS;
            Integer _id = Integer.valueOf(extractor.extractIdFromCookies(sharedPreferences));
            usernameS = username.getText().toString();
            nameS = name.getText().toString();
            emailS = email.getText().toString();
            if (nameS.isEmpty()) {
                nameS = extractor.extractNameFromCookies(sharedPreferences);
            }
            if(usernameS.isEmpty()){
                usernameS = extractor.extractUsernameFromCookies(sharedPreferences);
            }
            if (emailS.isEmpty()) {
                emailS = extractor.extractEmailFromCookies(sharedPreferences);
                System.out.println(email);
            }
            handleUserEdit(_id, usernameS, nameS, emailS);
        });

    }
    protected void handleUserEdit(int id, String username, String name, String email) {

        mApiService.editUser(id, username, name, email).enqueue(new Callback<User>(){
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User res = response.body();
                    if (res != null && res.id > 0) {
                        Toast.makeText(mContext, "Akun di edit silahkan login kembali", Toast.LENGTH_SHORT).show();
                        finish();
                        extractor.deleteCookies(sharedPreferences);
                        moveActivity(mContext, LoginActivity.class);
                    } else if(res.id == -1){
                        errUser.setText("Username already Used");
                    } else if (res.id == -2) {
                        errEmail.setText("Email Already Used");
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
    public void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }
}