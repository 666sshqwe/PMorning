package com.onlinetea.prower.Bean;


import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Button {

    private List<AbstractButon> button = new ArrayList<>();

    public List<AbstractButon> getButton() {
        return button;
    }

    public void setButton(List<AbstractButon> button) {
        this.button = button;
    }
}
