package com.example.temptest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentEdit extends AppCompatActivity {

    String id, date, Content, board_num, board_name;
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_edit);

        TextView writerTv = (TextView) findViewById(R.id.writer);
        TextView dateTv = (TextView) findViewById(R.id.date);
        EditText cmtContent = (EditText) findViewById(R.id.commentContent);

        Intent intent=getIntent();
        id = intent.getStringExtra("id");
        date = intent.getStringExtra("date");
        Content = intent.getStringExtra("content");
        board_num = intent.getStringExtra("board_num");
        board_name = intent.getStringExtra("board_name");

        writerTv.setText(id);
        dateTv.setText(date);
        cmtContent.setText(Content);

        Button btnCmtEditDone =findViewById(R.id.buttonCommentEditDone);
        btnCmtEditDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String content = cmtContent.getText().toString();

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

                CommentEditRequest commentEditRequest = new CommentEditRequest(board_num, board_name, content, date, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(commentEditRequest);

                finish();
            }
        });
    }
    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
}
