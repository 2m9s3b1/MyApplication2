package com.example.lg.myapplication;

class RecyclerItem {
    public String title, date, start, fin;

    public RecyclerItem(String title, String date, String start, String fin) {
        this.title = title;
        this.date = date;
        this.start = start;
        this.fin = fin;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getStart() {
        return start;
    }

    public String getFin() {
        return fin;
    }
}