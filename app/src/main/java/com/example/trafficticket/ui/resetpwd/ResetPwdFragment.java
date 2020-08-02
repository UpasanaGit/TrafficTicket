package com.example.trafficticket.ui.resetpwd;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.model.GlobalClass;
import com.example.trafficticket.ForgotPassword;
import com.example.trafficticket.LogIn;
import com.example.trafficticket.R;
import com.example.utility.UtilityClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPwdFragment extends Fragment {

    //object declarations
    private EditText edtEmail,oldPin;
    private Button btnResetPassword;
    private FirebaseAuth mAuth;
    private AwesomeValidation aweValid;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_resetpwd, container, false);

        edtEmail = (EditText) root.findViewById(R.id.edt_reset_email);
        btnResetPassword = (Button) root.findViewById(R.id.btn_reset_password);
        oldPin = (EditText) root.findViewById(R.id.old_password);

        //getting instance of global class
        GlobalClass gc = (GlobalClass) getActivity().getApplicationContext();
        edtEmail.setText(gc.getUser_MAIL());

        mAuth = FirebaseAuth.getInstance();
        aweValid = new AwesomeValidation(ValidationStyle.BASIC);
        aweValid.addValidation(getActivity(), R.id.edt_reset_email, Patterns.EMAIL_ADDRESS, R.string.invalidMail);
        aweValid.addValidation(getActivity(), R.id.old_password, RegexTemplate.NOT_EMPTY, R.string.wrongFormat);

        //call to action for reset password with the help of firebase authentication
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String pwd = oldPin.getText().toString().trim();
                if (!"".equalsIgnoreCase(email) && !"".equalsIgnoreCase(pwd)) {
                    reauthenticate();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter all details!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    public void reauthenticate() {
        try{
            // [START reauthenticate]
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            // Get auth credentials from the user for re-authentication. The example below shows
            // email and password credentials but there are multiple possible providers,
            // such as GoogleAuthProvider or FacebookAuthProvider.
            AuthCredential credential = EmailAuthProvider
                    .getCredential(edtEmail.getText().toString(),oldPin.getText().toString() );

            // Prompt the user to re-provide their sign-in credentials
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mAuth.sendPasswordResetEmail(edtEmail.getText().toString())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    new UtilityClass().showToast(getActivity(), "Check your email to reset your password!");

                                                    Intent intent = new Intent(getActivity().getApplicationContext(), LogIn.class);
                                                    startActivity(intent);
                                                    getActivity().finish();
                                                } else {
                                                    Toast.makeText(getActivity().getApplicationContext(), "Some error occurred, please try again!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }else{
                                new UtilityClass().showToast(getActivity(),"Please enter correct password!");
//                                Toast.makeText(getActivity().getApplicationContext(), "Entered password is incorrect", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
            // [END reauthenticate]
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}