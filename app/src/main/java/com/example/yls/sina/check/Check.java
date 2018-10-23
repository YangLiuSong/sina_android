package com.example.yls.sina.check;

public class Check {
    private String month;
    private int has_tag;
    private int no_tag;
    private int all_count;

    public Check(){super();}

    public Check(String month, int has_tag, int no_tag, int all_count) {
        super();
        this.month = month;
        this.has_tag = has_tag;
        this.no_tag = no_tag;
        this.all_count = all_count;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getHas_tag() {
        return has_tag;
    }

    public void setHas_tag(int has_tag) {
        this.has_tag = has_tag;
    }

    public int getNo_tag() {
        return no_tag;
    }

    public void setNo_tag(int no_tag) {
        this.no_tag = no_tag;
    }

    public int getAll_count() {
        return all_count;
    }

    public void setAll_count(int all_count) {
        this.all_count = all_count;
    }
}
