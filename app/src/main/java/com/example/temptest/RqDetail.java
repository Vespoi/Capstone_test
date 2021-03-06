package com.example.temptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class RqDetail extends AppCompatActivity {
    private static String TAG = "phptest_AnnouncementDetailActivity";
    private static final String TAG_JSON="phptest";
    private static final String TAG_USERID = "id";
    private static final String TAG_TITLE = "rq_title";
    private static final String TAG_CONTENT = "rq_content";
    private static final String TAG_DATE = "date";
    private static final String TAG_COMMENT_CONTENT = "comment_content";
    final static private String URL = "http://10.0.2.2/TestThings/cap_test/rqLoadDetail.php";
    final static private String COMMENT_URL = "http://10.0.2.2/TestThings/cap_test/commentLoad.php";
    String mJsonString;

    TextView title_tv, content_tv, date_tv;
    LinearLayout comment_layout;
    LayoutInflater layoutInflater;
    View view;
    Context context;
    EditText comment_et;
    Button reg_button, btnCmtRef;
    String board_num;
    String board_name;
    String id;

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rq_detail);
        context = this;

        Intent intent = getIntent();
        board_num = intent.getStringExtra("position");
        id = intent.getStringExtra("id");
        board_name = "rqBoard";

        title_tv = findViewById(R.id.textTitle);
        content_tv = findViewById(R.id.textContent);
        date_tv = findViewById(R.id.textDate);

        comment_layout = findViewById(R.id.layoutComment);
        comment_et = findViewById(R.id.comment_et);
        reg_button = findViewById(R.id.reg_button);
        layoutInflater = LayoutInflater.from(this);

        btnCmtRef = findViewById(R.id.buttonCommentRefresh);

        btnCmtRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();//????????? ??????
                overridePendingTransition(0, 0);//????????? ?????? ?????????
                Intent intent = getIntent(); //?????????
                startActivity(intent); //???????????? ??????
                overridePendingTransition(0, 0);//????????? ?????? ?????????
            }
        });

        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String board_name ="rqBoard";
                String comment_content = comment_et.getText().toString();
                String date = getTime();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success)
                            {
                                Toast.makeText(getApplicationContext(), "??????", Toast.LENGTH_SHORT).show();
                                finish();//????????? ??????
                                overridePendingTransition(0, 0);//????????? ?????? ?????????
                                Intent intent = getIntent(); //?????????
                                startActivity(intent); //???????????? ??????
                                overridePendingTransition(0, 0);//????????? ?????? ?????????
                            }

                            else
                            {
                                Toast.makeText(getApplicationContext(), "??????", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        catch(JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "?????? 1", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                CommentRegRequest commentRegRequest = new CommentRegRequest(board_num, board_name, comment_content, date, id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(commentRegRequest);
            }
        });

        GetCommentData ctask = new GetCommentData();
        ctask.execute(COMMENT_URL,board_num,board_name);

    }

    @Override
    public void onResume(){
        super.onResume();
        GetData task = new GetData();
        task.execute(URL,board_num);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int curId = item.getItemId();
        switch(curId) {
            case R.id.action_change:
                //intent??? ?????? ???????????? ??????, ????????? ?????? ?????? ?????? ??? ?????? ??????
                Toast.makeText(this, "????????? ?????????", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RqDetail.this, RqEdit.class);
                Intent getIntent = getIntent();
                intent.putExtra("position",board_num);
                intent.putExtra("title",title_tv.getText());
                intent.putExtra("content",content_tv.getText());
                startActivity(intent);
                break;
            case R.id.action_delete:
                //??????, ?????? ?????? ??????, ?????? ????????? ?????? ??????
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success)
                            {
                                Toast.makeText(getApplicationContext(), "??????", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            else
                            {
                                Toast.makeText(getApplicationContext(), "??????", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        catch(JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "?????? 1", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                RqRmv_Request rqRmv_Request = new RqRmv_Request(board_num, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(rqRmv_Request);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(RqDetail.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response  - " + result);

            if (result == null){
                content_tv.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String board_num = params[1];
            String postParameters = "board_num=" + board_num;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String title = item.getString(TAG_TITLE);
                String content = item.getString(TAG_CONTENT);
                String date = item.getString(TAG_DATE);

                title_tv.setText(title);
                content_tv.setText(content);
                date_tv.setText(date);
            }

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    private class GetCommentData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(RqDetail.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response  - " + result);

            if (result == null){
                Toast.makeText(RqDetail.this, errorString, Toast.LENGTH_SHORT).show();
            }
            else {
                mJsonString = result;
                comment_layout.removeAllViews();
                showCommentResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String board_num_check = params[1];
            String board_name = params[2];

            String postParameters = "board_num_check=" + board_num_check + "&board_name="+ board_name;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void showCommentResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String comment_content = item.getString(TAG_COMMENT_CONTENT);
                String date = item.getString(TAG_DATE);
                String id = item.getString(TAG_USERID);


                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_COMMENT_CONTENT, comment_content);
                hashMap.put(TAG_DATE, date);
                hashMap.put(TAG_USERID, id);

                view = layoutInflater.inflate(R.layout.custom_comment, null, false);
                TextView useridText = view.findViewById(R.id.writer);
                useridText.setText(id);

                TextView dateText = view.findViewById(R.id.date);
                dateText.setText(date);

                TextView contentText  = view.findViewById(R.id.commentContent);
                contentText.setText(comment_content);

                Button cmtEdit = view.findViewById(R.id.buttonCommentEdit);
                Button cmtDel = view.findViewById(R.id.buttonCommentDelete);

                cmtEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(RqDetail.this, CommentEdit.class);
                        intent.putExtra("id",id);
                        intent.putExtra("date",date);
                        intent.putExtra("content",comment_content);
                        intent.putExtra("board_num",board_num);
                        intent.putExtra("board_name",board_name);
                        startActivity(intent);
                    }
                });
                cmtDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");

                                    if (success)
                                    {
                                        Toast.makeText(getApplicationContext(), "??????", Toast.LENGTH_SHORT).show();
                                        finish();
                                        overridePendingTransition(0, 0);//????????? ?????? ?????????
                                        Intent intent = getIntent(); //?????????
                                        startActivity(intent); //???????????? ??????
                                        overridePendingTransition(0, 0);//????????? ?????? ?????????
                                    }

                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "??????", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }

                                catch(JSONException e)
                                {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "?????? 1", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        };

                        CommentRmvRequest commentRmvRequest = new CommentRmvRequest(board_num, board_name, date, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        queue.add(commentRmvRequest);
                    }
                });

                comment_layout.addView(view);
            }

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
}