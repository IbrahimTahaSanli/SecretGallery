package com.tahasanli.secretgallery;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tahasanli.secretgallery.databinding.CreatepasswordBinding;

public class CreatePassword extends AppCompatActivity {
    public CreatepasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CreatepasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void savePassword(View view) {
        if(binding.password.getText().toString().equals(binding.verifyPassword.getText().toString()))
        {
            MainActivity.instance.db.execSQL("DROP TABLE IF EXISTS " + MainActivity.DBTableName );
            MainActivity.instance.db.execSQL("CREATE TABLE IF NOT EXISTS " + MainActivity.DBTableName + "(id INTEGER PRIMARY KEY, name VARCHAR, date VARCHAR, image BLOB)");
            MainActivity.prefs.edit().putString(MainActivity.PasswordPrefKey, binding.password.getText().toString()).apply();
            this.finish();
        }
        else{
            Toast.makeText( getApplicationContext(), "Password dont match!", Toast.LENGTH_SHORT).show();
            binding.password.setText("");
            binding.verifyPassword.setText("");
        }
    }
}
