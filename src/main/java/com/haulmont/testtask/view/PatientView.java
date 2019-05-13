package com.haulmont.testtask.view;

import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.domain.Patient;
import com.haulmont.testtask.view.editforms.PatientForm;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;
import java.util.stream.Collectors;

class PatientView extends VerticalLayout {

    private Grid grid = new Grid();

    private TextField search = new TextField();

    private PatientForm patientForm = new PatientForm(this::updatePatientsList);

    private PatientDao patientDao = PatientDao.get();

    private List<Patient> patients;

    PatientView() {
        grid.setColumns("id", "name", "surname", "middleName", "phoneNum");
        updatePatientsList(null);

        search.setInputPrompt("Search");

        search.addTextChangeListener(event -> {
            BeanItemContainer container = new BeanItemContainer<>(Patient.class,
                    patients.stream()
                            .filter(patient -> patient.toString().
                                    contains(event.getText()))
                            .collect(Collectors.toList()));
            grid.setContainerDataSource(container);
        });

        Button clearFilter = new Button(FontAwesome.TIMES);
        clearFilter.addClickListener(click -> {
            search.clear();
            updatePatientsList(null);
        });

        Button addBtn = new Button("Add");
        addBtn.addClickListener(event -> {
            grid.select(null);
            Patient newPatient = new Patient();
            patientForm.setPojo(newPatient);
        });

        CssLayout search = new CssLayout();
        search.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        search.addComponents(this.search, clearFilter);

        HorizontalLayout tools = new HorizontalLayout(search, addBtn);
        tools.setSpacing(true);

        //
        HorizontalLayout body = new HorizontalLayout(grid, patientForm);
        patientForm.setVisible(false);
        body.setSpacing(true);
        body.setSizeFull();
        grid.setSizeFull();
        body.setExpandRatio(grid, 1);

        grid.addSelectionListener(event -> {
            if (event.getSelected().isEmpty()) {
                patientForm.setVisible(false);
            } else {
                Patient patient = (Patient) event.getSelected()
                        .iterator().next();
                patientForm.setPojo(patient);
            }
        });

        setSizeFull();
        setSpacing(true);
        addComponents(tools, body);
    }

    private void updatePatientsList(Object o) {
        patients = patientDao.findAll();
        grid.setContainerDataSource(
                new BeanItemContainer<>(Patient.class, patients));
    }

}
