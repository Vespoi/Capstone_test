package com.example.temptest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button signupButton = (Button) findViewById(R.id.signupButton);
        EditText signup_idInput = (EditText) findViewById(R.id.signup_idInput);
        EditText signup_pwInput = (EditText) findViewById(R.id.signup_pwInput);
        //EditText signup_password_checkInput = (EditText) findViewById(R.id.signup_password_checkInput);

        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String id = signup_idInput.getText().toString();
                String pw = signup_pwInput.getText().toString();

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
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
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

                SignupRequestActivity signupRequestActivity = new SignupRequestActivity(id, pw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(signupRequestActivity);
            }
        });
    }
}
