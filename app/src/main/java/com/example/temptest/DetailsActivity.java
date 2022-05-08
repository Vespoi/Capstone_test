package com.example.temptest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

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


        BarChart barChart = findViewById(R.id.barChart);

        //샘플 데이터
        ArrayList<BarEntry> visitors = new ArrayList<>();
        visitors.add(new BarEntry(2014, 420));
        visitors.add(new BarEntry(2015, 450));
        visitors.add(new BarEntry(2016, 520));
        visitors.add(new BarEntry(2017, 620));
        visitors.add(new BarEntry(2018, 540));
        visitors.add(new BarEntry(2019, 720));
        visitors.add(new BarEntry(2020, 920));

        BarDataSet barDataSet = new BarDataSet(visitors, "Visitors");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Bar Chart Example");
        barChart.animateY(2000);


        Button btnCloseDt = findViewById(R.id.buttonCloseDetails);
        btnCloseDt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}