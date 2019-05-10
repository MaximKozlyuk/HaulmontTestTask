package com.haulmont.testtask.frontend;

import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.domain.Patient;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

public class PatientCreator extends HorizontalLayout {

    private Patient patientData = new Patient("nameTest","surnameTest");

    private TextField nameField;
    private TextField surnameField;
    private TextField midNameField;
    private TextField phoneField;

    private PatientDao dao = PatientDao.get();

    private List<AbstractTextField> fields = new ArrayList<>();

    {
        nameField = new TextField();
        surnameField = new TextField();
        midNameField = new TextField();
        phoneField = new TextField();
        fields.add(nameField);
        fields.add(surnameField);
        fields.add(midNameField);
        fields.add(phoneField);
    }

    public PatientCreator() {
        setWidth(100,Unit.PERCENTAGE);
        setHeightUndefined();
        Button createButton = new Button("Create");
        addComponent(createButton);

        BeanFieldGroup<Patient> beanFieldGroup = new BeanFieldGroup<>(Patient.class);
        beanFieldGroup.setItemDataSource(patientData);

        PropertysetItem propertysetItem = new PropertysetItem();
        ObjectProperty<String> nameProperty = new ObjectProperty<>(patientData.getName());
        propertysetItem.addItemProperty("name", nameProperty);
        propertysetItem.addItemProperty("surname", new ObjectProperty<>(patientData.getSurname()));

        FieldGroup fieldGroup = new FieldGroup(propertysetItem);
        fieldGroup.bind(nameField, "name");
        fieldGroup.bind(surnameField, "surname");

        fields.forEach(this::addComponent);
        fields.forEach(field ->
                field.addTextChangeListener(event -> System.out.println(event.getText()))
        );

        nameField.addTextChangeListener(event -> {nameProperty.setValue(event.getText());});

        createButton.addClickListener(event -> {
            fields.forEach(AbstractField::validate);
            dao.save(patientData);
        });

    }

}
