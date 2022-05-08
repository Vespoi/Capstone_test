package com.example.temptest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();

        String SingleBinLoc=intent.getStringExtra("binLoc");

        String binLoc="";
        TextView binLocDt = findViewById(R.id.textBinLocation);

        if (SingleBinLoc != null) binLoc = SingleBinLoc;
        if (SingleBinLoc == null) binLoc = "선택된 쓰레기통이 없습니다.";
        binLocDt.setText(binLoc);


        Button btnCloseDt = findViewById(R.id.buttonCloseDetails);
        btnCloseDt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}