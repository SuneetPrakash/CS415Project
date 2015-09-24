package com.example.suneet86.cs415project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentProfile extends AppCompatActivity {


    public TextView txtStudentPName ;
    public TextView txtStudentPAddress ;
    public TextView txtStudentPProgram;
    public TextView txtStudentPMajor1 ;
    public TextView txtStudentPMajor2;
    public TextView txtStudentPID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);



        final String username;

        username= ((Global) this.getApplication()).getuserLogged().toString();


        final JSONParser jParser1 = new JSONParser();


        EditText TF;

        final  String url_all_products = "http://192.168.0.167/CS415/StudentProfile.php";
        final  String TAG_SUCCESS = "success";
        final String TAG_MESSAGE = "message";
        final String TAG_User_LOGGED= "username";
        final String TAG_ADDRESS= "address";
        final String TAG_FNAME= "student_fname";
        final String TAG_LNAME= "student_lname";
        final String TAG_PROGRAM= "program";
        final String TAG_MAJOR1= "major1";
        final String TAG_MAJOR2= "major2";


        final String TAG_STUDENTDETAILS = "studentdetails";
        JSONArray studentdetails = null;


        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {

                    List<NameValuePair> params2 = new ArrayList<NameValuePair>();

                    params2.add(new BasicNameValuePair(TAG_User_LOGGED, username));

                    final JSONObject json = jParser1.makeHttpRequest(url_all_products, "GET", params2);
                    Log.d("Create Response", json.toString());


                    try {
                        int success = json.getInt(TAG_SUCCESS);

                        if (success == 1) {
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run() {

                                    JSONArray productObj = null;

                                    try {
                                        productObj = json.getJSONArray(TAG_STUDENTDETAILS);
                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    try {



                                        String display_fname = "";
                                        String display_lname = "";
                                        String display_address = "";
                                        String display_phone = "";
                                        String display_major1 = "";
                                        String display_major2 = "";
                                        String display_program = "";



                                        display_fname = productObj.getJSONObject(0).getString(TAG_FNAME);
                                        display_lname = productObj.getJSONObject(0).getString(TAG_LNAME);
                                        display_address = productObj.getJSONObject(0).getString(TAG_ADDRESS);
                                        display_major1 = productObj.getJSONObject(0).getString(TAG_MAJOR1);
                                        display_major2 = productObj.getJSONObject(0).getString(TAG_MAJOR2);
                                        display_program = productObj.getJSONObject(0).getString(TAG_PROGRAM);


                                        txtStudentPName = (TextView) findViewById(R.id.txtStudentPName);
                                        txtStudentPName.setText(display_fname + " " + display_lname);

                                        txtStudentPAddress = (TextView) findViewById(R.id.txtStudentPAddress);
                                        txtStudentPAddress.setText(display_address);

                                        txtStudentPProgram = (TextView) findViewById(R.id.txtStudentPProgram);
                                        txtStudentPProgram.setText(display_program);

                                        txtStudentPMajor1 = (TextView) findViewById(R.id.txtStudentPMajor1);
                                        txtStudentPMajor1.setText(display_major1);

                                        txtStudentPMajor2 = (TextView) findViewById(R.id.txtStudentPMajor2);
                                        txtStudentPMajor2.setText(display_major2);

                                        txtStudentPID = (TextView) findViewById(R.id.txtStudentPID);
                                        txtStudentPID.setText(username);

                                        Log.d("The Values ",  productObj.getJSONObject(0).getString(TAG_FNAME));

                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }

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
                                            "Sorry No results found!!!!!.......",
                                            Toast.LENGTH_SHORT).show();


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



        findViewById(R.id.btnStudentPBack).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent i = new Intent(getApplicationContext(), StudentMain.class);
                        startActivity(i);
                    }
                });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_profile, menu);
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
