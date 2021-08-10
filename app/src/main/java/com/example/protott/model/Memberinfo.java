package com.example.protott.model;

public class Memberinfo {

    private String mb_name;
    private String mb_phonenum;
    private String mb_date;
    private String mb_address;



    public Memberinfo(String mb_name, String mb_phonenum, String mb_date, String mb_address) {
        this.mb_name = mb_name;
        this.mb_phonenum = mb_phonenum;
        this.mb_date = mb_date;
        this.mb_address = mb_address;
    }

    public String getMb_name() {
        return this.mb_name;
    }

    public String getMb_phonenum() {
        return this.mb_phonenum;
    }

    public String getMb_date() {
        return this.mb_date;
    }

    public String getMb_address() {
        return this.mb_address;
    }
//---------위 겟  밑셋
    public void setMb_name(String mb_name) {
        this.mb_name = mb_name;
    }

    public void setMb_phonenum(String mb_phonenum) {
        this.mb_phonenum = mb_phonenum;
    }

    public void setMb_date(String mb_date) {
        this.mb_date = mb_date;
    }

    public void setMb_address(String mb_address) {
        this.mb_address = mb_address;
    }
}

