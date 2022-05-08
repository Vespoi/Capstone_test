package com.example.temptest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText et_email, et_password;
    Button btn_login, btn_signup;
    Button btn_signup_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.emailInput);
        et_password = findViewById(R.id.passwordInput);
        btn_login = findViewById(R.id.loginButton);
        btn_signup = findViewById(R.id.signupButton);
        btn_signup_test = findViewById(R.id.signupButton_test);
        //로그인 버튼 이벤트
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                if (email.equals("email") || password.equals("password")) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "알맞은 이메일과 비밀번호를 입력해주세요",
                            Toast.LENGTH_SHORT).show();
                }
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
