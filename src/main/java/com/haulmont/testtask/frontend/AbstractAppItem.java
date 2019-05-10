package com.haulmont.testtask.frontend;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import lombok.Getter;

@Getter
abstract class AbstractAppItem extends HorizontalLayout {

    private long number;
    private Label numberLabel;

    public AbstractAppItem (long number) {
        this.number = number;
        setWidth(100,Unit.PERCENTAGE);
        setHeightUndefined();
        numberLabel = new Label(Long.toString(number));
        numberLabel.addStyleName("item-number");
        addComponent(numberLabel);
    }

}
