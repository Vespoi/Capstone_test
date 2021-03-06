package com.example.temptest;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CsRegRequest extends StringRequest {
    // 서버 URL 설정 (PHP 파일 연동)
    final static private String URL = "http://10.0.2.2/TestThings/cap_test/csReg.php";

    private Map<String, String> map;

    public CsRegRequest(String id, String cs_title, String cs_content, String date, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("id",id);
        map.put("cs_title", cs_title);
        map.put("cs_content", cs_content);
        map.put("date",date);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
