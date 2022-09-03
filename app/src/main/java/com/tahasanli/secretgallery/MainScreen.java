package com.tahasanli.secretgallery;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.tahasanli.secretgallery.databinding.MainscreenBinding;

public class MainScreen extends AppCompatActivity {
    private MainscreenBinding binding;

    public static MainScreen instance;
    public Photo[] photos;

    private MainScreenAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        photos = new Photo[0];
        instance = this;

        binding = MainscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.MainScreenRecycler.setLayoutManager(new GridLayoutManager(this,2));
        binding.MainScreenRecycler.setAdapter(adapter = new MainScreenAdapter());
    }

    @Override
    protected void onResume() {
        super.onResume();

        photos = Photo.GetAllPhotos().toArray(new Photo[0]);
        adapter.notifyDataSetChanged();
    }

    public void addNewPhoto(View view) {
        startActivity(new Intent(this, AddScreen.class));
    }
}
