package com.haulmont.testtask.frontend.editforms;

import com.vaadin.ui.TextField;
import lombok.Getter;

@Getter
class HumanForm extends FormButtons {

    private TextField name = new TextField("Name");
    private TextField surname = new TextField("Surname");
    private TextField middleName = new TextField("Middle name");

    HumanForm () {
        super();
    }

}
