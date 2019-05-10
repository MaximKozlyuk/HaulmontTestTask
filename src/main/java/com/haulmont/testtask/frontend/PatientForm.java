package com.haulmont.testtask.frontend;

import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.domain.Patient;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import java.util.function.Consumer;

public class PatientForm extends FormLayout {

    private TextField name = new TextField("Name");
    private TextField surname = new TextField("Surname");
    private TextField middleName = new TextField("Middle name");
    private TextField phoneNum = new TextField("Phone number");
    //private NativeSelect
    //private PopupDateField

    private Button save = new Button("Save");
    private Button delete = new Button("Delete ");

    private PatientDao dao = PatientDao.get();
    private Patient patient;
    private Consumer<Object> updateList;

    public PatientForm (Consumer<Object> updateList) {
        this.updateList = updateList;
        setSizeUndefined();

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());

        buttons.addComponents(save, delete);
        addComponents(name,surname,middleName,phoneNum, buttons);

    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        BeanFieldGroup.bindFieldsUnbuffered(patient,this);

        delete.setVisible(dao.existsById(patient.getId()));
        setVisible(true);
        name.selectAll();
    }

    private void save () {
        dao.save(patient);
        updateList.accept(null);
        setVisible(false);
    }

    private void delete () {
        dao.delete(patient);
        updateList.accept(null);
        setVisible(false);
    }

}
