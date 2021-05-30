package com.example.a2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2.Parcelable.Person;
import com.example.a2.database.CredentialsDatabase;
import com.example.a2.networkconnection.NetworkConnection;
import com.example.a2.viewmodel.CredentialsViewModel;


public class MainActivity extends AppCompatActivity {

    //start
    CredentialsDatabase db = null;
    EditText editText = null;
    TextView textView_insert = null;
    TextView textView_read = null;
    //Room-Task4
    CredentialsViewModel credentialsViewModel;
    //OKhttp
    NetworkConnection networkConnection = null;
    String signinCredentialsId;
    Button SignInbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //start
        //commented when modifing it to Task 4 style: db = CredentialsDatabase.getInstance(this);
        //textView_read = findViewById(R.id.tvTest);
        //room-Task 4
        /*
        credentialsViewModel = new ViewModelProvider(this).get(CredentialsViewModel.class);
        credentialsViewModel.initalizeVars(getApplication());
        credentialsViewModel.getAllCredentials().observe(this, new Observer<List<Credentials>>() {
            @Override
            public void onChanged(@Nullable final List<Credentials> credentials) {
                String allcredentials = "";
                for (Credentials temp : credentials) {
                    String credentialsstr = (temp.getId() + " " +
                            temp.getUsername() + " " + temp.getPasswordhash() + temp.getSignupdate());
                    allcredentials = allcredentials + System.getProperty("line.separator") + credentialsstr;
                }
                textView_read.setText("All data: " + allcredentials);
            }
        });

         */


        //OKhttp + Sign in + findbyusernameandpasswordhash
        final EditText editTextusername = findViewById(R.id.etUserAccount);
        final EditText editTextPassword = findViewById(R.id.etPassword);
        networkConnection = new NetworkConnection();
        SignInbtn = findViewById(R.id.btSignIn);
        SignInbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextusername.getText().toString();
                String password = editTextPassword.getText().toString();
                Hashpassword hashpassword = new Hashpassword();
                String MD5_hash = hashpassword.md5(password);
                GetByUsernameAndPassword getByUsernameAndPassword = new GetByUsernameAndPassword();
                GetCredentialsIdByUsername getCredentialsIdByUsername = new GetCredentialsIdByUsername();
                if (!username.isEmpty()) {
                    getCredentialsIdByUsername.execute(username);
                    getByUsernameAndPassword.execute(username, MD5_hash);

                }
            }
        });


        //intent- go to sign up page
        Button btn = findViewById(R.id.btSignUp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        //just for easy testing
        Button btntest = findViewById(R.id.btTest);
        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextusername.setText("rin@monash.edu");
                editTextPassword.setText("222");
                SignInbtn.callOnClick();
            }
        });

        /*
        Button btntest = findViewById(R.id.btTest);
        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToast("You can sign up for another account!");
            }
        });
         */

    }

    private class GetByUsernameAndPassword extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String passwordhash = params[1];
            return networkConnection.findByUsernameAndPassword(username, passwordhash);
        }

        @Override
        protected void onPostExecute(String credentials) {
            if (!(credentials.length() == 2)) {
                GetPersonFnameByCredentialsId getPersonFnameByCredentialsId = new GetPersonFnameByCredentialsId();
                getPersonFnameByCredentialsId.execute(signinCredentialsId);

            } else {
                sendToast("Incorrect username and/or password. Please try again.");
            }
            //TextView resultTextView = findViewById(R.id.tvTest);
            //resultTextView.setText(credentials);
        }
    }

    //to get the id and set the field
    private class GetCredentialsIdByUsername extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String username = params[0];

            return networkConnection.getCredentialsIdByUsername(username);
        }

        @Override
        protected void onPostExecute(String id) {
            signinCredentialsId = id;
        }
    }

    private class GetPersonFnameByCredentialsId extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String credentialsid = params[0];

            return networkConnection.getPersonFnameByCredentialsId(credentialsid);
        }

        @Override
        protected void onPostExecute(String fname) {
            Intent intent = new Intent(MainActivity.this, HomescreenActivity.class);

            //java object parcelable
            Bundle bundle = new Bundle();
            int credentialsid = Integer.parseInt(signinCredentialsId);
            Person personsignin = new Person(credentialsid, fname);
            bundle.putParcelable("person1",personsignin);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    protected void sendToast(String msg) {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

}
