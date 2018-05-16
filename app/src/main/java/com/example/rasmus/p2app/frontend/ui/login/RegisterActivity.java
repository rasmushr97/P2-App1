package com.example.rasmus.p2app.frontend.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rasmus.p2app.R;
import com.example.rasmus.p2app.cloud.DBHandler;
import com.example.rasmus.p2app.frontend.AppBackButtonActivity;
import com.example.rasmus.p2app.frontend.ui.activities.LoadingScreenActivity;

public class RegisterActivity extends AppBackButtonActivity {

    boolean isMale = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");


        final EditText etAge = (EditText) findViewById(R.id.etAge);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etHeight = (EditText) findViewById(R.id.etHeight);
        final EditText etWeight = (EditText) findViewById(R.id.etWeight);
        final EditText etGoalWeight = (EditText) findViewById(R.id.etGoalWeight);
        final Spinner spinner = (Spinner) findViewById(R.id.chooseSexSpinner);


        String[] paths = {"Female", "Male"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        isMale = false;
                        break;

                    case 1:
                        isMale = true;
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(RegisterActivity.this, "Choose a gender", Toast.LENGTH_SHORT).show();
            }
        });


        Button registerButton = findViewById(R.id.bRegister);
        registerButton.setOnClickListener(event -> {
            System.out.println(etAge.getText().toString().equals(""));
            if (etAge.getText().toString().equals("") || etName.getText().toString().equals("") || etPassword.getText().toString().equals("") ||
                    etUsername.getText().toString().equals("") || etHeight.getText().toString().equals("") ||
                    etWeight.getText().toString().equals("") || etGoalWeight.getText().toString().equals("")) {
                Toast.makeText(this, "You have to fill all fields", Toast.LENGTH_LONG).show();
            } else {
                // TODO: save the other information and upload weigths to the database

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (DBHandler.registerUser(username, password)) {
                    Toast.makeText(this, "Registration was a Success", Toast.LENGTH_SHORT).show();

                    Intent intent;
                    if (DBHandler.login(username, password)) {
                        intent = new Intent(RegisterActivity.this, LoadingScreenActivity.class);
                    } else {
                        intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    }
                    startActivity(intent);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                } else {
                    Toast.makeText(this, "Registration failed", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

}
