package com.onlinetea.prower.Bean;

public class ViewButton extends AbstractButon {

    private String url ;
    private String type = "view";


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ViewButton(String name, String url) {
        super(name);
        this.url = url;
    }
}
