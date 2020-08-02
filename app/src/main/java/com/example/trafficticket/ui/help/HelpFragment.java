package com.example.trafficticket.ui.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trafficticket.R;

public class HelpFragment extends Fragment {

    //object declarations
    private TextView question1, question2,question3;
    private  TextView answer1, answer2, answer3;


    //Setting strings for the question and answer
    private String faq1 = "1. What is the procedure to generate the ticket?\n";
    private String value1 = "There are two options to generate the ticket\n" +
            "* Scan - tap on the fab icon given on the right bottom corner, tap on scan on the dialog option and then scan the license plate for the respective vehicle.\n" +
            "* Upload - tap on the fab icon given on the right bottom corner, tap on upload on the dialog option and then upload the license plate of the respective vehicle saved in the gallery of the phone.\n" +
            "\n";

    private String faq2 = "2. Want to change your profile settings?\n";
    private String value2 = "Tap on the drawable icon given on the top left corner and then tap on Update Profile";

    private String faq3 = "3. Want to see your list of generated ticket list?\n";
    private String value3 =  "Once you login to the app, first screen shows up and it will display the ticket list which you generated.";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_help, container, false);

        question1 = (TextView) root.findViewById(R.id.question1);
        question1.setText(faq1);

        question2 = (TextView) root.findViewById(R.id.question2);
        question2.setText(faq2);

        question3 = (TextView) root.findViewById(R.id.question3);
        question3.setText(faq3);

        answer1 = (TextView) root.findViewById(R.id.answer1);
        answer1.setText(value1);

        answer2 = (TextView) root.findViewById(R.id.answer2);
        answer2.setText(value2);

        answer3 = (TextView) root.findViewById(R.id.answer3);
        answer3.setText(value3);

        return root;
    }
}