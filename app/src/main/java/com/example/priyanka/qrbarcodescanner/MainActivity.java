package com.example.priyanka.qrbarcodescanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.zxing.Result;

import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class MainActivity extends AppCompatActivity{

    EditText etProductName;
    RadioGroup rg;
    Button btnScan;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final int PREFERENCE_MODE_PRIVATE = 0;
    private static final String MY_FILE = "myFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etProductName = findViewById(R.id.editTextItem);
        btnScan = findViewById(R.id.btnScan);
        rg = findViewById(R.id.radioInOut);

        sharedPreferences = getSharedPreferences(MY_FILE, PREFERENCE_MODE_PRIVATE);
        editor = sharedPreferences.edit();

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = etProductName.getText().toString();
                int selectedId = rg.getCheckedRadioButtonId();
                RadioButton rb = findViewById(selectedId);
                String stringRb = rb.getText().toString();
                editor.putString("product", productName);
                editor.putString("inOut", stringRb);
                editor.commit();
                Intent i = new Intent(MainActivity.this, ScannerActivity.class);
                startActivity(i);

            }
        });
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
            Intent i = new Intent(MainActivity.this, ScannerActivity.class);
            startActivity(i);
        }
        else if (id == R.id.main){
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i);
        }
        else if (id == R.id.third) {
            Intent i = new Intent(MainActivity.this, FinalActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
