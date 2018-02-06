package com.rcpl.activitysms;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity
{
    EditText et1;
    Button b1;
    static String str1,str2,str3,str4;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et1 = (EditText) findViewById(R.id.editText1);
        b1 = (Button) findViewById(R.id.button1);

    }

    public void buttonClick(View v)
    {
        str1 = et1.getText().toString();

        Intent box = new Intent(LoginActivity.this,ContactActivity.class);
        box.putExtra("U",str1);
        startActivity(box);

        database d = new database(getBaseContext());
        d.insertData(getApplicationContext());
    }

    public static class database extends SQLiteOpenHelper implements BaseColumns
    {
        public static final int DATABASE_VERSION = 2;
        public static final String DATABASE_NAME = "database.db";

        public static final String TABLE_NAME = "SMS_DETAILS";
        public static final String CN_LOGINNO = "loginno";
        public static final String CN_SENTNO = "sentno";
        public static final String CN_MESSAGE = "message";

        public database(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY, " + CN_LOGINNO
                    + " TEXT, " + CN_SENTNO + " TEXT, " + CN_MESSAGE + " TEXT )";
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        public void insertData(Context context)
        {


            try
            {
                Intent intent3 = getIntent();
                Bundle bb = intent3.getExtras();
                str2 = bb.getString("sentNumber");
            }
            catch (NullPointerException e)
            {
                Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
            }
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CN_LOGINNO,str1);
            values.put(CN_SENTNO,str2);

            db.insert(TABLE_NAME,null,values);
            Toast.makeText(context, "Number saved. Please Proceed.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {

        }
    }
}
