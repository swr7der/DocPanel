package com.example.swarnim.signup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText fullNameText;
    EditText emailIDText;
    EditText passwordText;
    Spinner projectSpinner;
    Button createProfile;
    TextView alreadyAccount;
    ArrayAdapter<String> arrayAdapterSpinner;

    String projectSpinnerValue[] = {"Doctor"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fullNameText = (EditText) findViewById(R.id.fullNameText);
        emailIDText = (EditText) findViewById(R.id.emailIDText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        projectSpinner = (Spinner) findViewById(R.id.doctorSpinner);
        createProfile = (Button) findViewById(R.id.createProfile);
        alreadyAccount = (TextView) findViewById(R.id.alreadyAccount);

        arrayAdapterSpinner = new ArrayAdapter<String>(this,R.layout.spinner_text_styles,projectSpinnerValue);
        projectSpinner.setAdapter(arrayAdapterSpinner);

    }
}
