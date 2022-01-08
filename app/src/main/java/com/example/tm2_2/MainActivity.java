package com.example.tm2_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter adapter;
    private Button addItem;
    private Button deleteItem;
    private Button save;
    private Button load;
    private static final String FILE_NAME = "example.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_item);
        addItem = (Button) findViewById(R.id.add_item);
        deleteItem = (Button) findViewById(R.id.delete_item);
        save = (Button) findViewById(R.id.save);
        load = (Button) findViewById(R.id.load);
        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = arrayList.size() + 1;

                arrayList.add("Item " + count);
                adapter.notifyDataSetChanged();

            }
        });

        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {


                if(arrayList.isEmpty()==false){
                arrayList.remove((arrayList.size() - 1));}
                adapter.notifyDataSetChanged();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(baos);

                for (String element : arrayList) {
                    try {
                        out.writeUTF(element);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                byte[] bytes = baos.toByteArray();
                FileOutputStream fos = null;
                try {
                    fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                    fos.write(bytes);
                    Toast.makeText(getApplicationContext(), "Saved to " + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(fos !=null){
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });

        load.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view3) {
                FileInputStream fis = null;

                arrayList.removeAll( arrayList);

                try {
                    fis = openFileInput(FILE_NAME);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);
                    StringBuilder sb = new StringBuilder();
                    String text;

                    while((text = br.readLine()) != null){
                        sb.append(text);
                    }

                    int count = 1;
                    for(int i = 0; i<sb.length()/8; i++){

                        arrayList.add("Item " + count);
                        count++;

                    }
                    Toast.makeText(getApplicationContext(), "Load from " + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
        });

    }
}