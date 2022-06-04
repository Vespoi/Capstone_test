package com.example.temptest;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AnnounceEditRequest extends StringRequest {
    final static private String URL = "http://10.0.2.2/TestThings/cap_test/annEdit.php";

    private Map<String, String> map;

    public AnnounceEditRequest(String board_num,String title, String content, String date, Response.Listener<String> listener)
    {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("board_num",board_num);
        map.put("title", title);
        map.put("content", content);
        map.put("date",date);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
