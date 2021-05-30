package com.example.a2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a2.database.CredentialsDatabase;
import com.example.a2.entity.Credentials;
import com.example.a2.networkconnection.NetworkConnection;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class SignupActivity extends AppCompatActivity {
    CredentialsDatabase db = null;
    NetworkConnection networkConnection = new NetworkConnection();
    private Calendar calendar;
    private Spinner stateSpinner;
    private String personDob;

    static int countid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        //CountTask countTask = new CountTask();
        //countTask.execute();


        /*final ArrayAdapter<String> spinnerAdapter = new
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        sState.setAdapter(spinnerAdapter);

         */

        CalendarView cvDob = findViewById(R.id.cvDob);

        cvDob.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //https://developer.android.com/reference/java/time/ZoneId
                ZoneId zone = ZoneId.of("AET");
                ZoneOffset zoneOffset = zone.getRules().getOffset(LocalDateTime.now());
                //https://stackoverflow.com/questions/45675241/offsetdatetime-to-date-android
                //https://www.baeldung.com/java-zone-offset
                //https://www.geeksforgeeks.org/android-creating-a-calendar-view-app/
                OffsetDateTime dob = OffsetDateTime.of(year, month, dayOfMonth, 00, 00, 00, 00, zoneOffset);
                personDob = dob.toString().substring(0,16)+":00"+dob.toString().substring(16,22);
            }
        });

        //Sign up
        Button btn = findViewById(R.id.btSubmit);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText etEmail = findViewById(R.id.etUseraccounts);
                EditText etPassword = findViewById(R.id.etPassword);
                EditText etFirstname = findViewById(R.id.etFirstname);
                EditText etSurname = findViewById(R.id.etSurname);
                EditText etAddress = findViewById(R.id.etAddress);
                EditText etPostcode = findViewById(R.id.etPostcode);


                    //detail[0] firstname
                    StringBuffer p = new StringBuffer();
                    if (!(etFirstname.getText().toString().isEmpty())) {
                        p.append(etFirstname.getText().toString());
                        p.append("\n");
                    }
                    //detail[1] lastname
                    if (!(etSurname.getText().toString().isEmpty())) {
                        p.append(etSurname.getText().toString());
                        p.append("\n");
                    }
                    //detail[2] gender
                    //https://stackoverflow.com/questions/8307052/how-to-get-the-id-of-selected-radio-button-in-android
                    //https://mkyong.com/android/android-radio-buttons-example/
                    RadioGroup radioGenderGroup = findViewById(R.id.radioSex);
                    int gender = 999;
                    if (radioGenderGroup.getCheckedRadioButtonId() == findViewById(R.id.rbFemale).getId())
                        gender = 2;
                    else if (radioGenderGroup.getCheckedRadioButtonId() == findViewById(R.id.rbMale).getId())
                        gender = 1;
                    else if (radioGenderGroup.getCheckedRadioButtonId() == findViewById(R.id.rbOther).getId())
                        gender = 0;

                    Log.d(TAG, "" + gender);
                    p.append(gender);
                    p.append("\n");

                    //detail[3] dob
                    p.append(personDob);
                    p.append("\n");

                    //detail[4] snumber detail[5]  sname
                    if (!(etAddress.getText().toString().isEmpty())) {
                        String[] address = etAddress.getText().toString().split(" ");
                        if (address.length == 3)
                            p.append(address[0]);
                        p.append("\n");
                        String addressroad = address[1] + " " + address[2];
                        p.append(addressroad);
                        p.append("\n");
                    }
                    //detail[6] postcode
                    if (!(etPostcode.getText().toString().isEmpty())) {
                        p.append(etPostcode.getText().toString());
                        p.append("\n");
                    }
                    //detail[7] state
                    stateSpinner = findViewById(R.id.spState);
                    String selectedState = stateSpinner.getSelectedItem().toString();
                    p.append(selectedState);
                    p.append("\n");

                boolean enteremail = false;
                boolean enterpassword = false;
                boolean informationcomplete = false;

//credentials part
                //detail[8] username
                if (!(etEmail.getText().toString().isEmpty())) {
                    p.append(etEmail.getText().toString());
                    p.append("\n");
                    enteremail = true;
                } else
                    sendToast("You have not enter an email for user account.");
                //detail[9] hash
                if (!(etPassword.getText().toString().isEmpty())) {
                    String password = etPassword.getText().toString();
                    Hashpassword hash = new Hashpassword();
                    String hashpassword = hash.md5(password);
                    p.append(hashpassword);
                    p.append("\n");
                    enterpassword = true;
                } else
                    sendToast("You have not enter a password for your account.");
                //detail[10] signupdate
                if (enteremail == true && enterpassword == true) {
                    //https://www.boraji.com/java-8-offsetdatetime-example
                    OffsetDateTime dateTime = OffsetDateTime.now();
                    String today = dateTime.toString();

                    p.append(today);
                    p.append("\n");

                    informationcomplete = true;
                }

                    String[] personDetail = p.toString().split("\n");
                    if (personDetail.length == 11)
                    {
                        AddPersonTask addPersonTask = new AddPersonTask();
                        addPersonTask.execute(personDetail);
                    }


                    /*Intent intent = new Intent(SignupActivity.this,
                            MainActivity.class);
                    startActivity(intent);

                     */

                }
            });


        }




    private class AddPersonTask extends AsyncTask<String, Void, String> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... params) {

            Log.d(TAG, "doInBackground: " + params[0] + params[1] + params[2] + params[3] + params[4] + params[5] + params[6] + params[7]);

            return networkConnection.addPerson(params);

        }

        @Override
        protected void onPostExecute(final String result) {
            //https://stackoverflow.com/questions/3875184/cant-create-handler-inside-thread-that-has-not-called-looper-prepare
            new Thread() {
                public void run() {
                    SignupActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            sendToast(result);
                        }
                    });
                }
            }.start();
        }

    }//private class

    public String validateEntry(String stringtovalidate) {
        return "";

    }

    protected void sendToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
/*
    private class AddCredentialsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            /*Credentials credentials = new Credentials(Integer.parseInt(params[0]), params[1], params[2],Date.valueOf(params[3]));
            Credentials credentials = new Credentials(params[0], params[1], Date.valueOf(params[2]));
            long id = db.credentialsDao().insert(credentials);
            return (id + " " + params[0] + " " + params[1] + " " + params[2]);


            String message = "New account created!" + params[0] + params[1];
            try {
                return networkConnection.addCredentials(params);
            } catch (Exception e) {
                sendToast("ohh");
            }
            return "quite not ok";
        }


        @Override
        protected void onPostExecute(final String result) {
            //https://stackoverflow.com/questions/3875184/cant-create-handler-inside-thread-that-has-not-called-looper-prepare
            new Thread() {
                public void run() {
                    SignupActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            sendToast(result);
                        }
                    });
                }
            }.start();
        }
    }//private class
*/