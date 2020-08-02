package com.example.trafficticket.ui.UpdateProfile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.model.GlobalClass;
import com.example.trafficticket.LogIn;
import com.example.trafficticket.R;
import com.example.utility.FirebaseConnect;
import com.example.utility.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateProfileFragment extends Fragment {

    //object declarations
    private Button mUpdate;
    private EditText userMail, userPhone, userDob, userNameTxt;
    private AwesomeValidation aweValid;
    private boolean mailChange = false;
    private Intent intent;
    private static final String TAG = "Update Profile Changes";
    final Calendar myCalendar = Calendar.getInstance();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final GlobalClass gc = (GlobalClass) getActivity().getApplicationContext();

        View root = inflater.inflate(R.layout.fragment_update_profile, container, false);
        userMail = root.findViewById(R.id.txt_umail);
        userPhone = root.findViewById(R.id.txt_uphone);
        userNameTxt = root.findViewById(R.id.txt_uname);
        userDob = root.findViewById(R.id.txt_udob);
        mUpdate = root.findViewById(R.id.btn_create);
        aweValid = new AwesomeValidation(ValidationStyle.BASIC);

        //setting value in EditText Parameters of Update Profile
        userMail.setText(gc.getUser_MAIL());
        userPhone.setText(gc.getUser_PHONE());
        userDob.setText(gc.getUser_DOB());
        userNameTxt.setText(gc.getUsername());

        // adding validations to the fields
        aweValid.addValidation(getActivity(), R.id.txt_uname, RegexTemplate.NOT_EMPTY, R.string.invalidName);
        aweValid.addValidation(getActivity(), R.id.txt_umail, Patterns.EMAIL_ADDRESS, R.string.invalidMail);
        aweValid.addValidation(getActivity(), R.id.txt_uphone, "^[2-9]\\d{2}\\d{3}\\d{4}$", R.string.incorrectPhone);

        userMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mailChange = true;
            }
        });

        mUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (aweValid.validate()) {
                    new FirebaseConnect().updateUserProfile(gc.getChildId(), userNameTxt.getText().toString(), userDob.getText().toString(), userPhone.getText().toString(), userMail.getText().toString());
                    new UtilityClass().showToast(getActivity(),"Details updated successfully!");


                    gc.setUser_MAIL(userMail.getText().toString());
                    gc.setUser_DOB(userDob.getText().toString());
                    gc.setUser_PHONE(userPhone.getText().toString());
                    gc.setUsername(userNameTxt.getText().toString());

                    if (mailChange) {
                        new FirebaseConnect().updateEmail(userMail.getText().toString());
                        new UtilityClass().showToast(getActivity(), "Your email has been updated, you have to login again");
                        intent = new Intent(getActivity().getApplicationContext(), LogIn.class);// New activity
                        startActivity(intent);
                        getActivity().finish();
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Validation Failed!", Toast.LENGTH_LONG).show();
                }
            }
        });

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
                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(),R.style.DialogTheme, dateListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                //following line to restrict future date selection
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        return root;
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        userDob.setText(sdf.format(myCalendar.getTime()));

    }
}