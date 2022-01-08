package com.example.tm3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button go2ActivityButton;
    private Button button4;
    private TextView num1;
    private TextView num2;
    private Intent odbierzIntent;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            num2.setText(data.getStringExtra("parametrWyjsciowy"));

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        go2ActivityButton = findViewById(R.id.button);
        num1 = findViewById(R.id.et1);
        num2 = findViewById(R.id.textView2);
        button4 = findViewById(R.id.button4);

        go2ActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go2ActivityIntent = new Intent(MainActivity.this, MainActivity2.class);
                go2ActivityIntent.putExtra("parametrWejsciowy", num1.getText().toString());
                startActivity(go2ActivityIntent);
                //startActivity(go2ActivityIntent, "1234");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                odbierzIntent = getIntent();
                num2.setText(odbierzIntent.getStringExtra("parametrWyjsciowy"));
                //parWejData = parWejData.parseInt(parWej.getText().toString());

            }
        });


    }
}