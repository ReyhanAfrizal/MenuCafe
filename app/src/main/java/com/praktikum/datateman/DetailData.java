package com.praktikum.datateman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailData extends AppCompatActivity {
    private long temanID;
    TextView nama, harga, alamat;
    ImageView photo;
    Button btnUpdate;

    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data);

        databaseHelper = new DatabaseHelper(this);

        try {
            temanID = getIntent().getLongExtra("TEMAN_ID", 1);
        }catch (Exception e){
            e.printStackTrace();
        }

        nama = findViewById(R.id.tv_name);
        harga = findViewById(R.id.tv_harga);
        alamat = findViewById(R.id.tv_alamat);
        photo = findViewById(R.id.img_detail);
        btnUpdate = findViewById(R.id.btn_update);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateData = new Intent(DetailData.this, UpdateData.class);
                updateData.putExtra("TEMAN_ID", temanID);
                startActivity(updateData);
            }
        });

        //untuk mengambil data user berdasarkan ID
        getTeman(temanID);
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
            String linkPhoto = cursor.getString(cursor.getColumnIndex(DatabaseSetting.DatabaseEntry.COLUMN_PHOTO));

            Glide.with(this)
                    .load(linkPhoto)
                    .apply(new RequestOptions().override(500, 500))
                    .into(photo);

        }
    }
}