package com.haulmont.testtask.frontend.editforms;

import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.domain.GenericObject;
import com.haulmont.testtask.domain.Patient;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.TextField;

import java.util.function.Consumer;

public class PatientForm extends HumanForm implements EditForm {

    private TextField phoneNum = new TextField("Phone number");
    //private NativeSelect
    //private PopupDateField

    private PatientDao dao = PatientDao.get();
    private Patient pojo;

    private Consumer<Object> updateList;

    public PatientForm (Consumer<Object> updateList) {
        super();
        this.updateList = updateList;

        getSave().addClickListener(e -> save());
        getDelete().addClickListener(e -> delete());

        addComponents(getName(),getSurname(),getMiddleName(),phoneNum, getButtons());

    }

    @Override
    public void setPojo(GenericObject pojo) {
        this.pojo = (Patient) pojo;
        BeanFieldGroup.bindFieldsUnbuffered(pojo,this);

        getDelete().setVisible(dao.existsById(pojo.getId()));
        setVisible(true);
        getName().selectAll();
    }

    private void save () {
        dao.save(pojo);
        updateList.accept(null);
        setVisible(false);
    }

    private void delete () {
        dao.delete(pojo);
        updateList.accept(null);
        setVisible(false);
    }

}
