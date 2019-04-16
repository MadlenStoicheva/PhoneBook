Курсова работа

по “Програмиране на мобилни приложения” 
на тема 
„Телефонен указател с контакти“

на Мадлен Костадинова Стойчева, 3-ти курс СТД, 
Фак. номер: 1601681014
Линк към качения в GitHub проект:



							Преподавател:
април, 2019 г.					доц.д-р Николай Касъклиев  

СЪДЪРЖАНИЕ:



Увод ....................................................................................................... 2
Предварително проучване .................................................................. 3
Използвани технологии ........................................................................ 3
Описание на проекта ............................................................................ 3
Програмиране на приложението ........................................................13
Заключение ..........................................................................................
Използвана литература .......................................................................




УВОД:


	Изготвената курсова работа на тема „Телефонен указател с контакти“ цели да запознае читателите със създаденото мобилно приложение на тази тема, използваните технологии и методи на разработка. 
	Телефонният указател с контакти поддържа локална база данни с телефонни номера и предоставя възможност за всеки нов запис да се пази информация за име, телефонен номер, допълнително описание на контакта, категория потребител (познати, колеги, семейство, приятели). Приложението има следната фукнкционалност: създаване, преглед, редакция и изтриване на контакт.


ПРЕДВАРИТЕЛНО ПРОУЧВАНЕ:
	
	За изготвянето на актуално мобилно приложение на тази
тема беше направен анализ на сходни мобилни приложения на Android и iOS поддържащи подобна функционалност. След направеното проучване бяха установени техники за добра визуализация и идеи за бъдещото развитие на създаденото в момента мобилно приложение.


ИЗПОЛЗВАНИ ТЕХНОЛОГИИ:

	За създаването на проекта бе използвана платформата Android Studio версия 3.3.2 като среда за разработка на нейтив мобилни приложения с помощта на езика JAVA.  (https://developer.android.com/studio)
Android Studio е платформа пусната на пазара през декември 2014г. от Google. Програмата е базирана на IntelliJ IDEA, като основно IDE за Android предоставен от Google за качествена разработка на мобилни приложения за Android. Освен на JAVA, програмистите имат възможност да създават приложения с езици като Kotlin и C++. Едно от основните предимства на Android Studio е предоставеният от платформата емулатор, който дава възможност бързо да се провери как точно ще изглежда и действа разработваното приложение на конкретно устройство при зададен размер и резолюция на екрана.


ОПИСАНИЕ НА ПРОЕКТА:

	Приложението представлява телефонен указател с контакти. Данните се съхраняват с помощта на локална база данни. Всеки нов запис има следните характеристики: име, телефонен номер, допълнително описание на контакта, категория потребител (познати, колеги, семейство, приятели). Приложението има следната фукнкционалност: преглед на списък с всички контакти, създаване на нов контакт, преглед, редакция и изтриване на контакт.
 
СТРУКТУРА НА ПРОЕКТА:	

Разработеното приложение включва 4 Activities (Main Activity, Contact Detail Activity, Add Contact Activity, Edit Contact Activity). Клас Contact където са дефинирани всички атрибути на обекта, ContactDB клас за връзка с базата данни и класът Contact List Adapter за адаптиране на всички контакти и представянето им в List.



MainActivity

MainActivity е началната страница, която се отваря при влизане в приложението. Включва бутон Add New Contact за добавяне на нов контакт, ListView където се извежда списък с всички записи в базата данни и TextView което представя кратка информация за създателя на приложението. 


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
 



Contact List Adapter

За извеждането на лист с контакти се  използва предварително създаденият клас Contact List Adapter с помощта на който в Main Activity се сетват всички записи от базата данни.

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.*;
import entities.*;
import java.util.*;
import android.view.*;
import com.example.phonebook.R;
import org.w3c.dom.Text;

public class ContactListAdapter extends ArrayAdapter<Contact>{

    private Context context;
    private List<Contact> contacts;

    public ContactListAdapter(Context context, List<Contact> contacts) {
        super(context, R.layout.contact_layout, contacts);
        this.context = context;
        this.contacts = contacts;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.contact_layout, parent, false);

        TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
        textViewName.setText(contacts.get(position).getName());

        TextView textViewPhone = (TextView) view.findViewById(R.id.textViewPhone);
        textViewPhone.setText(contacts.get(position).getPhone());

        TextView textViewDescription = (TextView) view.findViewById(R.id.textViewDescription);
      textViewDescription.setText(contacts.get(position).getDescription());

        TextView textViewCategory = (TextView) view.findViewById(R.id.textViewCategory);
        textViewCategory.setText(contacts.get(position).getCategory());

        return view;
    }
}

Add Contact Activity

Add Contact Activity представява форма за въвеждане на информация на новия контакт. Полетата във формата включват информация за Име, Телефонен номер, Описание и Категория (Семейство, приятели, познати, колеги). За показването на съответните възможности за категория е използван Spinner, където са сетнати съответните стойности за избор. За целта е използван ArrayList със съответните опции.















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

        //Добавяне на елементите на спинера
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
                //Сетва стойността избрана от спинера
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

Contact Detail Activity 

Contact Detail Activity представя страница, която визуализира избран запис от списъка с контакти. На нея се показват всички атрибути на един контакт, а именно: Име, Телефонен номер, Описание и Категория за съответния контакт.
	Най-долу на екрана са представени три бутона за потенциални възможности от потребителя. Първият бутон в долният ляв ъгъл е за връщане назад към MainActivity (Началният екран). Вторият бутон предоставя възможност за препратка към Edit Contact Activity и редакция на избрания контакт. Третият бутон в долният десен ъгъл служи за изтриване на избрания контакт. Бутонът за изтриване отваря модален прозорец, който пита потребителя дали наистина да бъде изтрит този контакт. След получаване на полужителен отговор, контактът бива изтрит, а потребителят бива върнат отново в MainActivity (Началната страница) на приложението.

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import entities.Contact;
import database.*;
import android.view.View;
import android.widget.*;
import android.content.*;

public class ContactDetailActivity extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewPhone;
    private TextView textViewDescription;
    private TextView textViewCategory;
    private Button buttonBack;
    private Button buttonEdit;
    private Button buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        Intent intent1 = getIntent();
        final Contact contact = (Contact) intent1.getSerializableExtra("contact");

        this.textViewName = (TextView) findViewById(R.id.textViewName);
        this.textViewName.setText(contact.getName());

        this.textViewPhone = (TextView) findViewById(R.id.textViewPhone);
        this.textViewPhone.setText(contact.getPhone());

        this.textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        this.textViewDescription.setText(contact.getDescription());

        this.textViewCategory = (TextView) findViewById(R.id.textViewCategory);
        this.textViewCategory.setText(contact.getCategory());

        this.buttonBack = (Button) findViewById(R.id.buttonBack);
        this.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ContactDetailActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });

        this.buttonDelete = (Button) findViewById(R.id.buttonDelete);
        this.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setCancelable(false);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
                        ContactDB contactDB = new ContactDB(getBaseContext());
                        if (contactDB.delete(contact.getId())){
                            Intent intent1 = new Intent(ContactDetailActivity.this, MainActivity.class);
                            startActivity(intent1);
                        }else{
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getBaseContext());
                            builder1.setCancelable(false);
                            builder1.setMessage("Fail");
                            builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            builder1.create().show();
                        }
                    }
                });

builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });

        this.buttonEdit = (Button) findViewById(R.id.buttonEdit);
        this.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ContactDetailActivity.this, EditContactActivity.class);
                intent1.putExtra("contact", contact);

                startActivity(intent1);
            }
        });
    }
}
 
Edit Contact Activity

Edit Contact Activity е прозорец в който се показват данни за избрания контакт и се предоставя възможност за тяхната редакция. Всяко едно от полетата (Име, Телефон, Описание, Категория) може да бъде редактирано. Всяка направена промяна ще бъде отразена в съответния запис в базата данни.


import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import entities.Contact;
import database.*;
import android.view.View;
import android.widget.*;
import android.content.*;
import java.util.ArrayList;

public class EditContactActivity extends AppCompatActivity {

    private EditText editTextName;
    private  EditText editTextPhone;
    private EditText editTextDescription;
    private Button buttonBack;
    private Button buttonSave;
    String category;
    Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        Intent intent = getIntent();
        final Contact contact = (Contact) intent.getSerializableExtra("contact");
        this.editTextName = (EditText) findViewById(R.id.editTextName);
        this.editTextName.setText(contact.getName());

        this.editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        this.editTextPhone.setText(contact.getPhone());

        this.editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        this.editTextDescription.setText(contact.getDescription());

        //Добавя се масив с възможности за добавяне в спинера
        ArrayList<String> categoryList =  new ArrayList<String>();
        categoryList.add("Familiar");
        categoryList.add("Family");
        categoryList.add("Friends");
        categoryList.add("Colleagues");

        sp = (Spinner) findViewById(R.id.spinner_category);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout,R.id.spinnerText, categoryList);
        sp.setAdapter(adapter);

        //Взима стойността от базата и я сетва в спинера
        String categoryValue = contact.getCategory();
        int spinnerPosition = adapter.getPosition(categoryValue);
        sp.setSelection(spinnerPosition);

        this.buttonBack = (Button) findViewById(R.id.buttonBack);
        this.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(EditContactActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        this.buttonSave = (Button) findViewById(R.id.buttonSave);
        this.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactDB contactDB = new ContactDB(getBaseContext());
                contact.setName(editTextName.getText().toString());
                contact.setPhone(editTextPhone.getText().toString());
                contact.setDescription(editTextDescription.getText().toString());

                //Сетва стойността избрана от спинера 
                category= sp.getSelectedItem().toString();
                contact.setCategory(category);

                if (contactDB.update(contact)){
Intent intent1 = new Intent(EditContactActivity.this, MainActivity.class);
startActivity(intent1);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setCancelable(false);
                    builder.setMessage("Fail");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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


ПРОГРАМИРАНЕ НА ПРИЛОЖЕНИЕТО

Приложението използва SQLite като база данни. В класа ContactDB се създава таблица с име “contact” съдържаща колони за id, name, phone, description, category.

ContactDB

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.*;
import java.util.*;
import entities.*;

public class ContactDB extends SQLiteOpenHelper {

    private static String dbName = "ContactDB";
    private static String tableName = "contact";
    private static String idColumn = "id";
    private static String nameColumn = "name";
    private static String phoneColumn = "phone";
    private static String descriptionColumn = "description";
    private static String categoryColumn = "category";
    private Context context;

    public ContactDB(Context context){
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + tableName + "(" +
                idColumn +  " integer primary key autoincrement, " +
                nameColumn + " text, " +
                phoneColumn + " text, " +
                descriptionColumn + " text, " +
                categoryColumn + " text " +
                ")" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists " + tableName);
        onCreate(sqLiteDatabase);
    }

    public List<Contact> findAll(){
        try {
            List<Contact> contacts =  new ArrayList<Contact>();
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + tableName,null);
            if(cursor.moveToFirst()){
                do {
                    Contact contact = new Contact();
                    contact.setId(cursor.getInt(0));
                    contact.setName(cursor.getString(1));
                    contact.setPhone(cursor.getString(2));
                    contact.setDescription(cursor.getString(3));
                    contact.setCategory(cursor.getString(4));
                    contacts.add(contact);
                }while(cursor.moveToNext());
            }
            sqLiteDatabase.close();
            return contacts;
        }
        catch (Exception e){
            return null;
        }
    }

    public boolean create (Contact contact){
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(nameColumn,contact.getName().toUpperCase());
            contentValues.put(phoneColumn, contact.getPhone());
            contentValues.put(descriptionColumn, contact.getDescription());
            contentValues.put(categoryColumn,contact.getCategory());
            long rows = sqLiteDatabase.insert(tableName,null,contentValues);

            sqLiteDatabase.close();
            return rows>0;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean delete (int id){
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            int rows = sqLiteDatabase.delete(tableName, idColumn + " =?" , new String[]{String.valueOf(id)});

            sqLiteDatabase.close();
            return rows>0;
        }
        catch (Exception e){
            return false;
        }
    }

    public Contact find (int id){
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + tableName + " where " + idColumn + " = ?" , new String[]{String.valueOf(id)});
            Contact contact = null;
            if (cursor.moveToFirst()){
                contact = new Contact();
                contact.setId(cursor.getInt(0));
                contact.setName(cursor.getString(1));
                contact.setPhone(cursor.getString(2));
                contact.setDescription(cursor.getString(3));
                contact.setCategory(cursor.getString(4));
            }

            sqLiteDatabase.close();
            return contact;
        }
        catch (Exception e){
            return null;
        }
    }

    public boolean update(Contact contact){
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(nameColumn, contact.getName().toUpperCase());
            contentValues.put(phoneColumn, contact.getPhone());
            contentValues.put(descriptionColumn, contact.getDescription());
            contentValues.put(categoryColumn, contact.getCategory());
            int rows = sqLiteDatabase.update(tableName, contentValues, idColumn + " = ?", new String[] {String.valueOf(contact.getId())});

            sqLiteDatabase.close();
            return rows>0;
        }
        catch (Exception ex){
            return false;
        }
    }
}

Contact

Класът Contact представлява модел, който съдържа всички полета за един контакт.

import java.io.Serializable;

public class Contact implements Serializable {

    private int id;
    private String name;
    private String phone;
    private String description;
    private String category;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public Contact(int id, String name, String phone, String description, String category) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.category = category;
    }

    public Contact() {
    }
}

 ЗАКЛЮЧЕНИЕ

Създаденото приложение на тема „Телефонен указател с контакти“ предоставя възможност на потребителите да добавят нови контакти в своя указател, да преглеждат, редактират и изтриват избран контакт. Използваната цветова гама е генерирана чрез сайта за цветови комбинации за мобилни приложения използваща Material Palette с цветове (https://material.io/tools/color/) .

ИДЕИ ЗА БЪДЕЩО РАЗВИТИЕ

Приложението има потенциал да се развие, като се добавят възможности за телефонно обаждане на избран контакт или изпращане на съобщение. От гледна точка на User Experience, може да се добави опция за добавяне на снимка към контакт. А за улеснение на работата на потребителя може да се добави функционаност за търсене на контакт и подреждане на списъка с контакти в азбучен ред.


ИЗПОЛЗВАНА ЛИТЕРАТУРА И РЕСУРСИ

-	Информация за Android Studio - https://bg.wikipedia.org/wiki/Android#%D0%9F%D1%80%D0%B8%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D1%8F_(Apps)
-	За иконка за мобилното приложение - https://www.flaticon.com 
-	Документация на Android Studio - https://developer.android.com
-	Генериране на цветова палитра - https://material.io/tools/color/
-	Платформа за тестване - https://appetize.io/
