package com.materiotech.atc.model;

public class Home {

    private String name;
    private String id;

    public Home(String ids, String names){
        setId(ids);
        setName(names);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
