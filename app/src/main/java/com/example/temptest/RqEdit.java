package com.example.temptest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RqEdit extends AppCompatActivity {

    String board_num;
    String Title;
    String Content;
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rq_edit);

        EditText title_et = (EditText) findViewById(R.id.editTitle);
        EditText content_et = (EditText) findViewById(R.id.editContent);
        Button btnReg = (Button) findViewById(R.id.buttonRegister);

        Intent intent=getIntent();
        board_num = intent.getStringExtra("position");
        Title = intent.getStringExtra("title");
        Content = intent.getStringExtra("content");

        title_et.setText(Title);
        content_et.setText(Content);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String rq_title = title_et.getText().toString();
                String rq_content = content_et.getText().toString();
                String date = getTime();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "예외 1", Toast.LENGTH_SHORT).show();
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                RqEditRequest rqEditRequest = new RqEditRequest(board_num, rq_title, rq_content, date, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(rqEditRequest);
            }
        });
    }
    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
}