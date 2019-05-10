package com.haulmont.testtask.frontend;

import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.domain.GenericObject;
import com.haulmont.testtask.domain.Patient;
import com.haulmont.testtask.frontend.editforms.PatientForm;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;
import java.util.stream.Collectors;

/**
 * + Отображение списка пациентов
 * + Добавление нового пациента, редактирование и удаление существующего
 * Отображение списка врачей
 * Отображение статистической информации по количеству рецептов, выписанных врачами
 * Добавление нового врача, редактирование и удаление существующего
 * Отображения списка рецептов
 * Фильтрация списка рецептов по описанию, приоритету и пациенту
 * Добавление нового рецепта, редактирование и удаление существующего
 *
 * Система должна иметь защиту на уровне БД от удаления пациента и врача, для которых существуют рецепты
 * Все формы ввода должны валидировать данные в соответствии с их типом и допустимыми значениями
 */

//@StyleSheet("frontend://styles/styles.css")
//@PreserveOnRefresh
@Theme(ValoTheme.THEME_NAME)
@StyleSheet("vaadin://style.css")
public class MainUI extends  UI {

    private static final long serialVersionUID = 1L;

    private Grid grid = new Grid();

    private TextField searchPatient = new TextField();
    private PatientForm patientForm = new PatientForm(this::updatePatientsList);

    private PatientDao patientDao = PatientDao.get();
    private List<Patient> patients;

    @Override
    protected void init(VaadinRequest request) {

        VerticalLayout layout = new VerticalLayout();

        addHeader(layout);

        grid.setColumns("id", "name", "surname", "middleName", "phoneNum");
        updatePatientsList(null);

        // search functionality and create button
        searchPatient.setInputPrompt("Search");
        searchPatient.addTextChangeListener(event -> grid.setContainerDataSource(
                new BeanItemContainer<>(Patient.class,
                        patients.stream()
                                .filter(patient -> patient.toString().contains(event.getText()))
                                .collect(Collectors.toList()))
        ));
        Button clearFilter = new Button(FontAwesome.TIMES);
        clearFilter.addClickListener(click -> {
            searchPatient.clear();
            updatePatientsList(null);
        });

        Button addBtn = new Button("Add");
        addBtn.addClickListener(event -> {
            grid.select(null);
            Patient newPatient = new Patient();
            newPatient.setId(-1);
            patientForm.setPojo(newPatient);
        });

        CssLayout search = new CssLayout();
        search.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        search.addComponents(searchPatient, clearFilter);

        HorizontalLayout tools = new HorizontalLayout(search,addBtn);
        tools.setSpacing(true);

        //
        HorizontalLayout body = new HorizontalLayout(grid,patientForm);
        patientForm.setVisible(false);
        body.setSpacing(true);
        body.setSizeFull();
        grid.setSizeFull();
        body.setExpandRatio(grid,1);

        grid.addSelectionListener(event -> {
            if (event.getSelected().isEmpty()) {
                patientForm.setVisible(false);
            } else {
                Patient patient = (Patient) event.getSelected().iterator().next();
                patientForm.setPojo(patient);
            }
        });

        layout.addComponents(tools, body);
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);

    }

    private void updatePatientsList(Object o) {
        patients = patientDao.findAll();
        grid.setContainerDataSource(new BeanItemContainer<>(Patient.class, patients));
    }

    private void addHeader (AbstractOrderedLayout outerLayout) {

        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        headerLayout.addStyleName("header");
        headerLayout.setWidth(100, Unit.PERCENTAGE);

        Label title = new Label("<h2>Hospital viewer</h2>");
        title.addStyleName("site-title");
        title.setContentMode(ContentMode.HTML);
        headerLayout.addComponent(title);

        Button patients = new Button("Patients");
        Button recipes = new Button("Recipes");
        Button docs = new Button("Doctors");

        patients.addClickListener(event -> {

        });
        recipes.addClickListener(event -> {

        });
        docs.addClickListener(event -> {

        });

        headerLayout.addComponent(patients);
        headerLayout.addComponent(recipes);
        headerLayout.addComponent(docs);

        outerLayout.addComponent(headerLayout);

    }

    class InterfaceGeneralizer<T extends GenericObject> {

        private Class pojoType;

        public InterfaceGeneralizer(T pojo) {
            pojoType = pojo.getClass();
             //pojoType.getDeclaredFields()[0].getName()
            grid.setColumns();

        }

    }

}