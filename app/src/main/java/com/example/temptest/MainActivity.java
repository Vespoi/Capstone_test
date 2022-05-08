package com.example.temptest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    ArrayList<Marker> marker = new ArrayList<>();

    public static Context context_main;
    public String[] list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        NaverMapOptions startOptions = new NaverMapOptions()
                .camera(new CameraPosition(new LatLng(35.9071946, 128.8016816), 16));

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map_view);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance(startOptions);
            fm.beginTransaction().add(R.id.map_view, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setZoomControlEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setScaleBarEnabled(true);
        uiSettings.setLocationButtonEnabled(true);

    //   context_main = this;

        Animation animTransRight = AnimationUtils.loadAnimation(
                this,R.anim.translate_right);
        Animation animTransLeft = AnimationUtils.loadAnimation(
                this,R.anim.translate_left);
        Animation animTransUp = AnimationUtils.loadAnimation(
                this,R.anim.translate_up);
        Animation animTransDown = AnimationUtils.loadAnimation(
                this,R.anim.translate_down);

        //마커 생성
        for (int i = 0; i < 3; i++) {
            marker.add(new Marker());
        }

        //지도에 마커 표시
        marker.get(0).setPosition(new LatLng(35.9071946, 128.8016816));
        marker.get(0).setIcon(OverlayImage.fromResource(R.drawable.bin_red));

        marker.get(1).setPosition(new LatLng(35.9059172, 128.8014348));
        marker.get(1).setIcon(OverlayImage.fromResource(R.drawable.bin_green));

        marker.get(2).setPosition(new LatLng(35.9070643, 128.803012));
        marker.get(2).setIcon(OverlayImage.fromResource(R.drawable.bin_yellow));

        marker.get(0).setMap(naverMap);
        marker.get(1).setMap(naverMap);
        marker.get(2).setMap(naverMap);

        //마커 정보 표시 화면 띄우기
        View markerView1, markerView2, markerView3;
        markerView1 = findViewById(R.id.ViewM1);
        markerView2 = findViewById(R.id.ViewM2);
        markerView3 = findViewById(R.id.ViewM3);


        marker.get(0).setOnClickListener(overlay -> {
            markerView1.startAnimation(animTransUp);
            markerView1.setVisibility(View.VISIBLE);
            if (markerView2.getVisibility() == markerView1.getVisibility() ||
                    markerView3.getVisibility() == markerView1.getVisibility()) {
                markerView2.setVisibility(View.GONE);
                markerView3.setVisibility(View.GONE);
            }
            return true;
        });
        marker.get(1).setOnClickListener(overlay -> {
            markerView2.startAnimation(animTransUp);
            markerView2.setVisibility(View.VISIBLE);
            if (markerView1.getVisibility() == markerView2.getVisibility() ||
                    markerView3.getVisibility() == markerView2.getVisibility()) {
                markerView1.setVisibility(View.GONE);
                markerView3.setVisibility(View.GONE);
            }
            return true;
        });
        marker.get(2).setOnClickListener(overlay -> {
            markerView3.startAnimation(animTransUp);
            markerView3.setVisibility(View.VISIBLE);
            if (markerView1.getVisibility() == markerView3.getVisibility() ||
                    markerView2.getVisibility() == markerView3.getVisibility()) {
                markerView1.setVisibility(View.GONE);
                markerView2.setVisibility(View.GONE);
            }
            return true;
        });

        //메뉴 화면
        View menuView;
        menuView = findViewById(R.id.menuView);

        //쓰레기통 위치
        TextView binLoc1, binLoc2, binLoc3;
        binLoc1 = findViewById(R.id.binLocation1);
        binLoc2 = findViewById(R.id.binLocation2);
        binLoc3 = findViewById(R.id.binLocation3);

        //메뉴에 버튼들
        Button btnMenu = (Button) findViewById(R.id.buttonMenu);
        Button btnMenuConInfo = (Button) findViewById(R.id.buttonMenuContentMyinfo);
        Button btnMenuConRq = (Button) findViewById(R.id.buttonMenuContentRq);
        Button btnMenuConAn = (Button) findViewById(R.id.buttonMenuContentAnnouncement);
        Button btnMenuConSet = (Button) findViewById(R.id.buttonMenuContentSettings);
        Button btnMenuConCs = (Button) findViewById(R.id.buttonMenuContentCS);
        Button btnMenuConClose = (Button) findViewById(R.id.buttonMenuContentClose);

        //마커 세부정보 버튼들
        Button btnDt1=(Button) findViewById(R.id.binDetails1);
        Button btnDt2=(Button) findViewById(R.id.binDetails2);
        Button btnDt3=(Button) findViewById(R.id.binDetails3);


        //메뉴표시
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(menuView.getVisibility()==View.VISIBLE) {
                    menuView.startAnimation(animTransLeft);
                    menuView.setVisibility(View.GONE);
                }
                else{
                    menuView.startAnimation(animTransRight);
                    menuView.setVisibility(View.VISIBLE);
                }
            }
        });
        //메뉴 닫기
        btnMenuConClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuView.startAnimation(animTransLeft);
                menuView.setVisibility(View.GONE);
            }
        });

        //마커 정보 표시 했던거 닫는거
        Button btnBack1 = (Button) findViewById(R.id.buttonClose1);
        btnBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markerView1.startAnimation(animTransDown);
                markerView1.setVisibility(View.GONE);
            }
        });
        Button btnBack2 = (Button) findViewById(R.id.buttonClose2);
        btnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markerView2.startAnimation(animTransDown);
                markerView2.setVisibility(View.GONE);
            }
        });
        Button btnBack3 = (Button) findViewById(R.id.buttonClose3);
        btnBack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markerView3.startAnimation(animTransDown);
                markerView3.setVisibility(View.GONE);
            }
        });

        //마커 위치 텍스트로
        list = new String[]{binLoc1.getText().toString(), binLoc2.getText().toString(), binLoc3.getText().toString()};

        //검생창
        AutoCompleteTextView searchText = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        searchText.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list));

        //검색결과 누르면 이동하는 거
        searchText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (((TextView) view).getText().toString() == binLoc1.getText()) {
                    CameraUpdate movePostion = CameraUpdate.scrollTo(marker.get(0).getPosition());
                    naverMap.moveCamera(movePostion);
                    markerView1.startAnimation(animTransUp);
                    markerView1.setVisibility(View.VISIBLE);
                    if (markerView2.getVisibility() == markerView1.getVisibility() ||
                            markerView3.getVisibility() == markerView1.getVisibility()) {
                        markerView2.setVisibility(View.GONE);
                        markerView3.setVisibility(View.GONE);
                    }
                }
                if (((TextView) view).getText().toString() == binLoc2.getText()) {
                    CameraUpdate movePostion = CameraUpdate.scrollTo(marker.get(1).getPosition());
                    naverMap.moveCamera(movePostion);
                    markerView2.startAnimation(animTransUp);
                    markerView2.setVisibility(View.VISIBLE);
                    if (markerView1.getVisibility() == markerView2.getVisibility() ||
                            markerView3.getVisibility() == markerView2.getVisibility()) {
                        markerView1.setVisibility(View.GONE);
                        markerView3.setVisibility(View.GONE);
                    }
                }
                if (((TextView) view).getText().toString() == binLoc3.getText()) {
                    CameraUpdate movePostion = CameraUpdate.scrollTo(marker.get(2).getPosition());
                    naverMap.moveCamera(movePostion);
                    markerView3.startAnimation(animTransUp);
                    markerView3.setVisibility(View.VISIBLE);
                    if (markerView1.getVisibility() == markerView3.getVisibility() ||
                            markerView2.getVisibility() == markerView3.getVisibility()) {
                        markerView1.setVisibility(View.GONE);
                        markerView2.setVisibility(View.GONE);
                    }
                }

            }
        });


        //화면 이동, 데이터 전송
        btnMenuConInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyInfoActivity.class);
                startActivity(intent);
            }
        });
        btnMenuConRq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RqActivity.class);
                startActivity(intent);
            }
        });
        btnMenuConAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.temptest.AnnouncementActivity.class);
                startActivity(intent);
            }
        });
        btnMenuConSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.temptest.SettingsActivity.class);
                startActivity(intent);
            }
        });
        btnMenuConCs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.temptest.CSActivity.class);
                startActivity(intent);
            }
        });
        btnDt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.temptest.DetailsActivity.class);
                intent.putExtra("binLocation", list);
                intent.putExtra("binLoc",binLoc1.getText().toString());
                startActivity(intent);
            }
        });
        btnDt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.temptest.DetailsActivity.class);
                intent.putExtra("binLocation", list);
                intent.putExtra("binLoc",binLoc2.getText().toString());
                startActivity(intent);
            }
        });
        btnDt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.temptest.DetailsActivity.class);
                intent.putExtra("binLocation", list);
                intent.putExtra("binLoc",binLoc3.getText().toString());
                startActivity(intent);
            }
        });

    }
}



