package com.example.utility;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.model.CarMetaData;
import com.example.model.FineRecordObj;
import com.example.model.GlobalClass;
import com.example.model.TrafficRule;
import com.example.model.UserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FirebaseConnect {

    /* method to return rule list to show on FineRecord activity
    * use callback to perform task synchronously - RuleListCallback
    * */
    public void getRuleListData(final RuleListCallback callback) {
        try {
            final ArrayList<TrafficRule> dataList = new ArrayList<>();
            // get reference of the collection from firebase database
            DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("TT_RULE_MASTER");
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();
                    // setting up data in the datalist to return using callback
                    for (Map.Entry<String, Object> mapObj : td.entrySet()) {
                        TrafficRule tr = new Gson().fromJson(new Gson().toJson(mapObj.getValue()), TrafficRule.class);
                        dataList.add(tr);
                    }
                    callback.onCallback(dataList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // callback to support the Rule list fetch method
    public interface RuleListCallback {
        void onCallback(ArrayList<TrafficRule> ruleList);
    }

    /* method to fetch owner details of the car from firebase collection using query reference
    * using carNum as parameter with a callback interface
    * */
    public void getOwnerDetails(String carNum, final CarOwnerCallback cmdCallback) {
        try {
            DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("TT_CARS_METADATA");
            // query reference used as where clause to fetch detail
            Query queryRef = reff.orderByChild("car_NUMBER").equalTo(carNum);

            queryRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                    // set details to pojo class
                    Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                    CarMetaData cmd = new Gson().fromJson(new Gson().toJson(value), CarMetaData.class);
                    cmdCallback.getCallback(cmd);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // callback to support fetch method of car owner detail on the basis olf car number
    public interface CarOwnerCallback {
        void getCallback(CarMetaData cmd);
    }

    /* method to insert fine record in database - accepts 6 parameters
     * carNum - carNumber
     * fineAmt - fine amount to be paid
     * ticketId - unique ticket id
     * fineBy - logged in user name
     * fineById - logged in user id
     * fineStr - ";" separated rule list violated by driver
     */

    public void insertFineTicket(String carNum, int fineAmt, String ticketId, String fineBy, String fineById,String fineStr) {
        try {
            DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("TT_FINE_RECORDS");

            FineRecordObj frObj = new FineRecordObj();

            frObj.setCar_NUMBER(carNum);
            frObj.setDue_DATE(new UtilityClass().getDueDate());
            frObj.setFine_AMT(fineAmt);
            frObj.setFine_DATE(String.valueOf(new Date()));
            frObj.setFine_TICKETID(ticketId);
            frObj.setStatus("Unpaid");
            frObj.setLicense_STATUS(true);
            frObj.setFined_BY(fineBy);
            frObj.setFined_BYID(fineById);
            frObj.setRuledesc_LIST(fineStr);

            reff.push().setValue(frObj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /* method to fetch ticket list on basis of logged in user id
    * accepts userId and use a callback
    * */
    public void getTicketListData(String userId, final TicketListCallback callback) {
        try {
            final ArrayList<FineRecordObj> dataList = new ArrayList<>();

            DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("TT_FINE_RECORDS");
            //query to put where clause on userid
            Query queryRef = reff.orderByChild("fined_BYID").equalTo(userId);

            queryRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();
                    if(td != null) {
                        // setting up data in the dataList to return using callback
                        for (Map.Entry<String, Object> mapObj : td.entrySet()) {
                            FineRecordObj frObj = new Gson().fromJson(new Gson().toJson(mapObj.getValue()), FineRecordObj.class);
                            dataList.add(frObj);
                        }
                    }
                    callback.setCallback(dataList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

//            queryRef.addChildEventListener(new ChildEventListener() {
//                @Override
//                public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
//                    Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();
//                    Log.i("Receive Data", new Gson().toJson(td));
//                    // set details to the object class
//                    FineRecordObj frObj = new Gson().fromJson(new Gson().toJson(td), FineRecordObj.class);
//                    callback.setCallback(frObj);
//                }
//
//                @Override
//                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                }
//
//                @Override
//                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//                }
//
//                @Override
//                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                }
//            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // interface to support method to fetch ticket list on basis of userid
    public interface TicketListCallback {
        void setCallback(ArrayList<FineRecordObj> frObjList);
    }

    /* method to update user profile in firebase database - accepts 5 parameters
    * childId - default uniqueId of the user record in database
    * userName - logged in userName
    * dob - date of birth of user
    * phone - mobile number of user
    * email - mail address of user
    * */
    public void updateUserProfile(String childId, String userName, String dob, String phone, String email) {
        try {

            DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("TT_USER_DETAILS");
            Map<String, Object> dMap = new HashMap<String, Object>();
            dMap.put("user_MAIL", email);
            dMap.put("user_PHONE", phone);
            dMap.put("username", userName);
            dMap.put("user_DOB", dob);

            userReference.child(childId).updateChildren(dMap);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /* method to insert new user details in realtime database of firebase
    * accepts one parameter as input - object of UserDetails class
    * */
    public void insertUserDetails(UserDetails ud) {
        try {
            DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("TT_USER_DETAILS");
            userReference.push().setValue(ud);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /* method to fetch details of the logged in user from realtime database
    * accepts 3 parameters including a callback interface
    * userId - logged in userId
    * appContext - application context
    * */
    public void getLoggedInUserDet(String userId, final Context appContext, final loginCallback lgCall) {
        try {
            DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("TT_USER_DETAILS");
            Query queryRef = reff.orderByChild("userid").equalTo(userId);

            queryRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                    System.out.println(dataSnapshot.getValue());
                    Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();

                    // set values in the global class to use further
                    GlobalClass gc = (GlobalClass) appContext;
                    gc.setChildId(dataSnapshot.getKey());
                    gc.setUser_DOB(String.valueOf(value.get("user_DOB")));
                    gc.setUser_MAIL(String.valueOf(value.get("user_MAIL")));
                    gc.setUser_PHONE(String.valueOf(value.get("user_PHONE")));
                    gc.setUsername(String.valueOf(value.get("username")));
                    gc.setUserid(String.valueOf(value.get("userid")));

                    lgCall.gCallback();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // interface to support callback for method to fetch logged in user details
    public interface loginCallback {
        void gCallback();
    }

    // method to update email in firebase authentication - accepts single parameter as emailid
    public void updateEmail(String newEmail) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updateEmail(newEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("FireBase Connect", "User email address updated.");
                        }
                    }
                });
    }
}
