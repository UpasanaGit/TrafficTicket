package com.example.utility;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.trafficticket.R;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilityClass {

    /*method to find car number from the scanned text using the regex patterns - input scanned or uploaded image text
     * carNumber - returns either car number string or empty string
     */
    public String findSubstring(String numText) {
        String carNumber = "";
        try {

            /* ([0-9]([A-Z]){3}([0-9]){3}) - normal plate
             *   ([A-Z][0-9]([A-Z]){3}) - radio station upon request
             *   (([A-Z]){2}([0-9]){5}) - apportioned
             *   (([0-9]){4}[A-Z]) - disabled person from 2006
             *   (([0-9]){3}([A-Z]){2}) - disabled person from 2011
             *   (([A-Z]){2}([0-9]){3}) - disabled person from 2019
             *   (([0-9]){5}[A-Z]) - for truck - old
             *   (([0-9]){5}[A-Z][0-9]) - for truck
             *   ([0-9][A-Z]([0-9]){5})
             */
            if (!"".equals(numText)) {
                String[] regexArr = {"([0-9]([A-Z]){3}([0-9]){3})",
                        "([A-Z][0-9]([A-Z]){3})",
                        "(([A-Z]){2}([0-9]){5})",
                        "(([0-9]){4}[A-Z])",
                        "(([0-9]){3}([A-Z]){2})",
                        "(([A-Z]){2}([0-9]){3})",
                        "(([0-9]){5}[A-Z][0-9])",
                        "([0-9][A-Z]([0-9]){5})"};
                for (int i = 0; i < regexArr.length; i++) {
                    Pattern pattern = Pattern.compile(regexArr[i]);
                    Matcher matcher = pattern.matcher(numText);
                    if (matcher.find()) {
                        carNumber = matcher.group(1);
                        if (!"".equals(carNumber)) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return carNumber;
    }

    /* method to get unique ticket id to send to user with help of two input parameters
     * userMail and location - input parameters
     * ticketNum - return a unique ticket id
     * */
    public String getTicketId(String userMail, String location) {
        String ticketNum = "";
        try {
            SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
            int testVal = Integer.valueOf(prng.nextInt());
            if (testVal < 0) {
                testVal = testVal * -1;
            }
            //generate a random number
            ticketNum = String.valueOf(testVal);
            ticketNum = location.substring(0, 3).toUpperCase() + userMail.substring(0, 4).toUpperCase() + ticketNum;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return ticketNum;
    }

    // method to return due date for the ticket fined for the owner
    public String getDueDate() {
        String dueDate = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            //Getting current date
            Calendar cal = Calendar.getInstance();
            //Displaying current date in the desired format
            System.out.println("Current Date: " + sdf.format(cal.getTime()));

            //Number of Days to add
            cal.add(Calendar.DAY_OF_MONTH, 7);
            //Date after adding the days to the current date
            dueDate = sdf.format(cal.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dueDate;
    }

    public void showToast(Context context, String alertMsg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
        builder.setMessage(alertMsg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        TextView textView = (TextView) alert.findViewById(android.R.id.message);
        textView.setTextSize(18);
    }

}
