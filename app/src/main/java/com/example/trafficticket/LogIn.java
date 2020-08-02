package com.example.trafficticket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.utility.FirebaseConnect;
import com.example.utility.UtilityClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {

    //object Declaration
    private EditText userName, userPin, userMail;
    private Button signUpBtn, loginBtn, forgot_password;
    private static final String TAG = "FirebaseEmailPassword";
    private Context context;

    // using this class for form validation
    private AwesomeValidation aweValid;
    // using for firebase authentication
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        context = getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginBtn = (Button) findViewById(R.id.btn_login);
        signUpBtn = (Button) findViewById(R.id.btn_signup);
        userPin = (EditText) findViewById(R.id.txt_userpin);
        userMail = (EditText) findViewById(R.id.txt_email);
        forgot_password = (Button) findViewById(R.id.btn_forgot_password);

        mAuth = FirebaseAuth.getInstance();

        aweValid = new AwesomeValidation(ValidationStyle.BASIC);

        aweValid.addValidation(this, R.id.txt_email, Patterns.EMAIL_ADDRESS, R.string.invalidMail);
        aweValid.addValidation(this, R.id.txt_userpin, RegexTemplate.NOT_EMPTY, R.string.invalidPassword);

        //Forgot Password Event
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgetPwd = new Intent(LogIn.this, ForgotPassword.class);
                startActivity(forgetPwd);
            }
        });

        //LogIn Button Event
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userMail.getText().toString();
                String password = userPin.getText().toString();
                if (!aweValid.validate()) {
                    Toast.makeText(getApplicationContext(), "Please enter email and password! ", Toast.LENGTH_SHORT).show();
                } else {
                    //going to check for firebase authentication
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.e(TAG, "signIn: Success!");
                                        //call to get logged in user details and set them in global class
                                        new FirebaseConnect().getLoggedInUserDet(mAuth.getCurrentUser().getUid(), getApplicationContext(), new FirebaseConnect.loginCallback() {
                                            @Override
                                            public void gCallback() {
                                                Toast.makeText(getApplicationContext(), "Login is successful!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(LogIn.this, TicketList.class);
                                                startActivity(intent);
                                                finish();
                                                Log.i(TAG, "The current UUID is " + mAuth.getCurrentUser().getUid());
                                                Log.i(TAG, "The current email id is " + mAuth.getCurrentUser().getEmail());
                                            }
                                        });
                                    } else {
                                        Log.e(TAG, "signIn: Fail!", task.getException());
                                        new UtilityClass().showToast(LogIn.this,"Invalid username or password.");
                                    }
                                }
                            });
                }
            }
        });

        //SignUp Button Event
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this, SignUp.class);
                startActivity(intent);
            }
        });
    }
}
