package com.example.temptest;

import androidx.appcompat.app.AppCompatActivity;

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

public class CsRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cs_register);

        EditText title_et = (EditText) findViewById(R.id.editTitle);
        EditText content_et = (EditText) findViewById(R.id.editContent);
        Button btnReg = (Button) findViewById(R.id.buttonRegister);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cs_title = title_et.getText().toString();
                String cs_content = content_et.getText().toString();
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

                CsRegRequest csRegRequest = new CsRegRequest(cs_title, cs_content, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(csRegRequest);

            }
        });
    }
}