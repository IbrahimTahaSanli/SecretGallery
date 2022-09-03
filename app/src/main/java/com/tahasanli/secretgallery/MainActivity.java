package com.tahasanli.secretgallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tahasanli.secretgallery.databinding.ActivityMainBinding;
import com.tahasanli.secretgallery.databinding.CreatepasswordBinding;

public class MainActivity extends AppCompatActivity {

    public static SharedPreferences prefs;
    public static final String PasswordPrefKey = "PASSWORDSHAREDPREFERENCESKEY";
    public static MainActivity instance;

    public ActivityMainBinding binding;

    public SQLiteDatabase db;
    public static final String DBTableName = "Photos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;

        db = this.openOrCreateDatabase(getString(R.string.app_name), MODE_PRIVATE, null);

        prefs = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        if(prefs.getString(PasswordPrefKey,"").matches("")) {
            startActivity(new Intent(this, CreatePassword.class));
        }
        else {

            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        binding.editTextTextPassword.setText("");
    }

    public void createPassword(View view) {
        startActivity(new Intent(this, CreatePassword.class));
    }

    public void getIn(View view) {
        if(prefs.getString(PasswordPrefKey, "").toString().equals(binding.editTextTextPassword.getText().toString()))
            startActivity(new Intent(this, MainScreen.class));
        else
            Toast.makeText(getApplicationContext(), R.string.MainActivityWrongPass,Toast.LENGTH_LONG).show();

    }
}