package com.example.sqlitebasics;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private MyHelper mHelper;
    private SQLiteDatabase mDatabase;
    private SimpleCursorAdapter mAdapter;
    String msgid ;
    private  Cursor readAllData(){
        String[] columms ={
                MyHelper.COL_ID,mHelper.COL_NAME,MyHelper.COL_PHONE_NUMBER
        };
        Cursor cursor = mDatabase.query(MyHelper.TABLE_NAME,columms,null,null,null,null,null);
        return cursor;
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            String msgName = data.getStringExtra("name");
            String msgTel = data.getStringExtra("phone-number");
            mDatabase.insert(MyHelper.TABLE_NAME, null, addContact(msgName,msgTel));
            Cursor cursor = readAllData();
            mAdapter.changeCursor(cursor);

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new MyHelper(this);
        mDatabase = mHelper.getWritableDatabase();

        Cursor cursor = readAllData();

        mAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[]{MyHelper.COL_NAME, MyHelper.COL_PHONE_NUMBER},
                new int[]{android.R.id.text1, android.R.id.text2},
                0
        );

        ListView listView = (ListView) findViewById(R.id.contacts_listview);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                msgid = String.valueOf(id);
                new AlertDialog.Builder(MainActivity.this).setTitle("Delete or not.").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabase.execSQL("DELETE FROM contacts WHERE _id = " + msgid);
                        Cursor cursor = readAllData();
                        mAdapter.changeCursor(cursor);
                    }
                }).setNegativeButton("canale", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();


            }
        });
        // กำหนดการทำงานของปุ่ม Add Contact
        Button insertButton = (Button) findViewById(R.id.insert_button);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, add_contact.class);
                startActivityForResult(intent, 0);
            }
        });

        // กำหนดการทำงานปุ่ม Delete Contact


    }

    private void addContact() {
        ContentValues cv = new ContentValues();
        cv.put(MyHelper.COL_NAME, "bbbbb");
        cv.put(MyHelper.COL_PHONE_NUMBER, "333-333-3333");
        mDatabase.insert(MyHelper.TABLE_NAME, null, cv);
//testttt
        ///test12
        Cursor cursor = readAllData();
        mAdapter.changeCursor(cursor);
    }
    private ContentValues addContact(String name,String tel) {
        ContentValues cv = new ContentValues();
        cv.put(MyHelper.COL_NAME, name);
        cv.put(MyHelper.COL_PHONE_NUMBER, tel);
        return cv;
       // mDatabase.insert(MyHelper.TABLE_NAME, null, cv);

      //  Cursor cursor = readAllData();
        //mAdapter.changeCursor(cursor);
    }

    private void deleteContact() {
/*
        mDatabase.delete(
                MyHelper.TABLE_NAME,
                MyHelper.COL_PHONE_NUMBER + " LIKE ?",
                new String[]{ "333%" }
        );
*/

        mDatabase.execSQL("DELETE FROM contacts WHERE phone_number LIKE '333%'");

        Cursor cursor = readAllData();
        mAdapter.changeCursor(cursor);
    }


}
