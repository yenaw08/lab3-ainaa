package com.example.lab3_ainaa;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateBiodataActivity extends AppCompatActivity {

    protected Cursor cursor;
    DataHelper dbHelper;
    Button buttonUpdate, buttonBack;
    EditText textName, textDob, textGender, textAddress;
    TextView textId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_biodata);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DataHelper(this);

// Initialize EditText components
        textId = (TextView) findViewById(R.id.editTextId);
        textName = (EditText) findViewById(R.id.editTextName);
        textDob = (EditText) findViewById(R.id.editTextDob);
        textGender = (EditText) findViewById(R.id.editTextGender);
        textAddress = (EditText) findViewById(R.id.editTextAddress);

// Retrieve data from database based on selected name
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM biodata WHERE name = '" +
                getIntent().getStringExtra("name") + "'", null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            textId.setText(cursor.getString(0));
            textName.setText(cursor.getString(1));
            textDob.setText(cursor.getString(2));
            textGender.setText(cursor.getString(3));
            textAddress.setText(cursor.getString(4));
        }

// Initialize Buttons
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonBack = (Button) findViewById(R.id.buttonBack);

// Update Button Logic
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("UPDATE biodata SET " +
                        "name='" + textName.getText().toString() + "', " +
                        "dob='" + textDob.getText().toString() + "', " +
                        "gender='" + textGender.getText().toString() + "', " +
                        "address='" + textAddress.getText().toString() + "' " +
                        "WHERE no='" + textId.getText().toString() + "'");
                Toast.makeText(getApplicationContext(), "Data Successfully Updated", Toast.LENGTH_SHORT).show();
                MainActivity.ma.RefreshList();
                finish();
            }
        });

// Back Button Logic
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }
}