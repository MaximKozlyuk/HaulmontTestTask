package com.haulmont.testtask.view.editforms;

import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.domain.GenericObject;
import com.haulmont.testtask.domain.Patient;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.function.Consumer;

public class PatientForm extends HumanForm {

    private TextField phoneNum = new TextField("Phone number");

    private PatientDao dao = PatientDao.get();
    private Patient pojo;

    private Consumer<Object> updateList;

    public PatientForm(Consumer<Object> updateList) {
        super();
        this.updateList = updateList;

        getSave().addClickListener(e -> save());
        getDelete().addClickListener(e -> delete());

        RegexpValidator phoneValidator = new RegexpValidator(
                "^\\+(?:[0-9]•?){6,14}[0-9]$",
                "wrong phone number format");
        phoneNum.addValidator(phoneValidator);

        addComponents(getName(), getSurname(), getMiddleName(),
                phoneNum, getButtons());

    }

    public void setPojo(GenericObject pojo) {
        this.pojo = (Patient) pojo;
        BeanFieldGroup.bindFieldsUnbuffered(pojo, this);

        getDelete().setVisible(dao.existsById(pojo.getId()));
        setVisible(true);
        getName().selectAll();
    }

    private void save() {
        try {
            phoneNum.validate();
            super.validate();
        } catch (Validator.InvalidValueException exp) {
            Notification.show(exp.getMessage());
            return;
        }
        dao.save(pojo);
        updateList.accept(null);
        setVisible(false);
    }

    private void delete() {
        try {
            dao.delete(pojo);
        } catch (SQLIntegrityConstraintViolationException e) {
            Notification.show("cant delete patient with recipe");
            return;
        }
        updateList.accept(null);
        setVisible(false);
    }

}
