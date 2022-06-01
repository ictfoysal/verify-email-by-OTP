/*Ict Foysal & Foysal Tech & Mohammed Foysal Official*/

package com.foysaltech.verifyemailbyotp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextInputEditText editText;
    Button button, confirmbtn;
    int code;
    PinView pinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.signup_Email);
        button = findViewById(R.id.signup_btn);
        pinView = findViewById(R.id.firstPinView);
        confirmbtn = findViewById(R.id.signup_confirm);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                code = random.nextInt(8999) + 1000;
                String url = "https://foysaltech.000webhostapp.com/sendEmail.php";
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                        findViewById(R.id.LinearLayout_Email).setVisibility(View.GONE);
                        findViewById(R.id.LinearLayout_PinView).setVisibility(View.VISIBLE);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> mydata = new HashMap<>();
                        mydata.put("email", editText.getText().toString());
                        mydata.put("code", String.valueOf(code));
                        return mydata;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputCode = pinView.getText().toString();
                if (inputCode.equals(String.valueOf(code))) {
                    Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}