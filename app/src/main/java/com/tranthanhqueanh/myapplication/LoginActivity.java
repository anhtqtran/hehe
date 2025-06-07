package com.tranthanhqueanh.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private CheckBox cbSaveInfo;
    private Button btnLogin;
    private DatabaseHelper dbHelper;
    private static final String PREFS_NAME = "LoginPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        // Khởi tạo cơ sở dữ liệu
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        cbSaveInfo = findViewById(R.id.cbSaveInfo);
        btnLogin = findViewById(R.id.btnLogin);
        dbHelper = new DatabaseHelper(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbHelper.checkAndCopyDatabase();
        loadLoginInfo();

        btnLogin.setOnClickListener(v -> {
            String user = etUsername.getText().toString();
            String pass = etPassword.getText().toString();

            if (dbHelper.checkLogin(user, pass)) {
                if (cbSaveInfo.isChecked()) {
                    saveLoginInfo(user, pass);
                } else {
                    clearLoginInfo();
                }
                Intent intent = new Intent(LoginActivity.this, CategoryActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Login failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveLoginInfo(String username, String password) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putBoolean("saved", true);
        editor.apply();
    }

    private void loadLoginInfo() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (settings.getBoolean("saved", false)) {
            etUsername.setText(settings.getString("username", ""));
            etPassword.setText(settings.getString("password", ""));
            cbSaveInfo.setChecked(true);
        }
    }

    private void clearLoginInfo() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        settings.edit().clear().apply();
    }
}