package com.example.cropprice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContactActivity extends AppCompatActivity {

    EditText contactName, contactSubject, contactMessage;
    Button contactButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

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
                    startActivity(new Intent(ContactActivity.this, SellerHomeActivity.class));
                    finish();
                }
            }
        });

    }
}