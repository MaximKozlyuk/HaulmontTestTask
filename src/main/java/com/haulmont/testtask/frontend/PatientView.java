package com.haulmont.testtask.frontend;

import com.haulmont.testtask.domain.Patient;
import com.vaadin.data.Validator;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.*;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
class PatientView extends AbstractAppItem {

    private Patient patient;

    private TextField nameField;
    private TextField surnameField;
    private TextField midNameField;
    private TextField phoneField;

    public PatientView(Patient patient, long number) {
        super(number);
        this.patient = patient;
        setStyleName("patient-view");
        setDefaultComponentAlignment(Alignment.TOP_CENTER);

        nameField = new TextField();
        surnameField = new TextField();
        midNameField = new TextField();
        phoneField = new TextField();

        nameField.setValue(patient.getName());
        ObjectProperty nameProp = new ObjectProperty<>(patient.getName(), String.class);
        nameField.setPropertyDataSource(nameProp);
        nameField.setInputPrompt("name");
        nameField.setMaxLength(64);
        nameField.setNullRepresentation("noname");
        nameField.addTextChangeListener(event -> {
            System.out.println(event.getText());
            System.out.println(nameProp.getValue());
        });
        nameField.addValidator((Validator) event -> {});

        surnameField.setValue(patient.getSurname());
        surnameField.setInputPrompt("surname");
        surnameField.addTextChangeListener(event -> {});

        midNameField.setValue(patient.getMiddleName());
        midNameField.setInputPrompt("middle name");
        midNameField.addTextChangeListener(event -> {});

        phoneField.setValue(patient.getPhoneNum());
        phoneField.setInputPrompt("phone number");
        phoneField.addTextChangeListener(event -> {});
        phoneField.setNullSettingAllowed(true);
        phoneField.setNullRepresentation("null");
        phoneField.addValidator((Validator) value -> {
            String str = (String) value;
            Matcher m = Pattern.compile("^\\+(?:[0-9]•?){6,14}[0-9]$").matcher(str);
            if (!m.matches() && !str.equals(Patient.getDEFAULT_PHONE_NUMBER()))
                throw new Validator.InvalidValueException("error!");
        });
        phoneField.addTextChangeListener(event -> {
            System.out.println(event.getText());
        });

        addComponents(nameField, surnameField, midNameField, phoneField);
    }

}
