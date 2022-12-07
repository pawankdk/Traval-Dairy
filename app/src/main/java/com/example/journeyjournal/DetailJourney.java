package com.example.journeyjournal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.journeyjournal.helper.Jinfo;

public class DetailJourney extends AppCompatActivity {

    String id;
    TextView title, address, date, details;
    Button update, delete, share;
    DatabaseHelperj databaseHelperj;
    ImageView imageView;

    Jinfo jinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_journey);


        id = getIntent().getStringExtra("id");

        title = findViewById(R.id.about);
        address = findViewById(R.id.location);
        date = findViewById(R.id.date_time);
        details = findViewById(R.id.details);
        update = findViewById(R.id.updatebtn);
        delete = findViewById(R.id.deletebtn);
        share = findViewById(R.id.sharebtn);
        imageView = findViewById(R.id.cimage);


        databaseHelperj = new DatabaseHelperj(this);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailJourney.this, AddJourney.class);
                intent.putExtra("id", Integer.parseInt(id));
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable drawable = (BitmapDrawable)imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", "content");

                Uri uri = Uri.parse(bitmapPath);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Title: " + title.getText().toString()+"\n" + "Date: " + date.getText().toString()+"\n" + "Location: " + address.getText().toString()+"\n" + "My Thoughts: " + details.getText().toString());
                startActivity(Intent.createChooser(intent, "Share Via"));
            }
        });

    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Journal");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseHelperj.deleteDetail(id);
                Intent intent = new Intent(DetailJourney.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        jinfo = databaseHelperj.getDetailInfo(id);

        title.setText(jinfo.title);
        address.setText(jinfo.address);
        date.setText(jinfo.date);
        details.setText(jinfo.details);
        if (jinfo.image != null)
            imageView.setImageBitmap(DatabaseHelperj.getBitmap(jinfo.image));
    }
}