package com.example.journeyjournal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.journeyjournal.helper.Jinfo;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.dhaval2404.imagepicker.ImagePickerActivity;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddJourney extends AppCompatActivity {

    EditText title, details, address;
    ImageView image, imagePicker;
    Button add, cancel;
    TextView textView;

    LinearLayout form;

    int id;

    DatabaseHelperj databaseHelperj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journey);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id = getIntent().getIntExtra("id", 0);

        databaseHelperj = new DatabaseHelperj(this);

        form = findViewById(R.id.loginlayout);
        title = findViewById(R.id.about);
        details = findViewById(R.id.details);
        image = findViewById(R.id.gimage);
        address = findViewById(R.id.address);
        add = findViewById(R.id.addbtn);
        cancel = findViewById(R.id.cancelbtn);
        textView = findViewById(R.id.title);
        imagePicker = findViewById(R.id.imagePicker);
//      implementation of glide image picker library
        imagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(AddJourney.this)
                        .start();
            }
        });

        //        Date and time
        Date currentTime = Calendar.getInstance().getTime();
        String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 101);

            }
        });

        if (id != 0) {
            Jinfo info = databaseHelperj.getDetailInfo(String.valueOf(id));
            title.setText(info.title);
            address.setText(info.address);
            details.setText(info.details);
            formattedDate.toString();
            add.setText("Update Entries");
            textView.setText("Update Entries");
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emptyFieldValidation(title) && emptyFieldValidation(details)) {

                    String titleValue = title.getText().toString();
                    String detailValue = details.getText().toString();
                    String dateValue = formattedDate.toString();
                    String addressValue = address.getText().toString();


                    ContentValues contentValues = new ContentValues();
                    contentValues.put("title", titleValue);
                    contentValues.put("details", detailValue);
                    contentValues.put("address", addressValue);
                    contentValues.put("date", dateValue);
                    contentValues.put("image", DatabaseHelperj.getBlob(bitmap));


                    if (id == 0) {
                        databaseHelperj.insertDetail(contentValues);
                        Toast.makeText(AddJourney.this, "Journal Added successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddJourney.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        databaseHelperj.updateDetail(id + "", contentValues);
                        Toast.makeText(AddJourney.this, "Journal updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddJourney.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddJourney.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public boolean emptyFieldValidation(EditText view) {
        if (view.getText().toString().length() >= 2) {
            return true;
        } else {
            view.setError("Minimum length must be 2");
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

    Bitmap bitmap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(bitmap);


        }
        Uri uri = data.getData();
        image.setImageURI(uri);
    }

}