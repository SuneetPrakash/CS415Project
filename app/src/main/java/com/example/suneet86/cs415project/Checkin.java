package com.example.suneet86.cs415project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Checkin extends AppCompatActivity {


    public EditText editTextCopyNum_Checkin ;
    public EditText editTextBookISBN_Checkin ;



    JSONParser jParser1 = new JSONParser();

    EditText TF;
    private static String url_all_products = "http://192.168.0.167/CS415/checkin.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_BookISBN = "BookISBN";
    private static final String TAG_CopyNum = "CopyNum";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);



        findViewById(R.id.btnBackCheckIn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(getApplicationContext(), StaffMain.class);
                        startActivity(i);
                    }
                });


        findViewById(R.id.btnCheckIn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        final String BookISBN;
                        final String CopyNum;


                        editTextBookISBN_Checkin = (EditText) findViewById(R.id.editTextBookISBN_Checkin);
                        editTextCopyNum_Checkin = (EditText) findViewById(R.id.editTextCopyNum_Checkin);

                        BookISBN = editTextBookISBN_Checkin.getText().toString();
                        CopyNum = editTextCopyNum_Checkin.getText().toString();





                        Thread thread = new Thread(new Runnable(){
                            @Override
                            public void run() {
                                try {

                                    List<NameValuePair> params2 = new ArrayList<NameValuePair>();

                                    params2.add(new BasicNameValuePair(TAG_BookISBN, BookISBN));
                                    params2.add(new BasicNameValuePair(TAG_CopyNum, CopyNum));

                                    JSONObject json = jParser1.makeHttpRequest(url_all_products, "POST", params2);
                                    Log.d("Create Response", json.toString());


                                    try {
                                        int success = json.getInt(TAG_SUCCESS);
                                        final String message1 = json.getString(TAG_MESSAGE);

                                        if (success == 1) {
                                            runOnUiThread(new Runnable()
                                            {
                                                @Override
                                                public void run()
                                                {

                                                    Toast.makeText(getBaseContext(),
                                                            "Book Checked In Successfully",
                                                            Toast.LENGTH_SHORT).show();

                                                    Intent i = new Intent(getApplicationContext(), StaffMain.class);
                                                    startActivity(i);

                                                }
                                            });


                                        }


                                        if (success==0) {
                                            runOnUiThread(new Runnable()
                                            {
                                                @Override
                                                public void run()
                                                {

                                                    Toast.makeText(getBaseContext(),
                                                            "Error While Checking In Book!",
                                                            Toast.LENGTH_SHORT).show();

                                                    Intent i = new Intent(getApplicationContext(), StaffMain.class);
                                                    startActivity(i);

                                                }
                                            });
                                        }


                                    } catch (final JSONException e) {

                                        runOnUiThread(new Runnable()
                                        {
                                            @Override
                                            public void run()
                                            {

                                            }
                                        });
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        });
                        thread.start();

                    }
                });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checkin, menu);
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
