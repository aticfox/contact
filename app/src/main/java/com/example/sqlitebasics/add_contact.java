package com.example.sqlitebasics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class add_contact extends AppCompatActivity {
    Button btnadd;
    Button btncancle ;
    EditText addid ;
    EditText addpass ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        btnadd = (Button)findViewById(R.id.button);//ใช้botton
        btncancle = (Button)findViewById(R.id.button2);//ใช้
        addid = (EditText)findViewById(R.id.editText);//ใช้
        addpass = (EditText)findViewById(R.id.editText2);//ใช้
        btnadd.setOnClickListener(new View.OnClickListener() {//กดปุ่มแอดเกิดไรขึ้น
            @Override
            public void onClick(View v) {
                String msgName =String.valueOf(addid.getText().toString());
                String msgTel =String.valueOf(addpass.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("name",msgName);
                intent.putExtra("phone-number",msgTel);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
