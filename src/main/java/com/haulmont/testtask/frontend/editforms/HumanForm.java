package com.haulmont.testtask.frontend.editforms;

import com.vaadin.data.Validator;
import com.vaadin.ui.TextField;
import lombok.Getter;

@Getter
class HumanForm extends FormButtons {

    private TextField name = new TextField("Name");
    private TextField surname = new TextField("Surname");
    private TextField middleName = new TextField("Middle name");

    HumanForm () {
        super();

        name.setMaxLength(64);
        surname.setMaxLength(64);
        middleName.setMaxLength(64);

        name.setNullSettingAllowed(false);
        surname.setNullSettingAllowed(false);

        Validator nameValidator = event -> {
            String str = (String) event;
            str = str.replaceAll("\\s+","");
            if (str.length() < 1) throw new Validator.InvalidValueException("empty name or surname");
        };

        name.addValidator(nameValidator);
        surname.addValidator(nameValidator);

    }

    void validate () throws Validator.InvalidValueException {
        name.validate();
        surname.validate();
    }

}
