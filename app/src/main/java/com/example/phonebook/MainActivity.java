package com.example.phonebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import adapters.ContactListAdapter;
import entities.Contact;
import database.*;
import android.view.View;
import android.widget.*;
import android.content.*;

public class MainActivity extends AppCompatActivity {

    private Button buttonAdd;
    private ListView listViewContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.buttonAdd = (Button) findViewById(R.id.buttonAdd);


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,AddContactActivity.class);
                startActivity(intent1);

            }
        });

        final ContactDB contactDB = new ContactDB(this);
        this.listViewContacts = (ListView) findViewById(R.id.listViewContacts);
        this.listViewContacts.setAdapter(new ContactListAdapter(this,contactDB.findAll()));

        this.listViewContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = contactDB.findAll().get(position);
                Intent intent1 = new Intent(MainActivity.this,ContactDetailActivity.class);
                intent1.putExtra("contact",contact);
                startActivity(intent1);
            }
        });
    }
}
