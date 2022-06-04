package com.example.temptest;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CsRmv_Request extends StringRequest {
    // 서버 URL 설정 (PHP 파일 연동)
    final static private String URL = "http://10.0.2.2/TestThings/cap_test/csRmv.php";

    private Map<String, String> map;

    public CsRmv_Request(String board_num, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("cs_board_num",board_num);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
