package com.example.temptest;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CsRmv_Request extends StringRequest {
    final static private String URL = "http://10.0.2.2/TestThings/cap_test/csRmv.php";

    private Map<String, String> map;

    public CsRmv_Request(String cs_board_num, Response.Listener<String> listener)
    {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("cs_board_num",cs_board_num);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
