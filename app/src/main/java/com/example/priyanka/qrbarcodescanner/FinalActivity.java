package com.example.priyanka.qrbarcodescanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

public class FinalActivity extends AppCompatActivity {

    TextView tvInOut, tvProductName, tvSerial;
    ListView lv;
    Button btnUpload;
    private SharedPreferences sharedPreferences;
    private static final int PREFERENCE_MODE_PRIVATE = 0;
    private static final String MY_FILE = "myFile";
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        tvInOut = findViewById(R.id.tvInOut);
        tvProductName = findViewById(R.id.tvProduct);
        tvSerial = findViewById(R.id.tvSerial);
        btnUpload = findViewById(R.id.btnUpload);
        lv = findViewById(R.id.lv);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd ");
        final String strDate = mdformat.format(calendar.getTime());
        tvSerial.setText("Date: " + strDate);
        sharedPreferences = getSharedPreferences(MY_FILE, PREFERENCE_MODE_PRIVATE);

        final String productName = sharedPreferences.getString("product", "Default");
        final String inOut = sharedPreferences.getString("inOut", "Default");

        tvProductName.setText("Equipment Type: " + productName);
        tvInOut.setText("Going/Coming: " + inOut);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getArrayList("serialList"));
        lv.setAdapter(adapter);


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i = 0; i < getArrayList("serialList").size(); i++){
                    HttpRequest request = new HttpRequest ("https://pronics-digital.000webhostapp.com/addSerialNumber.php");
                    request.setOnHttpResponseListener(mHttpResponseListener);
                    request.setMethod("POST");
                    request.addData("dateScan", strDate);
                    request.addData("equipmentType", productName);
                    request.addData("in_Out", inOut);
                    request.addData("serialNumber", getArrayList("serialList").get(i));
                    request.execute();
                }

            }
        });

    }

    private HttpRequest.OnHttpResponseListener mHttpResponseListener =
            new HttpRequest.OnHttpResponseListener() {
                @Override
                public void onResponse(String response){

                    // process response here
                    try {
                        Log.i("JSON Results: ", response);

                        JSONObject jsonObj = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            };

    public ArrayList<String> getArrayList(String key){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main){
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
