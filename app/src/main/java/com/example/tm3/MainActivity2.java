package com.example.tm3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    private TextView parWej;
    private Button odbierz;
    private Button odeslij;
    private Intent odbierzIntent;
    private String parWejData;
    public String wyj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        parWej = findViewById(R.id.textView);
        odbierz = findViewById(R.id.button3);
        odeslij = findViewById(R.id.button2);

        odbierz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                odbierzIntent = getIntent();
                parWej.setText(odbierzIntent.getStringExtra("parametrWejsciowy"));
                parWejData = parWej.getText().toString();
                wyj = parWejData + "abc";


            }
        });

        odeslij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                parWejData = parWejData + "abc";
//                odbierzIntent.putExtra("parametrWyjsciowy", parWejData);
//                setResult(RESULT_OK, odbierzIntent);
//                finish();


                Intent go2Activity1Intent = new Intent(MainActivity2.this, MainActivity.class);
                go2Activity1Intent.putExtra("parametrWyjsciowy", wyj);

                startActivity(go2Activity1Intent);
            }
        });


    }
}