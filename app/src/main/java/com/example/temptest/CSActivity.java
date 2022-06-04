package com.example.temptest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class CSActivity extends AppCompatActivity {
    private static String TAG = "phptest_CustomerServiceActivity";
    private static final String TAG_JSON="phptest";
    private static final String TAG_USERID = "id";
    private static final String TAG_TITLE = "cs_title";
    private static final String TAG_BOARD_NUM = "cs_board_num";
    private static final String TAG_DATE = "date";
    final static private String URL = "http://10.0.2.2/TestThings/cap_test/csLoadBoard.php";
    private  SimpleAdapter adapter;
    private TextView mTextViewResult;

    ArrayList<HashMap<String, String>> csList;
    ListView listView;

    String mJsonString;
    String id;
    String board_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csactivity);

        mTextViewResult = (TextView)findViewById(R.id.phpTestText);

        csList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.CsList);
        Button btnReg = findViewById(R.id.buttonReg);

        GetData task = new GetData();
        task.execute(URL);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CSActivity.this, CsRegister.class);
                Intent getIntent = getIntent();
                id=getIntent.getStringExtra("id");
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        Button btnCloseAn = findViewById(R.id.buttonCloseCs);
        btnCloseAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CSActivity.this, CsDetail.class);
                Intent getIntent = getIntent();
                id=getIntent.getStringExtra("id");
                intent.putExtra("id",id);
                Object temp = (Object)adapterView.getAdapter().getItem(i);
                board_number = temp.toString();
                board_number = board_number.substring(40, 41);
                //Toast.makeText(CSActivity.this, board_number, Toast.LENGTH_SHORT).show();
                //Toast.makeText(CSActivity.this, temp.toString(), Toast.LENGTH_SHORT).show();
                intent.putExtra("position",board_number);
                startActivity(intent);
            }
        });
    }
    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(CSActivity.this,
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
                String cs_title = item.getString(TAG_TITLE);
                String cs_board_num = item.getString(TAG_BOARD_NUM);
                String date = item.getString(TAG_DATE);

                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_USERID, id);
                hashMap.put(TAG_TITLE, cs_title);
                hashMap.put(TAG_BOARD_NUM, cs_board_num);
                hashMap.put(TAG_DATE, date);

                csList.add(hashMap);
            }

            adapter = new SimpleAdapter(
                    CSActivity.this, csList, R.layout.cs_list_item,
                    new String[]{TAG_USERID, TAG_BOARD_NUM, TAG_TITLE, TAG_DATE},
                    new int[]{R.id.writer,R.id.board_num, R.id.title, R.id.date}
            );
            listView.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
}