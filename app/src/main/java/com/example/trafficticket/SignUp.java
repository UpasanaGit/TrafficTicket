package com.example.trafficticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.model.UserDetails;
import com.example.utility.FirebaseConnect;
import com.example.utility.UtilityClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUp extends AppCompatActivity {

    //object declarations
    private Button mRegister;
    private EditText userName, userPin, userMail, userPhone, userDob;
    private AwesomeValidation aweValid;
    private Context context;
    private FirebaseAuth mAuth;

    private static final String TAG = "FirebaseEmailPassword";
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        context = getApplicationContext();
        mAuth = FirebaseAuth.getInstance();

        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        userName = findViewById(R.id.txt_uname);
        userPin = findViewById(R.id.txt_upin);
        userMail = findViewById(R.id.txt_umail);
        userPhone = findViewById(R.id.txt_uphone);
        userDob = findViewById(R.id.txt_udob);
        mRegister = findViewById(R.id.btn_create);

        aweValid = new AwesomeValidation(ValidationStyle.BASIC);

        // adding validations to the fields
        aweValid.addValidation(this, R.id.txt_uname, RegexTemplate.NOT_EMPTY, R.string.invalidName);
        aweValid.addValidation(this, R.id.txt_umail, Patterns.EMAIL_ADDRESS, R.string.invalidMail);
        aweValid.addValidation(this, R.id.txt_upin, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", R.string.wrongFormat);
        aweValid.addValidation(this, R.id.txt_uphone, "^[2-9]\\d{2}\\d{3}\\d{4}$", R.string.incorrectPhone);
        aweValid.addValidation(this, R.id.txt_re_upin, R.id.txt_upin, R.string.passMisMatch);

        mRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!aweValid.validate()) {
                    Toast.makeText(context, "Validation Failed!", Toast.LENGTH_LONG).show();
                } else {
                    //call to create account in firebase authentication
                    createAccount(userMail.getText().toString(), userPin.getText().toString());
                    new UtilityClass().showToast(SignUp.this,"User Created successfully!");
                }
            }
        });

        //date picker dialog for dob field
        final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        userDob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUp.this,R.style.DialogTheme, dateListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                //following line to restrict future date selection
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
    }

    // method to create account in firebase authentication accepts 2 input parameters - email and password
    public void createAccount(String email, String password) {
        Log.e(TAG, "createAccount:" + email);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.i("check ", String.valueOf(task.isSuccessful()));
                        if (task.isSuccessful()) {
                            Log.e(TAG, "createAccount: Success!");

                            // get signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            //set up details to object class to store in realtime database
                            UserDetails ud = new UserDetails();
                            ud.setUsername(userName.getText().toString());
                            ud.setUser_MAIL(userMail.getText().toString());
                            ud.setUser_DOB(userDob.getText().toString());
                            ud.setUser_PWD(userPin.getText().toString());
                            ud.setUser_PHONE(userPhone.getText().toString());
                            ud.setUserid(user.getUid());
                            //method call to insert info in firebase database
                            new FirebaseConnect().insertUserDetails(ud);

                            Intent intent = new Intent(SignUp.this, LogIn.class);// New activity
                            startActivity(intent);
                            finish();
                        } else {
                            Log.e(TAG, "createAccount: Fail!", task.getException());
                            Toast.makeText(getApplicationContext(), "Some error occurred, please try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //method to set selected date in dob field
    private void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        userDob.setText(sdf.format(myCalendar.getTime()));
    }
}

