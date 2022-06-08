package com.example.temptest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
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
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class AnnounceDetail extends AppCompatActivity {
    private static String TAG = "phptest_AnnouncementDetailActivity";
    private static final String TAG_JSON="phptest";
    private static final String TAG_TITLE = "title";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_DATE = "date";
    final static private String URL = "http://10.0.2.2/TestThings/cap_test/annLoadDetail.php";
    String mJsonString;

    TextView title_tv, content_tv, date_tv;
    LinearLayout comment_layout;
    EditText comment_et;
    Button reg_button;
    String board_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce_detail);

        Intent intent = getIntent();
        board_num = intent.getStringExtra("position");

        title_tv = findViewById(R.id.textTitle);
        content_tv = findViewById(R.id.textContent);
        date_tv = findViewById(R.id.textDate);

        comment_layout = findViewById(R.id.layoutComment);
        comment_et = findViewById(R.id.comment_et);
        reg_button = findViewById(R.id.reg_button);

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
                //intent로 수정 페이지로 이동, 사용자 제목 내용 날짜 글 번호 전달
                Toast.makeText(this, "개발중 입니다", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AnnounceDetail.this, AnnounceEdit.class);
                Intent getIntent = getIntent();
                intent.putExtra("position",board_num);
                intent.putExtra("title",title_tv.getText());
                intent.putExtra("content",content_tv.getText());
                startActivity(intent);
                break;
            case R.id.action_delete:
                //삭제, 확인 취소 선택, 확인 선택시 삭제 진행
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success)
                            {
                                Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            else
                            {
                                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        catch(JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "예외 1", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                AnnounceRmv_Request announceRmv_Request = new AnnounceRmv_Request(board_num, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(announceRmv_Request);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class GetData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(AnnounceDetail.this,
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
}
