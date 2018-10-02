package com.example.priyanka.qrbarcodescanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Set;

public class FinalActivity extends AppCompatActivity {

    TextView tvInOut, tvProductName, tvSerial;
    ListView lv;
    private SharedPreferences sharedPreferences;
    private static final int PREFERENCE_MODE_PRIVATE = 0;
    private static final String MY_FILE = "myFile";
    ArrayList<String> serialNumbers;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        tvInOut = findViewById(R.id.tvInOut);
        tvProductName = findViewById(R.id.tvProduct);
        tvSerial = findViewById(R.id.tvSerial);
        lv = findViewById(R.id.lv);

        sharedPreferences = getSharedPreferences(MY_FILE, PREFERENCE_MODE_PRIVATE);

        String productName = sharedPreferences.getString("product", "Default");
        String inOut = sharedPreferences.getString("inOut", "Default");
        String serialNumber = sharedPreferences.getString("serial", "Default");


//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getArrayList("serialNumbers"));
//        lv.setAdapter(adapter);

        tvProductName.setText(productName);
        tvInOut.setText(inOut);
        tvSerial.setText(serialNumber);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.scanner){
            Intent i = new Intent(FinalActivity.this, ScannerActivity.class);
            startActivity(i);
        }
        else if (id == R.id.main){
            Intent i = new Intent(FinalActivity.this, MainActivity.class);
            startActivity(i);
        }
        else if (id == R.id.third) {
            Intent i = new Intent(FinalActivity.this, FinalActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
