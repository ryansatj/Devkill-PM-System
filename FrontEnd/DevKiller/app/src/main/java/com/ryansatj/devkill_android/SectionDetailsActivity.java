package com.ryansatj.devkill_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ryansatj.devkill_android.model.Project;
import com.ryansatj.devkill_android.model.Section;
import com.ryansatj.devkill_android.request.BaseApiService;
import com.ryansatj.devkill_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SectionDetailsActivity extends AppCompatActivity {

    private Context mContext;
    private BaseApiService mApiService;
    TextView title, deadline, description, resources, alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_details);
        title = findViewById(R.id.section_title);
        deadline = findViewById(R.id.section_deadline);
        description = findViewById(R.id.section_description);
        resources = findViewById(R.id.section_res);
        alert = findViewById(R.id.section_alerts);
        mContext = this;
        mApiService = UtilsApi.getApiService(mContext);

        System.out.println(SectionListAdapter.selectedSection.description);
        title.setText(SectionListAdapter.selectedSection.title);
        deadline.setText("Deadline: " + SectionListAdapter.selectedSection.deadline.substring(0,10));
        description.setText(SectionListAdapter.selectedSection.description);
        resources.setText("Resources: " + SectionListAdapter.selectedSection.resources);
        alert.setText("Priority: " + SectionListAdapter.selectedSection.alerts);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.section_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.delete_section) {
            showDeleteConfirmationDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_section_delete, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
        Button btnLogout = dialogView.findViewById(R.id.btn_logout);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDeleteSection(ProjectListAdapter.selectedProject.repository, SectionListAdapter.selectedSection.title);
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
    }

    public void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }
    protected void handleDeleteSection(String repository, String title) {
        mApiService.deleteSection(repository, title).enqueue(new Callback<Section>(){
            @Override
            public void onResponse(Call<Section> call, Response<Section> response) {
                if (response.isSuccessful()) {
                    moveActivity(mContext, HomeActivity.class);
                }
            }
            @Override
            public void onFailure(Call<Section> call, Throwable t) {
                Toast.makeText(mContext, t.toString(),
                        Toast.LENGTH_SHORT).show();
                System.out.println(t.toString());
                //moveActivity(mContext, HomeActivity.class);
            }
        });
    }
}
