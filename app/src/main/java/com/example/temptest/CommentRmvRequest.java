package com.example.temptest;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CommentRmvRequest extends StringRequest {
    final static private String URL = "http://10.0.2.2/TestThings/cap_test/commentRmv.php";

    private Map<String, String> map;

    public CommentRmvRequest(String board_num_check, String board_name, String date, Response.Listener<String> listener)
    {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("board_num_check",board_num_check);
        map.put("board_name",board_name);
        map.put("date",date);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
