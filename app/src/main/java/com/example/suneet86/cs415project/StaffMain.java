package com.example.suneet86.cs415project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class StaffMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staffmain);



        findViewById(R.id.btnLogout).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }
                });



        findViewById(R.id.btnStaffProfile).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent i = new Intent(getApplicationContext(), StaffProfile.class);
                        startActivity(i);
                    }
                });


        findViewById(R.id.btnCheckOut).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent i = new Intent(getApplicationContext(), Checkout.class);
                        startActivity(i);

                    }
                });



        findViewById(R.id.btnCheckInBook).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent i = new Intent(getApplicationContext(), Checkin.class);
                        startActivity(i);

                    }
                });

















    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_staff_main, menu);
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
