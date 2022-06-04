package com.example.temptest;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CsEditRequest extends StringRequest {
    final static private String URL = "http://10.0.2.2/TestThings/cap_test/csEdit.php";

    private Map<String, String> map;

    public CsEditRequest(String cs_board_num,String cs_title, String cs_content, String date, Response.Listener<String> listener)
    {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("cs_board_num", cs_board_num);
        map.put("cs_title", cs_title);
        map.put("cs_content", cs_content);
        map.put("date",date);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
