package com.onlinetea.prower.Bean;

public class ClickBotton extends AbstractButon {

    private String type = "click";
    private String key = "123";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ClickBotton(String name, String key) {
        super(name);
        this.key = key;
    }
}
