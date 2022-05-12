package com.example.temptest;

import java.util.Date;

public class announce_list_item {
     String title;
     String board_num;

    public announce_list_item(String annTitle, String annBoard_num) {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getBoard_num() {
        return board_num;
    }

    public void setBoard_num(String board_num) {
        this.board_num = board_num;
    }

    public announce_list_item(String title, Date write_date, String board_num) {
        this.title = title;
        this.board_num = board_num;
    }
}
