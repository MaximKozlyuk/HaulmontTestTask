package com.haulmont.testtask.frontend;

import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.domain.Doctor;
import com.haulmont.testtask.frontend.editforms.DoctorForm;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;
import java.util.stream.Collectors;

class DoctorView extends VerticalLayout implements ObjectView {

    private Grid grid = new Grid();

    private TextField search = new TextField();
    private DoctorForm doctorForm = new DoctorForm(this::updateDoctorList);

    private DoctorDao doctorDao = DoctorDao.get();

    private List<Doctor> doctors;

    DoctorView () {
        grid.setColumns("id", "name", "surname", "middleName", "specialization", "recipeAmount");
        updateDoctorList(null);

        // search functionality and create button
        search.setInputPrompt("Search");

        search.addTextChangeListener(event -> grid.setContainerDataSource(
                new BeanItemContainer<>(Doctor.class,
                        doctors.stream()
                                .filter(doc -> doc.toString().contains(event.getText()))
                                .collect(Collectors.toList()))
        ));

        Button clearFilter = new Button(FontAwesome.TIMES);
        clearFilter.addClickListener(click -> {
            search.clear();
            updateDoctorList(null);
        });

        Button addBtn = new Button("Add");
        addBtn.addClickListener(event -> {
            grid.select(null);
            Doctor newDoc = new Doctor();
            doctorForm.setPojo(newDoc);
        });

        CssLayout search = new CssLayout();
        search.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        search.addComponents(this.search, clearFilter);

        HorizontalLayout tools = new HorizontalLayout(search,addBtn);
        tools.setSpacing(true);

        //
        HorizontalLayout body = new HorizontalLayout(grid, doctorForm);
        doctorForm.setVisible(false);
        body.setSpacing(true);
        body.setSizeFull();
        grid.setSizeFull();
        body.setExpandRatio(grid,1);

        grid.addSelectionListener(event -> {
            if (event.getSelected().isEmpty()) {
                doctorForm.setVisible(false);
            } else {
                Doctor doc = (Doctor) event.getSelected().iterator().next();
                doctorForm.setPojo(doc);
            }
        });

        setSizeFull();
        setSpacing(true);
        addComponents(tools, body);
    }

    private void updateDoctorList (Object o) {
        doctors = doctorDao.findAllWithStats();
        BeanItemContainer beanItemContainer = new BeanItemContainer<>(Doctor.class, doctors);
        grid.setContainerDataSource(beanItemContainer);
    }

}
