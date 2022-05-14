package com.example.temptest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class AnnounceDetail extends AppCompatActivity {

    TextView title_tv, content_tv, date_tv;
    LinearLayout comment_layout;
    EditText comment_et;
    Button reg_button;
    int board_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce_detail);
        Intent intent = getIntent();
        board_num = intent.getIntExtra("position",0);

        title_tv = findViewById(R.id.textTitle);
        content_tv = findViewById(R.id.textContent);
        date_tv = findViewById(R.id.textDate);

        comment_layout = findViewById(R.id.layoutComment);
        comment_et = findViewById(R.id.comment_et);
        reg_button = findViewById(R.id.reg_button);


        content_tv.setText(board_num+"번 게시글 입니다."+"\n"+"추가 내용은 개발중 입니다.");
    }
    public void clickLoad(View view) {

        String serverUrl="http://10.0.2.2/TestThings/cap_test/annLoadDetail.php";

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.POST, serverUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {


                    JSONObject jsonObject = response.getJSONObject(0);

                    String title = jsonObject.getString("title");
                    String content = jsonObject.getString("content");
                    String date = jsonObject.getString("date");
                    title_tv.setText(title);
                    content_tv.setText(content);
                    date_tv.setText(date);

                } catch (JSONException e) {e.printStackTrace();}

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AnnounceDetail.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }
}
