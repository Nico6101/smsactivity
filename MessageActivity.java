package com.rcpl.activitysms;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MessageActivity extends AppCompatActivity
{
    TextView tv1;
    EditText et1;
    Button b1;
    String sentNumber,message;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        tv1 = (TextView) findViewById(R.id.textView2);
        et1 = (EditText) findViewById(R.id.editText2);
        b1 = (Button) findViewById(R.id.button2);
        tv1.setText(null);
        Intent box = getIntent();
        Bundle b = box.getExtras();
        sentNumber = b.getString("sentNumber");
        tv1.append("Number : "+sentNumber);
    }

    public void onsend(View v)
    {
        Intent intent2 = new Intent(MessageActivity.this,LoginActivity.class);
        intent2.putExtra("sentNumber",sentNumber);
        startActivity(intent2);

        message = et1.getText().toString();
        if(sentNumber.length()>0 && message.length()>0)
            sendSMS(sentNumber,message);
        else
            Toast.makeText(this, "Please enter correctly.", Toast.LENGTH_SHORT).show();
    }

    private void sendSMS(String number, String message)
    {
        PendingIntent pi = PendingIntent.getActivity(this,0,new Intent(this,MessageActivity.class),0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(number,null,message,pi,null);
    }
}
