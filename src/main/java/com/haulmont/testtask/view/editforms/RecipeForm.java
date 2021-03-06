package com.haulmont.testtask.view.editforms;

import com.haulmont.testtask.dao.RecipeDao;
import com.haulmont.testtask.domain.*;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.*;

import java.util.function.Consumer;

public class RecipeForm extends FormButtons {

    private TextField description = new TextField("Description");
    private PopupDateField creation = new PopupDateField("Creation");
    private PopupDateField expired = new PopupDateField("Expired");
    private NativeSelect priority = new NativeSelect("Priority");

    private RecipeDao recipeDao = RecipeDao.get();
    private Recipe pojo;

    private Consumer<Object> updateList;

    public RecipeForm(Consumer<Object> updateList,
                      Consumer<Object> selectDoctor,
                      Consumer<Object> selectPatient) {
        super();
        this.updateList = updateList;

        getSave().addClickListener(e -> save());
        getDelete().addClickListener(e -> delete());

        priority.addItems(Priority.values());
        priority.setNullSelectionItemId(Priority.NORMALEM);

        Button selectDoctorBtn = new Button("Select doctor");
        Button selectPatientBtn = new Button("Select patient");

        selectDoctorBtn.addClickListener(event -> selectDoctor.accept(null));

        selectPatientBtn.addClickListener(event ->
                selectPatient.accept(null));

        description.setMaxLength(256);

        addComponents(description, creation, expired, priority,
                selectDoctorBtn, selectPatientBtn, getButtons());

    }

    public void setPojo(Recipe pojo) {
        this.pojo = pojo;
        BeanFieldGroup.bindFieldsUnbuffered(pojo, this);

        getDelete().setVisible(recipeDao.existsById(pojo.getId()));
        setVisible(true);
        description.selectAll();
    }

    private void save() {
        if (pojo.getCreation() == null || pojo.getExpired() == null) {
            Notification.show("Choose date");
            return;
        }
        if (pojo.getDoctor() == null) {
            Notification.show("Choose doctor");
            return;
        }
        if (pojo.getPatient() == null) {
            Notification.show("Choose patient");
            return;
        }
        if (pojo.getCreation().after(pojo.getExpired())) {
            Notification.show("Wrong dates");
            return;
        }
        if (pojo.getPriority() == null)
            pojo.setPriority(Priority.NORMALEM);
        recipeDao.save(pojo);
        updateList.accept(null);
        setVisible(false);
    }

    private void delete() {
        recipeDao.delete(pojo);
        updateList.accept(null);
        setVisible(false);
        pojo = null;
    }

    public void setDoctor(Doctor doctor) {
        this.pojo.setDoctor(doctor);
    }

    public void setPatient(Patient patient) {
        this.pojo.setPatient(patient);
    }

}
