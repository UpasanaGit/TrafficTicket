package com.example.trafficticket;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.CarMetaData;
import com.example.utility.FirebaseConnect;
import com.example.utility.UtilityClass;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.gson.Gson;

import java.io.IOException;

public class UploadImage extends AppCompatActivity {

    //object declarations
    private Button mBtnUpload, mBtnProcess;
    private TextView textView;
    private static final String TAG = LogIn.class.getSimpleName();
    private int PICK_IMAGE_REQUEST = 1;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        //toolbar setup
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadImage.this, TicketList.class);// New activity
                startActivity(intent);
                finish();
            }
        });

        mBtnUpload = findViewById(R.id.btn_image);
        mBtnProcess = findViewById(R.id.btn_proceed1);
        textView = findViewById(R.id.textView1);

        // set button disabled
        mBtnProcess.setEnabled(false);

        //call to check permissions to access external storage on click of upload button
        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkPermissions();
            }
        });

        //process with the text fetched from the uploaded image
        mBtnProcess.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String scanText = textView.getText().toString();
                // check for empty scan or upload
                if (!"".equals(scanText.trim())) {
                    //call to get car number with help of regex from the text of uploaded image
                    scanText = new UtilityClass().findSubstring(scanText.trim());

                    // check for empty string after car number regex pattern matching
                    if (!"".equals(scanText)) {
                        //call to get car owner details
                        new FirebaseConnect().getOwnerDetails(scanText, new FirebaseConnect.CarOwnerCallback() {
                            @Override
                            public void getCallback(CarMetaData cmd) {
                                Intent myIntent = new Intent(UploadImage.this, FineRecord.class);
                                myIntent.putExtra("ownerData", new Gson().toJson(cmd));
                                startActivity(myIntent);
                            }
                        });
                    } else {
                        new UtilityClass().showToast(UploadImage.this,"Uploaded image is not valid, please upload again.");
                      //  Toast.makeText(getApplicationContext(), "Incorrect car number string...", Toast.LENGTH_LONG).show();
                    }
                } else {
                    new UtilityClass().showToast(UploadImage.this,"Please upload image first!");
                   // Toast.makeText(getApplicationContext(), "Upload Image first...", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    //method to check permission for external storage
    public void checkPermissions() {
        if (ContextCompat.checkSelfPermission(UploadImage.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // call method to process the activity
            uploadAndProcessImage();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(UploadImage.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(getApplicationContext(), "App permission required to upload image", Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(UploadImage.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_IMAGE_REQUEST);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // call method to process activity
                uploadAndProcessImage();
            } else {
                Toast.makeText(getApplicationContext(), "App permission required to upload image", Toast.LENGTH_LONG).show();
                return;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }
    }

    //method to initiate the external storage and filter on images
    public void uploadAndProcessImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
                if (data != null && data.getData() != null) {
                    //start processing on the selected image
                    Uri uri = data.getData();
                    try {
                        //set the image to the image view before final proceeding
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        ImageView imageView = findViewById(R.id.imageView2);
                        imageView.setImageBitmap(bitmap);

                        mBtnProcess.setEnabled(true);

                        // prepare object of TextRecognizer class
                        TextRecognizer textRecognizer = new TextRecognizer.Builder((getApplicationContext())).build();
                        if (!textRecognizer.isOperational()) {
                            Log.w("ERROR", "Detector are not yet available.");
                        } else {
                            //prepare a frame to start processing on Bitmap
                            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                            SparseArray<TextBlock> items = textRecognizer.detect(frame);
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int i = 0; i < items.size(); i++) {
                                TextBlock item = items.valueAt(i);
                                stringBuilder.append(item.getValue());
                                stringBuilder.append("\n");
                            }
                            textView.setText(stringBuilder.toString());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Image selection is cancelled", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Image selection is cancelled", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
