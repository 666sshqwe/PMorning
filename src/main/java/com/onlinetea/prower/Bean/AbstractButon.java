package com.onlinetea.prower.Bean;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstractButon {
    private String name;

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public AbstractButon(String name) {
        this.name = name;
    }
}
