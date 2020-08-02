package com.example.trafficticket;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Looper;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.model.CarMetaData;
import com.example.utility.FirebaseConnect;
import com.example.utility.UtilityClass;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.gson.Gson;

import java.io.IOException;

public class ScanImage extends AppCompatActivity {

    //object declarations
    private CameraSource mCameraSource;
    private TextRecognizer mTextRecognizer;
    private SurfaceView mSurfaceView;
    private TextView mTextView;
    private Button mBtnProceed;
    private Intent intent;

    //integer count to check camera permission
    private static final int RC_HANDLE_CAMERA_PERM = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_image);

        //setting up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScanImage.this, TicketList.class);// New activity
                startActivity(intent);
                finish();
            }
        });

        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mTextView = (TextView) findViewById(R.id.textView);
        mBtnProceed = (Button) findViewById(R.id.btn_proceed);

        // set button disabled
        mBtnProceed.setEnabled(false);
        //call method to check for the permissions
        checkPermissions();

        // call on click of proceed button
        mBtnProceed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String scanText = mTextView.getText().toString();
                Log.i("scanText---", scanText);
                // check for empty scan or upload
                if (!"".equals(scanText.trim())) {
                    //call to get the car number from the scanned text
                    scanText = new UtilityClass().findSubstring(scanText.trim());
                    Log.i("scanText Filtered text---", scanText);
                    // check for empty string after car number regex pattern matching
                    if (!"".equals(scanText)) {
                        mCameraSource.release();
                        // to fetch owner details on the basis of scanned car number
                        new FirebaseConnect().getOwnerDetails(scanText, new FirebaseConnect.CarOwnerCallback() {
                            @Override
                            public void getCallback(CarMetaData cmd) {
                                Intent myIntent = new Intent(ScanImage.this, FineRecord.class);
                                myIntent.putExtra("ownerData", new Gson().toJson(cmd));
                                startActivity(myIntent);
                            }
                        });
                    } else {
                        new UtilityClass().showToast(ScanImage.this,"Scanned image is not valid, please upload again.");
//                        Toast.makeText(getApplicationContext(), "Incorrect Car Number String...", Toast.LENGTH_LONG).show();
                    }
                } else {
                    new UtilityClass().showToast(ScanImage.this,"Please scan image first!");
                    //Toast.makeText(getApplicationContext(), "Scan image first...", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void checkPermissions() {
        //check for the permissions
        if (ContextCompat.checkSelfPermission(ScanImage.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // call method to process the activity
            startTextRecognizer();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ScanImage.this, Manifest.permission.CAMERA)) {
                Toast.makeText(getApplicationContext(), "App permission required to scan image", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(ScanImage.this, LogIn.class);
                startActivity(myIntent);
            }
            ActivityCompat.requestPermissions(ScanImage.this, new String[]{Manifest.permission.CAMERA}, RC_HANDLE_CAMERA_PERM);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RC_HANDLE_CAMERA_PERM) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // call method to process activity
                startTextRecognizer();
            } else {
                Toast.makeText(getApplicationContext(), "App permission required to scan image", Toast.LENGTH_LONG).show();
                return;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }
    }

    // method to start the camera to scan the images and fetch texts from them
    private void startTextRecognizer() {
        //create object of TextRecognizer class support by google vision api
        mTextRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if (!mTextRecognizer.isOperational()) {
            Toast.makeText(getApplicationContext(), "Oops ! Not able to start the text recognizer ...", Toast.LENGTH_LONG).show();
        } else {
            // prepare the camera source for scanning
            mCameraSource = new CameraSource.Builder(getApplicationContext(), mTextRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(15.0f)
                    .setAutoFocusEnabled(true)
                    .build();

            // add the callback of the scanning procedure
            mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {

                        mCameraSource.start(mSurfaceView.getHolder());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    mCameraSource.stop();
                }
            });

            // after setting camera as scanner process the text detected
            mTextRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                //receiver method override to handle the text scanned
                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    SparseArray<TextBlock> items = detections.getDetectedItems();
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < items.size(); ++i) {
                        TextBlock item = items.valueAt(i);
                        if (item != null && item.getValue() != null) {
                            stringBuilder.append(item.getValue() + " ");
                        }
                    }

                    final String fullText = stringBuilder.toString();
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            mTextView.setText(fullText);
                            if (!"".equals(fullText)) {
                                mBtnProceed.setEnabled(true);
                            }
                        }
                    });

                }
            });
        }
    }
}
