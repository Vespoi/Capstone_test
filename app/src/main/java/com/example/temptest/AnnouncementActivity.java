package com.example.temptest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AnnouncementActivity extends AppCompatActivity {

    ArrayList<String> titleList = new ArrayList<>();
    ArrayList<String> seqList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        Button btnReg = findViewById(R.id.buttonWrite);
        ListView listView = findViewById(R.id.announceList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AnnouncementActivity.this, AnnounceDetail.class);
                intent.putExtra("board_seq", seqList.get(i));
                startActivity(intent);
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnnouncementActivity.this, AnnounceRegister.class);
                startActivity(intent);
            }
        });

/*
        // 게시물 리스트를 읽어오는 함수
        class GetBoard extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                Log.d(TAG, "onPreExecute");
            }


            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.d(TAG, "onPostExecute, " + result);

// 배열들 초기화
                titleList.clear();
                seqList.clear();

                try {

// 결과물이 JSONArray 형태로 넘어오기 때문에 파싱
                    JSONArray jsonArray = new JSONArray(result);

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String title = jsonObject.optString("title");
                        String seq = jsonObject.optString("seq");

// title, seq 값을 변수로 받아서 배열에 추가
                        titleList.add(title);
                        seqList.add(seq);

                    }

// ListView 에서 사용할 arrayAdapter를 생성하고, ListView 와 연결
                    ArrayAdapter arrayAdapter = new ArrayAdapter<String>(AnnouncementActivity.this, android.R.layout.simple_list_item_1, titleList);
                    listView.setAdapter(arrayAdapter);

// arrayAdapter의 데이터가 변경되었을때 새로고침
                    arrayAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


            @Override
            protected String doInBackground(String... params) {
//
// String userid = params[0];
// String passwd = params[1];

                String server_url = "http://15.164.252.136/load_board.php";


                URL url;
                String response = "";
                try {
                    url = new URL(server_url);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("userid", "");
// .appendQueryParameter("passwd", passwd);
                    String query = builder.build().getEncodedQuery();

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(query);
                    writer.flush();
                    writer.close();
                    os.close();

                    conn.connect();
                    int responseCode=conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        String line;
                        BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        while ((line=br.readLine()) != null) {
                            response+=line;
                        }
                    }
                    else {
                        response="";

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return response;
            }
        }

        // onResume() 은 해당 액티비티가 화면에 나타날 때 호출됨
        @Override
        protected void onResume(){
            super.onResume();
// 해당 액티비티가 활성화 될 때, 게시물 리스트를 불러오는 함수를 호출
            GetBoard getBoard = new GetBoard();
            getBoard.execute();
        }

 */


        Button btnCloseAn = findViewById(R.id.buttonCloseAn);
        btnCloseAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}