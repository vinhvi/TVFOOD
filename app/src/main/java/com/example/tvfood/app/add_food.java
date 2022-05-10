package com.example.tvfood.app;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tvfood.R;
import com.example.tvfood.entyti.Foods;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class add_food extends AppCompatActivity {
    private ImageView imageFood;
    private Button btn_imageFood;
    private Button btn_addFood;
    private MultiAutoCompleteTextView mct_review;
    private EditText edt_name;
    private EditText editText_price;
    private ProgressBar progressBar;
    private Foods foods;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_food);

        imageFood = findViewById(R.id.img_add_food);
        btn_imageFood = findViewById(R.id.btn_add_image_food);
        btn_addFood = findViewById(R.id.btn_add_food);
        mct_review = findViewById(R.id.mct_review_food);
        edt_name = findViewById(R.id.edt_name_food);
        editText_price = findViewById(R.id.edt_price_food);
        progressBar = findViewById(R.id.progressBar_addFood);
        progressBar.setVisibility(View.INVISIBLE);

        btn_imageFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        btn_addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });


    }


    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent intent = result.getData();
                Uri uri = intent.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                    Bitmap resizeBitmap = Bitmap.createScaledBitmap(bitmap, 680, 500, false);
                    imageFood.setImageBitmap(resizeBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    });

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startForResult.launch(intent);
    }

    private void uploadImage() {
        Bitmap capture = Bitmap.createBitmap(imageFood.getWidth(), imageFood.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas captureCanvas = new Canvas(capture);
        imageFood.draw(captureCanvas);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        capture.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] data = outputStream.toByteArray();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("images/" + UUID.randomUUID() + ".png");
        UploadTask uploadTask = storageReference.putBytes(data);
        progressBar.setVisibility(View.VISIBLE);
        btn_addFood.setEnabled(false);
        btn_imageFood.setEnabled(false);
        uploadTask.addOnCompleteListener(add_food.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(add_food.this, "đã tải ảnh lên storage!!", Toast.LENGTH_SHORT).show();
            }
        });
        Task<Uri> getDowloadUri = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return storageReference.getDownloadUrl();
            }
        });
        getDowloadUri.addOnCompleteListener(add_food.this, new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri dowloadUri = task.getResult();
                    String uri_image = dowloadUri.toString();
                    addFood(uri_image);
                    progressBar.setVisibility(View.INVISIBLE);
                    btn_addFood.setEnabled(true);
                    btn_imageFood.setEnabled(true);
                }
            }
        });


    }

    private void addFood(String image) {
        String name = edt_name.getText().toString();
        double price = Double.parseDouble(editText_price.getText().toString());
        String review = mct_review.getText().toString();
        foods = new Foods(name, review, price, image);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("foods");
        databaseReference.child(String.valueOf(UUID.randomUUID())).setValue(foods).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    AlertDialog.Builder showmessgae = new AlertDialog.Builder(add_food.this);
                    showmessgae.setMessage("Thêm món mới thành công !").
                            setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    edt_name.setText("");
                                    mct_review.setText("");
                                    editText_price.setText("");
                                }
                            })
                            .create();
                    showmessgae.show();
                } else {
                    Toast.makeText(add_food.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
