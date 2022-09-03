package com.tahasanli.secretgallery;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.icu.util.Measure;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.tahasanli.secretgallery.databinding.AddscreenBinding;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class AddScreen extends AppCompatActivity {
    private AddscreenBinding binding;

    public static final String IDKey = "INTENTID";

    private Photo currentPhoto;

    private ActivityResultLauncher<Intent> resultIntent;
    private ActivityResultLauncher<String> resultString;

    @Override
    protected void onCreate(Bundle savedBundle) {
        super.onCreate(savedBundle);
        binding = AddscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int id = getIntent().getIntExtra(IDKey, -1);
        if( id > -1){
            binding.AddScreenName.setText(MainScreen.instance.photos[id].name);
            binding.AddScreenName.setEnabled(false);
            binding.AddScreenDate.setText(MainScreen.instance.photos[id].date);
            binding.AddScreenDate.setEnabled(false);
            binding.AddScreenSelectText.setVisibility(View.INVISIBLE);
            binding.AddScreenImage.setImageBitmap(MainScreen.instance.photos[id].photo);
            binding.AddScreenImageAddView.setOnClickListener(null);
            binding.button2.setVisibility(View.INVISIBLE);
        }

        resultIntent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK){
                    Intent intentFromResult = result.getData();
                    if(intentFromResult != null) {
                        Uri data = intentFromResult.getData();
                        try{
                            if(Build.VERSION.SDK_INT >= 28) {
                                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), data);

                                currentPhoto.photo = ImageDecoder.decodeBitmap(source);
                            }
                            else{
                                currentPhoto.photo = MediaStore.Images.Media.getBitmap(getContentResolver(), data);
                            }
                            changePhoto();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                }
            }
        });

        resultString = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result){
                    Intent galeryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    resultIntent.launch(galeryIntent);
                }
                else{
                    Toast.makeText(AddScreen.this, R.string.AddScreenPermissionNeed, Toast.LENGTH_LONG).show();
                }
            }
        });

        currentPhoto = new Photo();
    }

    public void changePhoto(){
        binding.AddScreenSelectText.setEnabled(false);
        binding.AddScreenImage.bringToFront();
        binding.AddScreenImage.setImageBitmap(this.currentPhoto.photo);
    }

    public void selectPhoto(View view) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                Snackbar.make(view, R.string.AddScreenPermissionNeed,Snackbar.LENGTH_INDEFINITE).setAction(R.string.AddScreenGrantPermission, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resultString.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                }).show();
            }
            else{
                resultString.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            return;
        }
        else{
            Intent galeryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            resultIntent.launch(galeryIntent);
        }


        Toast.makeText(this, "fyguyhj", Toast.LENGTH_LONG).show();
    }



    public void addPhoto(View view) {
        currentPhoto.name = binding.AddScreenName.getText().toString();
        currentPhoto.date = binding.AddScreenDate.getText().toString();

        currentPhoto.InsertPhoto();

        this.finish();
    }
}
