package com.example.temptest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText et_id, et_pw;
    Button btn_login, btn_signup;
    Button btn_signup_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = findViewById(R.id.idInput);
        et_pw = findViewById(R.id.pwInput);
        btn_login = findViewById(R.id.loginButton);
        btn_signup = findViewById(R.id.signupButton);
        btn_signup_test = findViewById(R.id.signupButton_test);

        //로그인 버튼 이벤트(초기)
        /*btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = et_id.getText().toString();
                String pw = et_pw.getText().toString();
                if (id.equals("id") || pw.equals("pw")) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "알맞은 아이디와 비밀번호를 입력해주세요",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
         */

        //로그인 버튼 이벤트(업데이트)
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = et_id.getText().toString();
                String pw = et_pw.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success)
                            {
                                String msg = jsonObject.getString("id");
                                Toast.makeText(getApplicationContext(), "로그인 성공. ID :" + msg, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("id", id);
                                startActivity(intent);
                            }

                            else
                            {
                                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        catch (JSONException e)
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

                LoginRequestActivity loginRequestActivity = new LoginRequestActivity(id, pw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(loginRequestActivity);
            }
        });


        //회원가입 버튼 이벤트
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        //비회원(테스트) 버튼 이벤트
        btn_signup_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
