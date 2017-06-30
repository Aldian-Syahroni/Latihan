package com.example.aldhyan.contactaldian;


import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    String[] item,number;
    ListAdapter adapter;
    Cursor mCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.List);
    }
    @Override
    protected void onResume(){
        super.onResume();
        readContacts();
    }
    public void readContacts(){
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null,null);

        if(cur.getCount()>0){
            int i = 0;
            int j = 0;
            item = new String[cur.getCount()];
            number = new String[cur.getCount()];
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                if (Integer.
                        parseInt(cur.getString(cur
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER)))>1)
                    System.out.println("ID:" + id +"Name:"+ name);
                item[i++] = name;

                Cursor pCur= cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID +"= ?",new String[] { id }, null);
                while (pCur.moveToNext()){
                    String phone = pCur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    System.out.println("phone" + phone);
                    number[j++] = phone;
                }
                pCur.close();

            }
        }
        ArrayAdapter<String> g = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, item);
        listView.setAdapter(g);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                String v = number[arg2];
                Toast.makeText(getApplicationContext(), v,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
