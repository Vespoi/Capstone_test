package com.example.temptest;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RqRegRequest extends StringRequest {
    // 서버 URL 설정 (PHP 파일 연동)
    final static private String URL = "http://10.0.2.2/TestThings/cap_test/rqReg.php";

    private Map<String, String> map;

    public RqRegRequest(String id, String rq_title, String rq_content, String date, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("id",id);
        map.put("rq_title", rq_title);
        map.put("rq_content", rq_content);
        map.put("date",date);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
