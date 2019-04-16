package com.example.phonebook;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import entities.Contact;
import database.*;
import android.view.View;
import android.widget.*;
import android.content.*;

import java.util.ArrayList;

public class AddContactActivity extends AppCompatActivity {

    private Button buttonBack;
    private Button buttonSave;
    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextDescription;
    String category;
    Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        this.editTextName = (EditText) findViewById(R.id.editTextName);
        this.editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        this.editTextDescription = (EditText) findViewById(R.id.editTextDescription);

        //tuk dobavqm za spineraa
        ArrayList<String> categoryList =  new ArrayList<String>();
        categoryList.add("Familiar");
        categoryList.add("Family");
        categoryList.add("Friends");
        categoryList.add("Colleagues");


        sp = (Spinner) findViewById(R.id.spinner_category);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout,R.id.spinnerText, categoryList);
        sp.setAdapter(adapter);


        this.buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AddContactActivity.this,MainActivity.class);
                startActivity(intent1);
            }
        });

        this.buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactDB contactDB = new ContactDB(getBaseContext());
                Contact contact = new Contact();
                contact.setName(editTextName.getText().toString());
                contact.setPhone(editTextPhone.getText().toString());
                contact.setDescription(editTextDescription.getText().toString());
                //setvam stoinostta izbrana ot spinera
                category= sp.getSelectedItem().toString();
                contact.setCategory(category);

                if (contactDB.create(contact)){
                    Intent intent1 = new Intent(AddContactActivity.this, MainActivity.class);
                    startActivity(intent1);
                } else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("Fail");
                    builder.setCancelable(false);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.create().show();
                }

            }
        });

    }
}
