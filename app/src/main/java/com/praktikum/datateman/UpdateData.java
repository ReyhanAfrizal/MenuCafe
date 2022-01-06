package com.praktikum.datateman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateData extends AppCompatActivity {
    private long temanID;
    EditText nama, harga, alamat, photo;
    Button btnUpdate;

    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        databaseHelper = new DatabaseHelper(this);

        try {
            temanID = getIntent().getLongExtra("TEMAN_ID", 1);
        }catch (Exception e){
            e.printStackTrace();
        }

        nama = findViewById(R.id.edt_upd_nama);
        harga = findViewById(R.id.edt_upd_price);
        alamat = findViewById(R.id.edt_upd_address);
        photo = findViewById(R.id.edt_upd_photo);
        btnUpdate = findViewById(R.id.btn_update_data);
        //untuk mengisi Edit Text
        getTeman(temanID);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
    }

    public void getTeman(long id){
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        String query = "SELECT * FROM " + DatabaseSetting.DatabaseEntry.TABLE_NAME + " WHERE _ID = " + id;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            nama.setText(cursor.getString(cursor.getColumnIndex(DatabaseSetting.DatabaseEntry.COLUMN_NAME)));
            harga.setText(cursor.getString(cursor.getColumnIndex(DatabaseSetting.DatabaseEntry.COLUMN_PRICE)));
            alamat.setText(cursor.getString(cursor.getColumnIndex(DatabaseSetting.DatabaseEntry.COLUMN_ADDRESS)));
            photo.setText(cursor.getString(cursor.getColumnIndex(DatabaseSetting.DatabaseEntry.COLUMN_PHOTO)));
        }
    }

    public void updateData(){

        String name = nama.getText().toString().trim();
        String price = harga.getText().toString().trim();
        String address = alamat.getText().toString().trim();
        String img = photo.getText().toString().trim();

        if(name.isEmpty() || price.isEmpty() || address.isEmpty() ||
                img.isEmpty())
        {
            Toast.makeText(this, "Data tidak boleh kosong!", Toast.LENGTH_LONG).show();
            return;
        }

        sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSetting.DatabaseEntry.COLUMN_NAME, name);
        contentValues.put(DatabaseSetting.DatabaseEntry.COLUMN_PRICE, price);
        contentValues.put(DatabaseSetting.DatabaseEntry.COLUMN_ADDRESS, address);
        contentValues.put(DatabaseSetting.DatabaseEntry.COLUMN_PHOTO, img);

        int result = sqLiteDatabase.update(DatabaseSetting.DatabaseEntry.TABLE_NAME, contentValues,
                "_ID = ?", new String[] {String.valueOf(temanID)});

        if(result > 0)
            Toast.makeText(this, "Data Berhasil Diubah", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Data Gagal Diubah", Toast.LENGTH_LONG).show();

        Intent updateForm = new Intent(this, HomeActivity.class);
        startActivity(updateForm);
    }
}
