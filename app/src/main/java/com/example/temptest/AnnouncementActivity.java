package com.example.temptest;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class AnnouncementActivity extends AppCompatActivity {
    private static String TAG = "phptest_AnnouncementActivity";
    private static final String TAG_JSON="phptest";
    private static final String TAG_USERID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_BOARD_NUM = "board_num";
    private static final String TAG_DATE = "date";
    private TextView mTextViewResult;

    private String authority;
    private String AUTHORITY_USER = "user";
    private String AUTHORITY_ADMIN = "admin";
    private String AUTHORITY_SUPER_ADMIN = "super_admin";

    ArrayList<HashMap<String, String>> annList;
    ListView listView;

    String mJsonString;
    String id;
    String board_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        mTextViewResult = (TextView)findViewById(R.id.mTextViewResult);

        annList = new ArrayList<>();

        Intent getIntent = getIntent();
        id=getIntent.getStringExtra("id");
        authority = getIntent.getStringExtra("authority");

        GetData task = new GetData();
        task.execute("http://10.0.2.2/TestThings/cap_test/annLoadBoard.php");

        listView = (ListView) findViewById(R.id.announceList);
        Button btnReg = findViewById(R.id.buttonWrite);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(authority.equals(AUTHORITY_USER)){
                    Toast.makeText(AnnouncementActivity.this,"????????? ????????????.",Toast.LENGTH_SHORT).show();
                }
                if(authority.equals(AUTHORITY_ADMIN) || authority.equals(AUTHORITY_SUPER_ADMIN)) {
                    Intent intent = new Intent(AnnouncementActivity.this, AnnounceRegister.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        });

        Button btnCloseAn = findViewById(R.id.buttonCloseAn);
        btnCloseAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button btnRefresh = findViewById(R.id.refresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();//????????? ??????
                overridePendingTransition(0, 0);//????????? ?????? ?????????
                Intent intent = getIntent(); //?????????
                startActivity(intent); //???????????? ??????
                overridePendingTransition(0, 0);//????????? ?????? ?????????
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AnnouncementActivity.this, AnnounceDetail.class);
                intent.putExtra("id",id);
                Object temp = (Object)adapterView.getAdapter().getItem(i);
                board_number = temp.toString();
                board_number = board_number.substring(37, 38);
                intent.putExtra("position",board_number);
               // Toast.makeText(AnnouncementActivity.this, board_number,Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }


    private class GetData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(AnnouncementActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);

            if (result == null){

                mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


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

    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString(TAG_USERID);
                String title = item.getString(TAG_TITLE);
                String board_num = item.getString(TAG_BOARD_NUM);
                String date = item.getString(TAG_DATE);

                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_USERID, id);
                hashMap.put(TAG_TITLE, title);
                hashMap.put(TAG_BOARD_NUM, board_num);
                hashMap.put(TAG_DATE, date);

                    annList.add(hashMap);
            }

            SimpleAdapter adapter = new SimpleAdapter(
                    AnnouncementActivity.this, annList, R.layout.ann_list_item,
                    new String[]{TAG_USERID, TAG_BOARD_NUM, TAG_TITLE, TAG_DATE},
                    new int[]{R.id.writer,R.id.board_num, R.id.title, R.id.date}
            );
            listView.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}