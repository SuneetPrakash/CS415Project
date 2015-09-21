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

public class MainActivity extends AppCompatActivity {


    Button button;

    public EditText editusername ;
    public EditText editpassword ;
    public Spinner role;

    JSONParser jParser1 = new com.example.suneet86.cs415project.JSONParser();

    EditText TF;
   // private static String url_all_products = "http://10.0.2.2:80/CS415/loginAndroid.php";
    private static String url_all_products = "http://192.168.0.167/CS415/loginAndroid.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_Username = "Username";
    private static final String TAG_Password = "Password";
    private static final String TAG_Role = "Role";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        findViewById(R.id.btnLogin).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                      userlogin();

                    }
                });


    }
    //making test changes

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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




    public void userlogin() {

        final String Username;
        final String Password;
        final String Role;


        editusername = (EditText) findViewById(R.id.editTextUsername);
        editpassword = (EditText) findViewById(R.id.editTextPassword);
        role = (Spinner) findViewById(R.id.spinner);


        Username = editusername.getText().toString();

       // ((MyApplication) this.getApplication()).setUsername(Username);
        Password = editpassword.getText().toString();
        Role= role.getSelectedItem().toString();

        ((Global) this.getApplication()).setUserLogged(Username);

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {

                    List<NameValuePair> params2 = new ArrayList<NameValuePair>();

                    params2.add(new BasicNameValuePair(TAG_Username, Username));
                    params2.add(new BasicNameValuePair(TAG_Password, Password));
                    params2.add(new BasicNameValuePair(TAG_Role, Role));


                    // params2.add(new BasicNameValuePair(TAG_bmi, bmiInterpretation));
                    JSONObject json = jParser1.makeHttpRequest(url_all_products, "POST", params2);
                    Log.d("Create Response", json.toString());


                    try {
                        int success = json.getInt(TAG_SUCCESS);

                        if (success == 1) {
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {

                                    Toast.makeText(getBaseContext(),
                                            "Login Successful" ,
                                            Toast.LENGTH_SHORT).show();

                                        Intent i = new Intent(getApplicationContext(), StaffMain.class);
                                        startActivity(i);

                                }
                            });


                        }


                        if (success == 2) {
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {


                                    Toast.makeText(getBaseContext(),
                                            "Login Successful" ,
                                            Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent(getApplicationContext(), StudentMain.class);
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
                                            "Login UnSuccessful",
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

                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        thread.start();




       // Intent intent = new Intent(getApplicationContext(), MainActivity.class);
      //  startActivity(intent);



    }

}
