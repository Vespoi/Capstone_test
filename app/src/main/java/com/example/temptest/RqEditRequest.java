package com.example.temptest;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RqEditRequest extends StringRequest {
    final static private String URL = "http://10.0.2.2/TestThings/cap_test/rqEdit.php";

    private Map<String, String> map;

    public RqEditRequest(String rq_board_num,String rq_title, String rq_content, String date, Response.Listener<String> listener)
    {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("rq_board_num",rq_board_num);
        map.put("rq_title", rq_title);
        map.put("rq_content", rq_content);
        map.put("date",date);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
