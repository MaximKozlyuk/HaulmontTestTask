package com.haulmont.testtask.frontend.editforms;

import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.domain.Doctor;
import com.haulmont.testtask.domain.GenericObject;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.TextField;

import java.util.function.Consumer;

public class DoctorForm extends HumanForm implements EditForm {

    private TextField specialization = new TextField("Specialization");

    private DoctorDao dao = DoctorDao.get();
    private Doctor pojo;

    private Consumer<Object> updateList;

    public DoctorForm (Consumer<Object> updateList) {
        super();
        this.updateList = updateList;

        getSave().addClickListener(e -> save());
        getDelete().addClickListener(e -> delete());

        addComponents(getName(),getSurname(),getMiddleName(),specialization, getButtons());

    }

    @Override
    public void setPojo (GenericObject pojo) {
        this.pojo = (Doctor) pojo;
        BeanFieldGroup.bindFieldsUnbuffered(pojo,this);

        getDelete().setVisible(dao.existsById(this.pojo.getId()));
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
