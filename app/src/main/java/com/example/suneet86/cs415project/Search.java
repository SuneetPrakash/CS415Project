package com.example.suneet86.cs415project;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Search extends AppCompatActivity {
    Button search;
    Button reserve;
    TextView keyword;
    Spinner searchtype;
    TextView details;

    String username="S11086903";
    String usertype="student";

    ArrayList <String> books = new ArrayList<String>();
    String Title,Name,publisher,subject,status,catalog_num,pub_year,isbn,copy_num;

    private static String URL= "http://192.168.0.167/CS415/booksearch.php";
    private static String URL2= "http://192.168.0.167/CS415/searchreservation.php";
    private static final String TAG_DATA = "data";
    private static final String TAG_SUCCESS= "success";
    JSONArray data= null;

    JSONParser jParser1 = new com.example.suneet86.cs415project.JSONParser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search = (Button)findViewById(R.id.btnSearch);
        reserve = (Button)findViewById(R.id.btnreserve);
        keyword=(TextView)findViewById(R.id.keyword);
        searchtype=(Spinner)findViewById(R.id.searchtype);


        search.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        Intent i = new Intent(Search.this, Search.class);
//                        startActivity(i);

                        //userlogin();
                        search();
                        //details.setText(books.get(1));
                    }
                });

        reserve.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        Intent i = new Intent(Search.this, Search.class);
//                        startActivity(i);

                        //userlogin();
                        reservation();
                        //details.setText(books.get(1));
                    }
                });
    }

    public void search(){

        final String key;
        final String type;

        key="%"+keyword.getText().toString()+"%";
        type=searchtype.getSelectedItem().toString();
        System.out.println(key+ type);

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {

                    List<NameValuePair> params2 = new ArrayList<NameValuePair>();

                    params2.add(new BasicNameValuePair("Keyword", key));
                    params2.add(new BasicNameValuePair("SearchType", type));



                    // params2.add(new BasicNameValuePair(TAG_bmi, bmiInterpretation));

                    JSONObject json = jParser1.makeHttpRequest(URL, "POST", params2);
                    Log.d("Create Response", json.toString());
                    Log.d("Create ",json.getString(TAG_SUCCESS));
                    // data = json.getJSONArray("data");
                    JSONArray bookdata = json.getJSONArray(TAG_DATA);
                    //Log.d("Create",data.getJSONObject(1).toString());
                    Log.d("Create ","-------------");
                    try {
                        // String sucesss=json.getString(TAG_DATA);
                        //data = json.getJSONArray("data");

                        for (int i=0;i<bookdata.length();i++)
                        {
                            JSONObject c = bookdata.getJSONObject(i);

                            Title=c.getString("book_title");
                            Name=c.getString("Name");
                            pub_year=c.getString("pub_year");
                            publisher=c.getString("publisher_name");
                            subject=c.getString("subject_name");
                            catalog_num=c.getString("catalog_num");
                            isbn=c.getString("book_isbn");
                            copy_num=c.getString("copy_num");
                            status=c.getString("book_status_name");
                            System.out.println(Title+" : "+Name+" : "+publisher+" : "+subject+" : "+status+" : "+catalog_num+" : "+pub_year+" : "+isbn+" : "+copy_num);
                            Log.d("Title",Title);
                            String sum=Title+" : "+Name+" : "+publisher+" : "+subject+" : "+isbn+" : "+catalog_num+" : "+pub_year+" : "+copy_num+" : "+status;
                            books.add(i,sum);



                        }

                        Intent i = new Intent(Search.this, Bookdetails.class);

                        //Create the bundle
                        Bundle bundle = new Bundle();
                        //Add data to bundle
                        bundle.putStringArrayList("books", books);
                        bundle.putString("username",username);
                        bundle.putString("usertype",usertype);
                        //Add the bundle to the intent
                        i.putExtras(bundle);

                        //Fire that second activity
                        startActivity(i);


//
                    } catch (final JSONException e) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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

        //Intent i = new Intent(MainActivity.this, Search.class);
        //startActivity(i);

    }

    public void reservation(){
        final ArrayList <String> reserves = new ArrayList<String>();
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {

                    List<NameValuePair> params2 = new ArrayList<NameValuePair>();

                    params2.add(new BasicNameValuePair("userID", username));




                    // params2.add(new BasicNameValuePair(TAG_bmi, bmiInterpretation));

                    JSONObject json = jParser1.makeHttpRequest(URL2, "POST", params2);

                    // data = json.getJSONArray("data");
                    JSONArray bookdata = json.getJSONArray(TAG_DATA);
                    //Log.d("Create",data.getJSONObject(1).toString());
                    Log.d("Create ","-------------");
                    try {
                        // String sucesss=json.getString(TAG_DATA);
                        //data = json.getJSONArray("data");

                        for (int i=0;i<bookdata.length();i++)
                        {
                            JSONObject c = bookdata.getJSONObject(i);

                            Title=c.getString("book_title");

                            reserves.add(i,Title);

                        }

                        Intent i = new Intent(Search.this, Reservationlist.class);

                        //Create the bundle
                        Bundle bundle = new Bundle();
                        //Add data to bundle
                        bundle.putStringArrayList("reserves", reserves);
                        bundle.putString("username",username);
                        bundle.putString("usertype",usertype);
                        //Add the bundle to the intent
                        i.putExtras(bundle);

                        //Fire that second activity
                        startActivity(i);


//
                    } catch (final JSONException e) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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

        //Intent i = new Intent(MainActivity.this, Search.class);
        //startActivity(i);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
