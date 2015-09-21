package com.example.suneet86.cs415project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Checkout extends AppCompatActivity {

    Button button;

    public EditText editBookISBN ;
    public EditText editCopyNum ;
    public EditText editUsername ;


    JSONParser jParser1 = new JSONParser();

    EditText TF;
    // private static String url_all_products = "http://10.0.2.2:80/CS415/loginAndroid.php";
    private static String url_all_products = "http://192.168.0.167/CS415/checkout.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_Username = "Username";
    private static final String TAG_BookISBN = "BookISBN";
    private static final String TAG_CopyNum = "CopyNum";
    private static final String TAG_User_LOGGED= "UserLogged";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

      final String userLogged = ((Global) this.getApplication()).getuserLogged();





        findViewById(R.id.btnBack).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent i = new Intent(getApplicationContext(), StaffMain.class);
                        startActivity(i);

                    }
                });


        findViewById(R.id.btnCheckOutBook).setOnClickListener(
                new View.OnClickListener() {


                    @Override
                    public void onClick(View view) {



                        final String Username;
                         final String BookISBN;
                         final  String  CopyNum;
                      //  final String userLogged;


                        editUsername = (EditText) findViewById(R.id.editUsername);
                        editBookISBN = (EditText) findViewById(R.id.editBookISBN);
                        editCopyNum = (EditText) findViewById(R.id.editCopyNum);

                        Username = editUsername.getText().toString();
                        BookISBN = editBookISBN.getText().toString();
                        CopyNum = editCopyNum.getText().toString();



                        Thread thread = new Thread(new Runnable(){
                            @Override
                            public void run() {
                                try {

                                    List<NameValuePair> params2 = new ArrayList<NameValuePair>();

                                    params2.add(new BasicNameValuePair(TAG_Username, Username));
                                    params2.add(new BasicNameValuePair(TAG_BookISBN, BookISBN));
                                    params2.add(new BasicNameValuePair(TAG_CopyNum, CopyNum));
                                    params2.add(new BasicNameValuePair(TAG_User_LOGGED, userLogged));


                                    // params2.add(new BasicNameValuePair(TAG_bmi, bmiInterpretation));
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
                                                            "Book Checked Out Successfully",
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
                                                            "Error While Checking Out Book!",
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
                                                //  resultText7.setText("Login UnSuccessful" + e);
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
        getMenuInflater().inflate(R.menu.menu_checkout, menu);
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
