package com.example.utility;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

// Async class to send email to the car owner with details of fined ticket
public class SendMail extends AsyncTask<Void, Void, String> {

    private String userMail, userName, place, carNumber, ticketNumber, ruleStr;
    private int fineAmt;

    // constructor to accept the values
    public SendMail(String email, int amount, String location, String ticket, String name, String carNum, String fineStr) {
        this.userMail = email;
        this.fineAmt = amount;
        this.place = location;
        this.userName = name;
        this.ticketNumber = ticket;
        this.carNumber = carNum;
        this.ruleStr = fineStr;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            String[] ruleArr = ruleStr.split(";");
            String ruleList = "";
            for (int i = 0; i < ruleArr.length; i++) {
                ruleList = ruleList + (i + 1) + ". " + ruleArr[i] + ".\n";
            }

            // get due date of fine
            String dueDate = new UtilityClass().getDueDate();
            Session session;
            String subject = "Traffic Rule Violation Ticket : " + ticketNumber;
            String message = "Hello " + userName + ",\n"
                    + "You have been charged for $" + fineAmt + " traffic for rule violation at " + place + ".\n"
                    + "Your ticket id is " + ticketNumber + " due by " + dueDate + " on vehicle number - " + carNumber + ".\n"
                    + "Take a look on the following violations you have made -\n"
                    + ruleList
                    + "Please fill the fine before the mentioned due date. We hope you will not repeat such things in future.\n\n"
                    + "Safety is our priority. Drive Safe!\n"
                    + "Regards,\nTraffic Police Department";
            final String USEREMAIL = "upasanagarg9@gmail.com";
            final String USERPIN = "Trial@2020";

            Log.i("message", message);

            //set properties to send email using Gmail SMTP sockets
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            //get session of username and password
            session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(USEREMAIL, USERPIN);
                }
            });
            try {
                //object initiation of mine message to set subject, mail content and email id of recipient
                MimeMessage mm = new MimeMessage(session);
                mm.setFrom(new InternetAddress(USEREMAIL));
                mm.addRecipient(Message.RecipientType.TO, new InternetAddress(userMail));
                mm.setSubject(subject);
                mm.setText(message);
                // send message
                Transport.send(mm);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
}

