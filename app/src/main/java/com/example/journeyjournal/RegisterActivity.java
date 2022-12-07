package com.example.journeyjournal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.journeyjournal.helper.Userinfo;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password, email, address, phone;
    RadioGroup gender;
    Button register, login;

    DatabaseHelper databaseHelper;

    int id;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_actvity);
        id = getIntent().getIntExtra("id", 0);

        sharedPreferences = getSharedPreferences("Userinfo", Context.MODE_PRIVATE);
        databaseHelper = new DatabaseHelper(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        gender = findViewById(R.id.gender);
        register = findViewById(R.id.registerbtnr);
//        login = findViewById(R.id.loginbtnr);
//        register.setBackgroundColor(Color.RED);


        if (id != 0) {
            Userinfo info = databaseHelper.getUserinfo(String.valueOf(id));
            username.setText(info.username);
            address.setText(info.address);
            phone.setText(info.phone);
            password.setText(info.password);
            if (info.gender.equals("Male")) {
                ((RadioButton) findViewById(R.id.male)).setChecked(true);
            } else if (info.gender.equals("Female")){
                ((RadioButton) findViewById(R.id.female)).setChecked(true);
            }else {
            ((RadioButton) findViewById(R.id.otherb)).setChecked(true);
        }
            email.setText(info.email);
            register.setText("Update");
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (emptyFieldValidation(username) && emptyFieldValidation(password) && isvalidEmail(email)) {

                    String usernameValue = username.getText().toString();
                    String passwordValue = password.getText().toString();
                    String emailValue = email.getText().toString();
                    String addressValue = address.getText().toString();
                    String phoneValue = phone.getText().toString();

                    RadioButton checkedBtn = findViewById(gender.getCheckedRadioButtonId());
                    String genderValue = checkedBtn.getText().toString();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", usernameValue);
                    editor.putString("password", passwordValue);
                    editor.putString("email", emailValue);
                    editor.putString("address", addressValue);
                    editor.putString("phone", phoneValue);
                    editor.putString("gender", genderValue);
                    editor.commit();

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("username", usernameValue);
                    contentValues.put("password", passwordValue);
                    contentValues.put("email", emailValue);
                    contentValues.put("address", addressValue);
                    contentValues.put("phone", phoneValue);
                    contentValues.put("gender", genderValue);

                    if (id == 0) {
                        databaseHelper.insertUser(contentValues);

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);

                        Toast.makeText(RegisterActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                    } else {
                        databaseHelper.updateUser(id + "", contentValues);
                        Toast.makeText(RegisterActivity.this, "User updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

            }
        });

//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    public boolean emptyFieldValidation(EditText view) {
        if (view.getText().toString().length() > 6) {
            return true;
        } else {
            view.setError("Minimum length must be 6");
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isvalidEmail(EditText view) {
        String emailValue = view.getText().toString();
        if (Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            return true;
        } else {
            view.setError("Invalid email address");
            return false;
        }
    }


}