package com.example.journeyjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {
    EditText username;
    Button reset;
    DatabaseHelper DH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        username = findViewById(R.id.reset_pass);
        DH = new DatabaseHelper(this);

        reset = findViewById(R.id.btnreset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                Boolean checkuser = DH.checkusername(user);
                if(checkuser == true)
                {
                    Intent intent = new Intent(getApplicationContext(), ResetActivity.class);
                    intent.putExtra("username", user);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(PasswordActivity.this, "User does not exits!.", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}