package com.haulmont.testtask.view.editforms;

import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.domain.Doctor;
import com.haulmont.testtask.domain.GenericObject;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.function.Consumer;

public class DoctorForm extends HumanForm {

    private TextField specialization = new TextField("Specialization");

    private DoctorDao dao = DoctorDao.get();
    private Doctor pojo;

    private Consumer<Object> updateList;

    public DoctorForm (Consumer<Object> updateList) {
        super();
        this.updateList = updateList;

        getSave().addClickListener(e -> save());
        getDelete().addClickListener(e -> delete());

        specialization.setMaxLength(256);

        addComponents(getName(),getSurname(),getMiddleName(),
                specialization, getButtons());
    }

    public void setPojo (GenericObject pojo) {
        this.pojo = (Doctor) pojo;
        BeanFieldGroup.bindFieldsUnbuffered(pojo,this);

        getDelete().setVisible(dao.existsById(pojo.getId()));
        setVisible(true);
        getName().selectAll();
    }

    private void save () {
        try {
            super.validate();
        } catch (Validator.InvalidValueException exp) {
            Notification.show(exp.getMessage());
            return;
        }
        dao.save(pojo);
        updateList.accept(null);
        setVisible(false);
    }

    private void delete () {
        try {
            dao.delete(pojo);
        } catch (SQLIntegrityConstraintViolationException e) {
            Notification.show("cant delete doctor with recipes");
            return;
        }
        updateList.accept(null);
        setVisible(false);
    }

}
