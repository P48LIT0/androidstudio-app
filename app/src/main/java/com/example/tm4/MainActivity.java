package com.example.tm4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView contact;
    private Button chooseContact;
    private EditText textInput;
    private Button search;
    private Button showOnMap;
    private Button takeAPicture;
    private ImageView photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getPermissions();
    }

    private void init(){
        contact = findViewById(R.id.contact);

        chooseContact = findViewById(R.id.chooseContact);
        chooseContact.setOnClickListener(this);

        textInput = findViewById(R.id.textInput);

        search = findViewById(R.id.search);
        search.setOnClickListener(this);

        showOnMap = findViewById(R.id.showOnMap);
        showOnMap.setOnClickListener(this);

        photo = findViewById(R.id.photo);

        takeAPicture = findViewById(R.id.takeAPicture);
        takeAPicture.setOnClickListener(this);
    }

    private void getPermissions(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            chooseContact.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1001);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1001:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    chooseContact.setEnabled(true);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2001){
            Uri contactUri = data.getData();
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor c = getApplicationContext().getContentResolver().query(contactUri, projection, null, null, null);
            if(c != null && c.moveToFirst()){
                int nameInd = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int numInd = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String data1 = c.getString(nameInd) + "\n" + c.getString(numInd);
                contact.setText(data1);
            }
        }
        else if (requestCode == 2002) photo.setImageBitmap((Bitmap) data.getExtras().get("data"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.chooseContact:
                chooseContact();
                break;
            case R.id.search:
                search(textInput.getText().toString());
                break;
            case R.id.showOnMap:
                showOnMap(textInput.getText().toString());
                break;
            case R.id.takeAPicture:
                takeAPicture();
                break;
        }
    }

    private void chooseContact(){
        Intent chooseContactIntent = new Intent(Intent.ACTION_PICK);
        chooseContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(chooseContactIntent, 2001);
    }

    private void search(String s){
        Intent searchIntent = new Intent(Intent.ACTION_WEB_SEARCH);
        searchIntent.putExtra(SearchManager.QUERY, s);
        startActivity(searchIntent);
    }

    private void showOnMap(String s){
        Intent showOnMapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + s));
        showOnMapIntent.putExtra(SearchManager.QUERY, s);
        startActivity(showOnMapIntent);
    }

    private void takeAPicture(){
        Intent takeAPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivity(takeAPicture);
        startActivityForResult(takeAPicture, 2002);
    }
}