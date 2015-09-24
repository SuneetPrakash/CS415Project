package com.example.suneet86.cs415project;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Bookdetails extends AppCompatActivity {


    LinearLayout mainlayout;
    String username;
    String usertype;


    private static String URL= "http://192.168.0.167/CS415/reservation.php";
    private static final String TAG_SUCCESS= "success";

    JSONArray data= null;

    JSONParser jParser1 = new com.example.suneet86.cs415project.JSONParser();
    ArrayList<String> booklist = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetails);


        mainlayout=(LinearLayout)findViewById(R.id.mainLL);

        Bundle b= getIntent().getExtras();
        booklist= b.getStringArrayList("books");
        username=b.getString("username");
        usertype=b.getString("usertype");

        int size= booklist.size();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);



        for(int i=0;i<booklist.size();i++)
        {
            //extract bok details
            String bookdetails [] = booklist.get(i).split(":");

            //create LinearLayout
            LinearLayout section = new LinearLayout(this);
            section.setOrientation(LinearLayout.VERTICAL);




            // Create Button
            final Button btn = new Button(this);
            // Give button an ID
            final String ID= bookdetails[5]+":"+bookdetails[7];
            Log.d("Buton ID",ID);
            btn.setId(i + 1);
            btn.setText(bookdetails[0]);
            btn.setGravity(Gravity.LEFT);

            // set the layoutParams on the button
            btn.setLayoutParams(params);

            final int index = i;
            // Set click listener for button
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

//                    Log.i("TAG", "index :" + index);
//
//
//                    Toast.makeText(getApplicationContext(),
//                            "Clicked Button Index :" + index,
//                            Toast.LENGTH_LONG).show();

                }
            });
            // Create TextView
            TextView book = new TextView(this);
            book.setText("Name-"+bookdetails[1]+"\n");
            book.append("Publisher-" + bookdetails[2] + "\n");
            book.append("Subject-"+bookdetails[3]+"\n");
            book.append("ISBN-"+bookdetails[4]+"\n");
            book.append("Catalog No.-"+bookdetails[5]+"\n");
            book.append("Year-"+bookdetails[6]+"\n");
            book.append("Copy-"+bookdetails[7]+"\n");
            book.append("Status-"+bookdetails[8]+"\n");


            section.addView(btn);
            section.addView(book);
            String check="ON LOAN";
            if ((bookdetails[8].trim().equalsIgnoreCase(check.trim()))&&(usertype.trim().equalsIgnoreCase("student")))
            {
                final Button reserve = new Button(this);
                // Give button an ID
                final String Catalognum= bookdetails[5].trim();
                final String copynum=bookdetails[7];

                reserve.setId(i+100);
                reserve.setText("RESERVE");
                btn.setGravity(Gravity.LEFT);
                //LEFT , TOP, RIGHT , BOTTOM
                reserve.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rsv2,0,0,0);


                // set the layoutParams on the button
                reserve.setLayoutParams(params);
                reserve.setBackgroundColor(Color.TRANSPARENT);
                //reserve.setTextColor(Color.RED);



                final int id = i+100;
                // Set click listener for button
                reserve.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        // Log.i("TAG", "index :" + index);
                        showAlert(username,Catalognum, copynum);

//                    Toast.makeText(getApplicationContext(),
//                            "Catalog Num :" + Catalognum+", Copu Num:"+copynum,
//                            Toast.LENGTH_LONG).show();

                    }
                });

                section.addView(reserve);
            }



            //Create Button
//            final Button btn= new Button(this);
//            btn.setId(i+1);
            mainlayout.addView(section);
        }
        //String  xxxx= booklist.get(1);
        // Log.d("Book1",xxxx);
        // bookdetails.setText(Title);

    }

    public void showAlert(String userID, String catnum, String cpynum){
        final String uID=userID;
        final String catnumb=catnum;
        final String cpyn=cpynum;
        Bookdetails.this.runOnUiThread(new Runnable() {
            public void run() {

                AlertDialog.Builder builder = new AlertDialog.Builder(Bookdetails.this);
                builder.setTitle("Confirm");
                builder.setMessage("Confirm Resevation")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Reserve(uID,catnumb,cpyn);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void Reserve(String uID, String catnum, String cpynum){
        final String userid=uID;
        final String catnumber=catnum;
        final String copynumber=cpynum;
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {


                    List<NameValuePair> params2 = new ArrayList<NameValuePair>();

                    params2.add(new BasicNameValuePair("userid", userid));
                    params2.add(new BasicNameValuePair("catnumber", catnumber));
                    params2.add(new BasicNameValuePair("copynumber", copynumber));



                    // params2.add(new BasicNameValuePair(TAG_bmi, bmiInterpretation));

                    JSONObject json = jParser1.makeHttpRequest(URL, "POST", params2);

                    // data = json.getJSONArray("data");

                    //Log.d("Create",data.getJSONObject(1).toString());
                    Log.d("Create ","-------------");
                    try {
                        // String sucesss=json.getString(TAG_DATA);
                        //data = json.getJSONArray("data");




                        int success = json.getInt(TAG_SUCCESS);

                        if (success == 1) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(getBaseContext(),
                                            "Book Successfully Reserved",
                                            Toast.LENGTH_SHORT).show();



                                }
                            });




                        }
                        else
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(getBaseContext(),
                                            "Reservation Unsuccessful",
                                            Toast.LENGTH_SHORT).show();



                                }
                            });

//
                    } catch (final JSONException e) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getBaseContext(),
                                        "Reservation Unsuccessful",
                                        Toast.LENGTH_SHORT).show();
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bookdetails, menu);
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
