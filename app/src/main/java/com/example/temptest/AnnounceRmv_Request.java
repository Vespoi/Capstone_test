package com.example.temptest;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AnnounceRmv_Request extends StringRequest {
    final static private String URL = "http://10.0.2.2/TestThings/cap_test/annRmv.php";

    private Map<String, String> map;

    public AnnounceRmv_Request(String board_num, Response.Listener<String> listener)
    {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("board_num",board_num);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
