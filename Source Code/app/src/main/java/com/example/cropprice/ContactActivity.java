package com.example.cropprice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ContactActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText contactName, contactSubject, contactMessage;
    Button contactButton;
    String addContactDataUrl = "https://crop-price-web.000webhostapp.com/seller.php?addContactData=true";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // set the toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));

        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText("Contact Us");

        contactName = findViewById(R.id.contactName);
        contactSubject = findViewById(R.id.contactSubject);
        contactMessage = findViewById(R.id.contactMessage);
        contactButton = findViewById(R.id.contactButton);

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = contactName.getText().toString();
                String subject = contactSubject.getText().toString();
                String message = contactMessage.getText().toString();

                if (name.trim().equals("")) {
                    contactName.setError("Name is required");
                } else if (subject.trim().equals("")) {
                    contactSubject.setError("Subject is required");
                } else if (message.trim().equals("")) {
                    contactMessage.setError("Message is required");
                } else {
                    SweetAlertDialog pDialog = new SweetAlertDialog(ContactActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    StringRequest request = new StringRequest(Request.Method.POST, addContactDataUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("success")) {
                                pDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Contact Form Has Been Submitted", Toast.LENGTH_LONG).show();
                                contactName.setText("");
                                contactSubject.setText("");
                                contactMessage.setText("");
                            } else {
                                pDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            pDialog.dismiss();
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> param = new HashMap<>();
                            param.put("name", name);
                            param.put("subject", subject);
                            param.put("message", message);

                            return param;
                        }
                    };

                    if (requestQueue == null) {
                        requestQueue = Volley.newRequestQueue(ContactActivity.this);
                        requestQueue.add(request);
                    } else {
                        requestQueue.add(request);
                    }
                }
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}