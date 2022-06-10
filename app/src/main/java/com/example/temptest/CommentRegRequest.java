package com.example.temptest;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CommentRegRequest extends StringRequest {
    // 서버 URL 설정 (PHP 파일 연동)
    final static private String URL = "http://10.0.2.2/TestThings/cap_test/commentReg.php";

    private Map<String, String> map;

    public CommentRegRequest(String baord_num_check, String board_name, String comment_content, String date, String id, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("board_num_check",baord_num_check);
        map.put("board_name", board_name);
        map.put("comment_content", comment_content);
        map.put("date",date);
        map.put("id",id);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
