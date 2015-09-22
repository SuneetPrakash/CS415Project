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

public class StaffProfile extends AppCompatActivity {




    public TextView txtName2 ;
    public TextView txtAddress ;
    public TextView txtPhone ;
    public TextView txtName ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_profile);



        final String username;

        username= ((Global) this.getApplication()).getuserLogged().toString();


        final JSONParser jParser1 = new JSONParser();


        EditText TF;

        final  String url_all_products = "http://192.168.0.167/CS415/StaffProfile.php";
        final  String TAG_SUCCESS = "success";
        final String TAG_MESSAGE = "message";
        final String TAG_User_LOGGED= "username";
        final String TAG_ADDRESS= "address";
        final String TAG_FNAME= "staff_fname";
        final String TAG_LNAME= "staff_lname";
        final String TAG_PHONE= "phone";

       // final String url_all_products = "http://10.0.2.2/CS427Project/DoctorDisplayAppointment.php";
      //  final String TAG_SUCCESS = "success";
      //  final String TAG_Username = "username";
      //  final String TAG_Display_user = "username_display";
      //  final String TAG_TimeSlot = "timeslot";
       // final String TAG_Doctor = "doctor";
      //  final String TAG_Date = "app_date";

        final String TAG_STAFFDETAILS = "staffdetails";
        JSONArray staffdetails = null;



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
                                        productObj = json.getJSONArray(TAG_STAFFDETAILS);
                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    try {



                                        String display_fname = "";
                                        String display_lname = "";
                                        String display_address = "";
                                        String display_phone = "";



                                        display_fname = productObj.getJSONObject(0).getString(TAG_FNAME);
                                        display_lname = productObj.getJSONObject(0).getString(TAG_LNAME);
                                        display_address = productObj.getJSONObject(0).getString(TAG_ADDRESS);
                                        display_phone = productObj.getJSONObject(0).getString(TAG_PHONE);

                                        txtName2 = (TextView) findViewById(R.id.txtName2);
                                        txtName2.setText(display_fname + " " + display_lname);

                                        txtAddress = (TextView) findViewById(R.id.txtAddress);
                                        txtAddress.setText(display_address);

                                        txtPhone = (TextView) findViewById(R.id.txtPhone);
                                        txtPhone.setText(display_phone);

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




        findViewById(R.id.btnProfileBack).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(getApplicationContext(), StaffMain.class);
                        startActivity(i);
                    }
                });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_staff_profile, menu);
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
