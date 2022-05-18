package com.example.temptest;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AnnRegRequestActivity extends StringRequest {
    // 서버 URL 설정 (PHP 파일 연동)
    final static private String URL = "http://13.125.205.121/annReg.php";

    private Map<String, String> map;

    public AnnRegRequestActivity(String title, String content, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("title", title);
        map.put("content", content);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
